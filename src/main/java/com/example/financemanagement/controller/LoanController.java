package com.example.financemanagement.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.financemanagement.dto.FullLoanDetailsDTO;
import com.example.financemanagement.dto.LoanRequestDTO;
import com.example.financemanagement.dto.LoanResponseDTO;
import com.example.financemanagement.dto.LoanStatsDTO;
import com.example.financemanagement.feign.LoanServiceFeignClient;
import com.example.financemanagement.service.LoanService;

@RestController
@RequestMapping("/api/loan")
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from localhost:3000
public class LoanController {
	
    @Autowired
    private LoanService loanService;
    
    @Autowired
    private LoanServiceFeignClient loanServiceFeignClient;
    
    
    @PostMapping
    public ResponseEntity<String> createLoan(@RequestBody LoanRequestDTO loanDTO){
    	loanServiceFeignClient.createLoan(loanDTO);
    	return ResponseEntity.status(HttpStatus.CREATED).body("Loan created successfully");
    	
    }
    
    
    @GetMapping("/loans")
    public List<LoanResponseDTO> getAllLoans(){
    	return loanServiceFeignClient.getAllLoans();
    	
    }
    
    
    @GetMapping("/{id}")
    public ResponseEntity<FullLoanDetailsDTO> getLoanById(@PathVariable Long id) {
        FullLoanDetailsDTO loanDetails = loanService.getFullLoanDetailsById(id);

        return ResponseEntity.ok(loanDetails);
    }
   
    @PutMapping("/{id}")
    public ResponseEntity<String> updateLoan(@PathVariable Long id, @RequestBody LoanRequestDTO updatedLoan) {
    	loanService.updateLoan(id,updatedLoan);
    	return ResponseEntity.ok("Loan updated successfully!");
    }
    
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLoan(@PathVariable Long id){
    	loanService.deleteLoan(id);
    	return ResponseEntity.ok("Loan with ID " + id + " deleted successfully.");
    	
    	
    }
    
    @GetMapping(value = "/stats")
    public ResponseEntity<LoanStatsDTO> getLoanStats() {
        LoanStatsDTO stats = loanService.getLoanStatistics();
        return ResponseEntity.ok(stats);
    }
    
   

    
    
    
}

