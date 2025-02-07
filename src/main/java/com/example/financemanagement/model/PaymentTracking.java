package com.example.financemanagement.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;




@Entity
@Table(name = "payment_tracking", schema = "finance_management")
public class PaymentTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    
    @Column(nullable = false)
    private String transactionType; // "Loan Given" or "EMI Paid"
    
    @Column(nullable = false, unique = true)
    private String billNumber; // Unique bill number (L-XXXX / E-XXXX)
    
    @Column(nullable = false)
    private LocalDate transactionDate; // Date of transaction
    
    @Column(nullable = false)
    private Double amount; // Amount given/paid
    
//    @ManyToOne
//    @JoinColumn(name = "loan_emi_id")
//    private LoanEmi loanEmi; // Links to EMI paid
//
//    @ManyToOne
//    @JoinColumn(name = "loan_id")
//    private Loan loan; // Links to the loan
//
//    @ManyToOne
//    @JoinColumn(name = "customer_id")
//    private Customer customer; // Links to the customer

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

//	public LoanEmi getLoanEmi() {
//		return loanEmi;
//	}
//
//	public void setLoanEmi(LoanEmi loanEmi) {
//		this.loanEmi = loanEmi;
//	}
//
//	public Loan getLoan() {
//		return loan;
//	}
//
//	public void setLoan(Loan loan) {
//		this.loan = loan;
//	}
//
//	public Customer getCustomer() {
//		return customer;
//	}
//
//	public void setCustomer(Customer customer) {
//		this.customer = customer;
//	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	

    // Getters & Setters
    
    
}

