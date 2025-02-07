package com.example.financemanagement.model;

import java.time.LocalDate;
import javax.persistence.*;


@Entity
@Table(name = "payments_tracking", schema = "finance_management")
public class PaymentsTracking {
    
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
    private Double amount; // Amount paid

    @ManyToOne
    @JoinColumn(name = "loan_emi_id")
    private LoanEmi loanEmi;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // Getters and Setters
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

    public LoanEmi getLoanEmi() { return loanEmi; }
    public void setLoanEmi(LoanEmi loanEmi) { this.loanEmi = loanEmi; }

    public Loan getLoan() { return loan; }
    public void setLoan(Loan loan) { this.loan = loan; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
}
