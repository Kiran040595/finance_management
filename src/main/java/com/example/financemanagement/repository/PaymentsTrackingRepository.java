package com.example.financemanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.financemanagement.model.PaymentsTracking;

@Repository
public interface PaymentsTrackingRepository extends JpaRepository<PaymentsTracking, Long> {
    
    List<PaymentsTracking> findByLoanId(Long loanId);

    @Query("SELECT p.billNumber FROM PaymentsTracking p WHERE p.transactionType = 'Loan Given' ORDER BY p.id DESC")
    List<String> findLastLoanBillNumbers();

    @Query(value = "SELECT bill_number FROM finance_management.payments_tracking WHERE transaction_type = 'EMI Paid' ORDER BY id DESC LIMIT 1", 
    nativeQuery = true)
    Optional<String> findLastEmiBillNumber();
}
