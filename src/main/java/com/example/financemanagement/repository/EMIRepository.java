package com.example.financemanagement.repository;

import com.example.financemanagement.model.EMI;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EMIRepository extends JpaRepository<EMI, Long> {

	List<EMI> findByLoanId(Long loanId);
}
