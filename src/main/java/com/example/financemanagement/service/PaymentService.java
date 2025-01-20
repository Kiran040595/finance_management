package com.example.financemanagement.service;

import org.springframework.data.domain.Pageable;

import com.example.financemanagement.dto.PaymentDTO;
import com.example.financemanagement.dto.PaymentResponse;
import com.example.financemanagement.model.LoanEmi;

public interface PaymentService {
	
	public PaymentResponse getAllPayments(String searchQuery, Pageable  pageable);
	PaymentDTO getPaymentDetails(Long loanId);
	public PaymentDTO getLoanDetailsByFileNumber(String fileNumber);
	public LoanEmi payEmi(Long loanId, Integer emiNumber, Double paymentAmount);

}
