package com.example.financemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.financemanagement.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{
	
	List<Payment> findByLoanId(Long loanId);

}
