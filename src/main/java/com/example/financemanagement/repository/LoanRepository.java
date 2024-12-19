package com.example.financemanagement.repository;

import com.example.financemanagement.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LoanRepository extends JpaRepository<Loan, Long> {

	
	 @Query("SELECT l FROM Loan l " +
	           "LEFT JOIN FETCH l.customer c " +
	           "LEFT JOIN FETCH l.vehicle v " +
	           "LEFT JOIN FETCH l.guarantor g " +
	           "WHERE l.id = :loanId")
	    Loan findLoanWithDetailsById(@Param("loanId") Long loanId);
	
}
