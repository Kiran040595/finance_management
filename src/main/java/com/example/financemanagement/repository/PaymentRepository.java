package com.example.financemanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.financemanagement.model.PaymentTracking;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentTracking, Long>{
	
	

    // Fetch the last loan bill number (native SQL query)
    @Query(value = "SELECT bill_number FROM payment_tracking WHERE transaction_type = 'Loan Given' ORDER BY id DESC LIMIT 1", 
           nativeQuery = true)
    Optional<String> findLastLoanBillNumber();

    // Fetch the last EMI bill number (native SQL query)
    @Query(value = "SELECT bill_number FROM payment_tracking WHERE transaction_type = 'EMI Paid' ORDER BY id DESC LIMIT 1", 
           nativeQuery = true)
    Optional<String> findLastEmiBillNumber();
	 
	 

}
