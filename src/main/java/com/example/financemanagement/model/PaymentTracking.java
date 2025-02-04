package com.example.financemanagement.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PaymentTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long ReceptNumber;
    private LocalDate paymentDate; // Date of payment
    private Double amountPaid; // Amount paid
    private String paymentMethod; // Cash, UPI, Bank Transfer, etc.

    @ManyToOne
    @JoinColumn(name = "loan_emi_id")
    private LoanEmi loanEmi; // Links to EMI paid

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan; // Links to the loan

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer; // Links to the customer

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getReceptNumber() {
		return ReceptNumber;
	}

	public void setReceptNumber(Long receptNumber) {
		ReceptNumber = receptNumber;
	}

	public LocalDate getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(Double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public LoanEmi getLoanEmi() {
		return loanEmi;
	}

	public void setLoanEmi(LoanEmi loanEmi) {
		this.loanEmi = loanEmi;
	}

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

    // Getters & Setters
    
    
}

