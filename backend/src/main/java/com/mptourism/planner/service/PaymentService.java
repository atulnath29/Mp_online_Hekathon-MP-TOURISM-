package com.mptourism.planner.service;

import com.mptourism.planner.dto.PaymentRequest;
import com.mptourism.planner.model.Payment;
import com.mptourism.planner.model.Trip;
import com.mptourism.planner.repository.PaymentRepository;
import com.mptourism.planner.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private TripRepository tripRepository;

    public Map<String, Object> processPayment(PaymentRequest request) {
        if (request.getAmount() == null || request.getAmount() <= 0) {
            throw new IllegalArgumentException("Invalid payment amount.");
        }

        String txId = request.getTransactionId() != null && !request.getTransactionId().isBlank()
                ? request.getTransactionId()
                : "MPT-TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Payment payment = new Payment(
            request.getTripId(),
            request.getAmount(),
            "SUCCESS",
            txId,
            request.getPaymentMethod() != null ? request.getPaymentMethod() : "Razorpay Mock UPI"
        );

        Payment savedPayment = paymentRepository.save(payment);

        String destinationName = "Madhya Pradesh Tour";
        if (request.getTripId() != null) {
            Trip trip = tripRepository.findById(request.getTripId()).orElse(null);
            if (trip != null && trip.getPlaceName() != null) {
                destinationName = trip.getPlaceName();
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("paymentId", savedPayment.getId());
        response.put("transactionId", savedPayment.getTransactionId());
        response.put("amount", savedPayment.getAmount());
        response.put("status", savedPayment.getStatus());
        response.put("paymentMethod", savedPayment.getPaymentMethod());
        response.put("createdAt", savedPayment.getCreatedAt().toString());
        response.put("destination", destinationName);
        response.put("message", "Payment processed successfully! Your MP Tourism trip booking is confirmed.");

        return response;
    }
}
