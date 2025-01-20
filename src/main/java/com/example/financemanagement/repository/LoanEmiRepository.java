package com.example.financemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.financemanagement.model.LoanEmi;

public interface LoanEmiRepository extends JpaRepository<LoanEmi, Long> {

	public List<LoanEmi> findByLoanId(Long id);

	LoanEmi findByLoanIdAndEmiNumber(Long loanId, Integer emiNumber);

	@Query("SELECT COUNT(e) " +
		       "FROM LoanEmi e " +
		       "JOIN e.loan l " +
		       "WHERE e.status = 'Pending' AND e.emiDate < CURRENT_DATE AND l.status = true")
	long fetchTotalPendingEmiCount();

	@Query("SELECT SUM(e.emiAmount - COALESCE(e.paidAmount, 0)) " +
		       "FROM LoanEmi e " +
		       "JOIN e.loan l " +
		       "WHERE e.status = 'Pending' AND e.emiDate < CURRENT_DATE AND l.status = true")
	Double fetchTotaPendingEmiAmount();

	@Query("SELECT COUNT(DISTINCT e.loan.customer.id) " +
		       "FROM LoanEmi e " +
		       "JOIN e.loan l " +
		       "WHERE e.status = 'Pending' AND e.emiDate < CURRENT_DATE AND l.status = true")
	long countPendingCustomers();
}
