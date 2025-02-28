package com.example.financemanagement.dto;

import java.time.LocalDate;
import java.util.Date;

public class PaymentRequestDTO {
    private Long fileNumber;
    private Integer emiNumber;
    private Double paymentAmount;
    private LocalDate paymentDate;
    
    private String emiDate; // Assuming String to match frontend date format
    private Double remainingAmount;

    // Getters and Setters
    public Long getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(Long fileNumber) {
        this.fileNumber = fileNumber;
    }

    public Integer getEmiNumber() {
        return emiNumber;
    }

    public void setEmiNumber(Integer emiNumber) {
        this.emiNumber = emiNumber;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

	public LocalDate getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getEmiDate() {
		return emiDate;
	}

	public void setEmiDate(String emiDate) {
		this.emiDate = emiDate;
	}

	public Double getRemainingAmount() {
		return remainingAmount;
	}

	public void setRemainingAmount(Double remainingAmount) {
		this.remainingAmount = remainingAmount;
	}
    
	
    
}
