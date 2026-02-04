package com.example.Backend.service.PaymentService;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Service;

import com.example.Backend.dto.Payment.PaymentRequestV1;
import com.example.Backend.dto.Payment.PaymentWebhookRequestV1;
import com.example.Backend.entity.UserModel;
import com.example.Backend.entity.Order.Order;
import com.example.Backend.entity.Order.OrderItem;
import com.example.Backend.entity.Payment.Payment;
import com.example.Backend.repository.jpa.OrderItemRepository;
import com.example.Backend.repository.jpa.OrderRepository;
import com.example.Backend.repository.jpa.PaymentRepository;
import com.example.Backend.repository.jpa.UserRepository;
import com.example.Backend.service.ProductService.ProductService;
import com.example.Backend.service.email.EmailService;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {
    @Autowired
    private final PaymentRepository paymentRepository;

    @Autowired
    private final OrderRepository orderRepository;

    @Autowired
    private final OrderItemRepository orderItemRepository;

    @Autowired
    private final ProductService productService;

    @Autowired
    private final RazorpayClient razorpayClient;

    @Autowired
    private final EmailService emailService;

    @Autowired
    private final UserRepository userRepository;

    public Payment createPayment(PaymentRequestV1 request) throws RazorpayException {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found!"));

        // verify order amount if it wmatches with the amount in the request
        if (!order.getTotalAmount().equals(request.getAmount())) {
            throw new RuntimeException("Payment amount mismatch! Expected: " +
                    order.getTotalAmount() + ", Received: " + request.getAmount());
        }
        // Create Razorpay Order
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", (int) (order.getTotalAmount() * 100)); // Razorpay uses Paisa
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", order.getId().toString());

        com.razorpay.Order razorpayOrder = razorpayClient.orders.create(orderRequest);

        // Save PENDING payment record to Postgres
        Payment payment = Payment.builder()
                .orderId(order.getId())
                .amount(order.getTotalAmount())
                .razorpayOrderId(razorpayOrder.get("id"))
                .status("PENDING")
                .build();

        return paymentRepository.save(payment);
    }

    @Transactional
    public void processPaymentWebhook(PaymentWebhookRequestV1 request) {
        String rzpOrderId = request.getPayload().getPayment().getOrder_id();
        String status = request.getPayload().getPayment().getStatus();
        String rzpPaymentId = request.getPayload().getPayment().getId();

        // find payment in postgres
        Payment payment = paymentRepository.findByRazorpayOrderId(rzpOrderId);

        if ("captured".equalsIgnoreCase(status)) {
            // update postgres status
            payment.setStatus("SUCCESS");
            payment.setRazorpayPaymentId(rzpPaymentId);

            Order order = orderRepository.findById(payment.getOrderId())
                    .orElseThrow(() -> new RuntimeException("Order not found"));
            order.setStatus("PAID");

            orderItemRepository.findByOrderId(order.getId())
                    .forEach(item -> productService.decrementStock(item.getProductId(), item.getQuantity()));

            List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());

            String userEmail = userRepository.findById(order.getUserId()).get().getEmail();
            if (!userEmail.isEmpty()) {
                emailService.sendOrderConfirmation(userEmail, order, items);
                orderRepository.save(order);
            } else {
                throw new RuntimeException("User not found");
            }
        } else {
            payment.setStatus("FAILED");
        }
        paymentRepository.save(payment);
    }
}
