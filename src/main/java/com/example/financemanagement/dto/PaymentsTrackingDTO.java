package com.example.financemanagement.dto;

import java.time.LocalDate;

public class PaymentsTrackingDTO {
    private Long id;
    private String transactionType;
    private String billNumber;
    private LocalDate transactionDate;
    private Double amount;
    private String fileNumber;

    // Constructors
    public PaymentsTrackingDTO() {}

    public PaymentsTrackingDTO(Long id, String transactionType, String billNumber, LocalDate transactionDate, Double amount, String fileNumber) {
        this.id = id;
        this.transactionType = transactionType;
        this.billNumber = billNumber;
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.fileNumber = fileNumber;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }

    public String getBillNumber() { return billNumber; }
    public void setBillNumber(String billNumber) { this.billNumber = billNumber; }

    public LocalDate getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDate transactionDate) { this.transactionDate = transactionDate; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getFileNumber() { return fileNumber; }
    public void setFileNumber(String fileNumber) { this.fileNumber = fileNumber; }
}
