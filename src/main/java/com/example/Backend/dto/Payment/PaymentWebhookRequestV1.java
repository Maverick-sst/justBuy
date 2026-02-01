package com.example.Backend.dto.Payment;

import lombok.Data;

@Data
public class PaymentWebhookRequestV1 {
    private String event;
    private Payload payload;

    @Data
    public static class Payload{
        private PaymentInfo payment;
    }

    @Data
    public static class PaymentInfo{
        private String id; // rz paymentid
        private String order_id; // rz orderid
        private String status; // captures or failed etc
    }
}
