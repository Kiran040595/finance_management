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

    Optional<Loan> findByFileNumber(String fileNumber);

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
                cb.like(cb.lower(root.get("fileNumber")), likePattern),
                cb.like(cb.lower(root.get("customer").get("name")), likePattern),
                cb.like(cb.lower(root.get("vehicle").get("vehicleNumber")), likePattern)
            );

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

}
