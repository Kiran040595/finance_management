package com.example.financemanagement.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class LoanEmi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer emiNumber; // EMI Sequence Number (1, 2, 3, etc.)
    @Column(precision = 10, scale = 2) 
    private Double emiAmount; // Total EMI Amount
    @Column(precision = 10, scale = 2) 
    private Double paidAmount = 0.0; // Amount Paid (default: 0)
    @Column(precision = 10, scale = 2) 
    private Double remainingAmount; // Remaining Amount (calculated dynamically)
    private LocalDate paymentDate; // Last Payment Date
    private LocalDate emiDate;
    private String status = "Pending"; // EMI Status: "Pending", "Paid", or "Overpaid"
    @Column(precision = 10, scale = 2) 
    private Double odAmountAfterPaymnet = 0.0;
    @ManyToOne
    @JoinColumn(name = "loan_id")
    @JsonBackReference
    private Loan loan; // The loan associated with this EMI

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEmiNumber() {
        return emiNumber;
    }

    public void setEmiNumber(Integer emiNumber) {
        this.emiNumber = emiNumber;
    }

    public Double getEmiAmount() {
        return emiAmount;
    }

    public void setEmiAmount(Double emiAmount) {
        this.emiAmount = emiAmount;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Double getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(Double remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }
    
    public LocalDate getEmiDate() {
		return emiDate;
	}

	public void setEmiDate(LocalDate emiDate) {
		this.emiDate = emiDate;
	}
	
	public Double getOdAmountAfterPaymnet() {
		return odAmountAfterPaymnet;
	}

	public void setOdAmountAfterPaymnet(Double odAmountAfterPaymnet) {
		this.odAmountAfterPaymnet = odAmountAfterPaymnet;
	}

	// Utility Method to Update Payment
    public void updatePayment(double paymentAmount,Double od,LocalDate paidDate) {
        this.paidAmount += paymentAmount;
        this.remainingAmount = this.emiAmount+od - this.paidAmount;
        Double reainingAmountWithoutOD = this.emiAmount-this.paidAmount;
        LocalDate date = paidDate!=null? paidDate:LocalDate.now();
        if (reainingAmountWithoutOD < -100) {
            this.status = "Overpaid";
            this.paymentDate = date;
        } else if (reainingAmountWithoutOD  <=0) {
            this.status = "Paid";
            this.paymentDate = date;
        } else {
            this.status = "Pending";
        }

         // Update payment date to current date
    }
}
