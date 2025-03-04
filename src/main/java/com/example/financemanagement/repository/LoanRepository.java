package com.example.financemanagement.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.financemanagement.model.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long>, JpaSpecificationExecutor<Loan> {

    @Query("SELECT l FROM Loan l " +
            "LEFT JOIN FETCH l.customer c " +
            "LEFT JOIN FETCH l.vehicle v " +
            "LEFT JOIN FETCH l.guarantor g " +
            "WHERE l.id = :loanId")
    Loan findLoanWithDetailsById(@Param("loanId") Long loanId);

    Optional<Loan> findByFileNumber(Long fileNumber);

    Long getLoanIdByFileNumber(Long loanId);

    // Custom method for search with pagination and dynamic sorting
 // Custom method for search with pagination and dynamic sorting
    default Page<Loan> findBySearchQuery(String searchQuery, Pageable pageable) {
        // Create a Specification object
        return findAll((Root<Loan> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            // Search query pattern for case-insensitive matching
            String likePattern = "%" + searchQuery.toLowerCase() + "%";

            // Build search predicates for multiple fields
            Predicate searchPredicate = cb.or(
            	    // Check if searchQuery is not empty before converting to Long
            	    searchQuery != null && !searchQuery.isEmpty() 
            	        ? cb.equal(root.get("fileNumber"), Long.valueOf(searchQuery))  // Exact match for fileNumber
            	        : cb.conjunction(),  // Skip this condition if searchQuery is empty
            	    cb.like(cb.lower(root.get("customer").get("name")), likePattern),
            	    cb.like(cb.lower(root.get("vehicle").get("vehicleNumber")), likePattern)
            	);

            
            Predicate statusPredicate = cb.equal(root.get("status"), true); // Filter by active status
            searchPredicate = cb.and(searchPredicate, statusPredicate);

            // Apply dynamic sorting based on the 'Pageable' object
            List<javax.persistence.criteria.Order> orders = pageable.getSort().stream()
                .map(order -> {
                    Path<Object> path = null;

                    // Handle sorting for nested fields using if conditions
                    if ("customerName".equals(order.getProperty())) {
                        path = root.get("customer").get("name"); // Nested customer.name
                    } else if ("vehicleNumber".equals(order.getProperty())) {
                        path = root.get("vehicle").get("vehicleNumber"); // Nested vehicle.vehicleNumber
                    } else if ("fileNumber".equals(order.getProperty())) {
                        path = root.get("fileNumber"); // Simple fileNumber
                    } else if ("loanAmount".equals(order.getProperty())) {
                        path = root.get("loanAmount"); // Simple loanAmount
                    }
                    // Add more conditions as needed for other fields

                    // Return ascending or descending order based on the direction
                    if (path != null) {
                        return order.isAscending() ? cb.asc(path) : cb.desc(path);
                    }
                    return null;
                })
                .filter(order -> order != null) // Filter out any null orders
                .collect(Collectors.toList()); // Use Collectors.toList() instead of toList()

            // Apply the sorting criteria to the query
            query.orderBy(orders);

            // Return the search predicate to filter the results
            return searchPredicate;
        }, pageable);
    }
    
    //Loan Managemnt DashBoard Data code
    
    	@Query("SELECT COALESCE(SUM (l.loanAmount),0) FROM Loan l")
    	double getTotalLoanAmount();
    	
    	@Query("SELECT COALESCE(SUM(e.paidAmount), 0) FROM LoanEmi e JOIN e.loan l")
    	double getTotalReceivedAmount();

    	
		/*
		 * @Query("SELECT COALESCE(SUM(l.loanAmount) - SUM(e.paidAmount), 0) FROM Loan l LEFT JOIN LoanEmi e ON l.id = e.loan.id"
		 * ) double getTotalOutstandingAmount();
		 */
    	
    	@Query("SELECT SUM(l.remainingAmount) FROM LoanEmi l WHERE l.status = 'Pending'")
    	Double getTotalOutstandingAmount();



 
    
    	@Query("SELECT COUNT(l) FROM Loan l WHERE l.status = 'True'")
    	int getActiveLoanCount();
    	
    	@Query("SELECT COUNT(l) FROM Loan l WHERE l.status = 'False'")
    	int getClosedLoanCount();

    
    
    
    

}
