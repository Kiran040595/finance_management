package com.example.financemanagement.service;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.data.domain.Pageable;

import com.example.financemanagement.dto.PaymentDTO;
import com.example.financemanagement.dto.PaymentResponse;
import com.example.financemanagement.model.LoanEmi;

public interface PaymentService {
	
	public PaymentResponse getAllPayments(String searchQuery, Pageable  pageable);
	PaymentDTO getPaymentDetails(Long loanId);
	public PaymentDTO getLoanDetailsByFileNumber(Long fileNumber);
	public LoanEmi payEmi(Long loanId, Integer emiNumber, Double paymentAmount, LocalDate paidDate);
	
	public LoanEmi updateEmi(Long loanId, Integer emiNumber, String emiDate, LocalDate paymentDate, Double paidAmount, Double remainingAmount);
}
