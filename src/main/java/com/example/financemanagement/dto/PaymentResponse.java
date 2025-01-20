package com.example.financemanagement.dto;

import org.springframework.data.domain.Page;

public class PaymentResponse {
	
	private Page<PaymentDTO> payments;
    private long totalLoans;
    private long pendingEmiCount;
    private double pendingEmiAmount;
    private long pendingCustomerCount;
    
	public PaymentResponse(Page<PaymentDTO> payments, long totalLoans, long pendingEmiCount,
			double pendingEmiAmount,long pendingCustomerCount) {
		super();
		this.payments = payments;
		this.totalLoans = totalLoans;
		this.pendingEmiCount = pendingEmiCount;
		this.pendingEmiAmount = pendingEmiAmount;
		this.pendingCustomerCount=pendingCustomerCount;
	}
	public Page<PaymentDTO> getPayments() {
		return payments;	
	}
	public void setPayments(Page<PaymentDTO> payments) {
		this.payments = payments;
	}
	public long getTotalLoans() {
		return totalLoans;
	}
	public void setTotalLoans(long totalLoans) {
		this.totalLoans = totalLoans;
	}
	public long getPendingEmiCount() {
		return pendingEmiCount;
	}
	public void setPendingEmiCount(long pendingEmiCount) {
		this.pendingEmiCount = pendingEmiCount;
	}
	public double getPendingEmiAmount() {
		return pendingEmiAmount;
	}
	public void setPendingEmiAmount(double pendingEmiAmount) {
		this.pendingEmiAmount = pendingEmiAmount;
	}
	public long getPendingCustomerCount() {
		return pendingCustomerCount;
	}
	public void setPendingCustomerCount(long pendingCustomerCount) {
		this.pendingCustomerCount = pendingCustomerCount;
	}
    
    
    

}
