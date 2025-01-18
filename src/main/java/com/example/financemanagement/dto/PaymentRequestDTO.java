package com.example.financemanagement.dto;

public class PaymentRequestDTO {
    private Long fileNumber;
    private Integer emiNumber;
    private Double paymentAmount;

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
}
