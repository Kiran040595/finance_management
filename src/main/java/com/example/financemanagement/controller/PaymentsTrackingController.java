package com.example.financemanagement.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.financemanagement.dto.PaymentsTrackingDTO;
import com.example.financemanagement.service.PaymentsTrackingService;

@RestController
@RequestMapping("/api/paymentsTracking")
public class PaymentsTrackingController {

    private final PaymentsTrackingService paymentsTrackingService;

    public PaymentsTrackingController(PaymentsTrackingService paymentsTrackingService) {
        this.paymentsTrackingService = paymentsTrackingService;
    }

    @GetMapping("/getAll")
    public List<PaymentsTrackingDTO> getAllPayments() {
        return paymentsTrackingService.getAllPayments();
    }
}
