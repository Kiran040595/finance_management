package com.example.financemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping; // Added for update
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.financemanagement.dto.PaymentDTO;
import com.example.financemanagement.dto.PaymentRequestDTO;
import com.example.financemanagement.dto.PaymentResponse;
import com.example.financemanagement.model.LoanEmi;
import com.example.financemanagement.service.LoanService;
import com.example.financemanagement.service.PaymentService;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private LoanService loanService;

    @GetMapping("/payments")
    public ResponseEntity<PaymentResponse> getPayments(
            @RequestParam(defaultValue = "") String searchQuery,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fileNumber") String sortKey,
            @RequestParam(defaultValue = "asc") String sortDirection) {

        Pageable pageable = PageRequest.of(page - 1, size,
                "desc".equalsIgnoreCase(sortDirection)
                        ? Sort.by(sortKey).descending()
                        : Sort.by(sortKey).ascending());
        PaymentResponse response = paymentService.getAllPayments(searchQuery, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/payment/{loanId}")
    public PaymentDTO getPaymentDetails(@PathVariable Long loanId) {
        return paymentService.getPaymentDetails(loanId);
    }

    @GetMapping("/payments/{fileNumber}")
    public PaymentDTO getLoanDetailsByFileNumber(@PathVariable Long fileNumber) {
        return paymentService.getLoanDetailsByFileNumber(fileNumber);
    }

    @PostMapping("/payments/pay/{fileNumber}")
    public LoanEmi payEmi(@RequestBody PaymentRequestDTO paymentRequest) {
        return paymentService.payEmi(paymentRequest.getFileNumber(), paymentRequest.getEmiNumber(),
                paymentRequest.getPaymentAmount(), paymentRequest.getPaymentDate());
    }

    @PutMapping("/payments/{fileNumber}/{emiNumber}")
    public LoanEmi updateEmi(
            @PathVariable Long fileNumber,
            @PathVariable Integer emiNumber,
            @RequestBody PaymentRequestDTO paymentRequest) {
        return paymentService.updateEmi(
                fileNumber,
                emiNumber,
                paymentRequest.getEmiDate(),
                paymentRequest.getPaymentDate(),
                paymentRequest.getPaymentAmount(),
                paymentRequest.getRemainingAmount()
        );
    }
}