package com.example.Backend.service.email;

import com.example.Backend.entity.Order.Order;
import com.example.Backend.entity.Order.OrderItem;
import com.example.Backend.document.Product;
import com.example.Backend.repository.mongo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final ProductRepository productRepository; //

    @Async // Runs in background
    public void sendOrderConfirmation(String userEmail, Order order, List<OrderItem> items) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(userEmail);
            helper.setSubject("JustBuy Order Confirmation - #" + order.getId());

            StringBuilder html = new StringBuilder();
            html.append("<h2>Thank you for your order!</h2>");
            html.append("<p>Order Status: <b>PAID</b></p>");
            html.append("<table border='1' cellpadding='10'><tr><th>Product</th><th>Quantity</th><th>Price</th></tr>");

            for (OrderItem item : items) {
                // Bridge to MongoDB to get the display name
                Product p = productRepository.findById(item.getProductId()).orElse(null);
                String name = (p != null) ? p.getName() : "Product ID: " + item.getProductId();

                html.append("<tr>")
                    .append("<td>").append(name).append("</td>")
                    .append("<td>").append(item.getQuantity()).append("</td>")
                    .append("<td>₹").append(item.getPriceAtPurchase()).append("</td>")
                    .append("</tr>");
            }
            html.append("</table>");
            html.append("<h3>Total Paid: ₹").append(order.getTotalAmount()).append("</h3>");

            helper.setText(html.toString(), true);
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace(); // Log email failures without crashing the transaction
        }
    }
}