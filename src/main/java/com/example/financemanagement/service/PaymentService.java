package com.example.financemanagement.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.financemanagement.dto.LoanDTO;
import com.example.financemanagement.dto.PaymentDTO;
import com.example.financemanagement.model.LoanEmi;

public interface PaymentService {
	
	public Page<PaymentDTO> getAllPayments(String searchQuery, Pageable  pageable);
	PaymentDTO getPaymentDetails(Long loanId);
	public PaymentDTO getLoanDetailsByFileNumber(String fileNumber);
	public LoanEmi payEmi(Long loanId, Integer emiNumber, Double paymentAmount);

}
