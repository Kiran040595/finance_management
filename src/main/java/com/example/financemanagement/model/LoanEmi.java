package com.example.financemanagement.model;

import java.time.LocalDate;
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
    private Double emiAmount; // Total EMI Amount
    private Double paidAmount = 0.0; // Amount Paid (default: 0)
    private Double remainingAmount; // Remaining Amount (calculated dynamically)
    private LocalDate paymentDate; // Last Payment Date
    private LocalDate emiDate;
    private String status = "Pending"; // EMI Status: "Pending", "Paid", or "Overpaid"

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

	// Utility Method to Update Payment
    public void updatePayment(double paymentAmount) {
        this.paidAmount += paymentAmount;
        this.remainingAmount = this.emiAmount - this.paidAmount;

        if (this.remainingAmount < -100) {
            this.status = "Overpaid";
        } else if (this.remainingAmount  <=0) {
            this.status = "Paid";
        } else {
            this.status = "Pending";
        }

        this.paymentDate = LocalDate.now(); // Update payment date to current date
    }
}
