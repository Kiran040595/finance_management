package com.example.financemanagement.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.financemanagement.dto.LoanDTO;
import com.example.financemanagement.dto.LoanRequestDTO;
import com.example.financemanagement.service.LoanService;

@RestController
@RequestMapping("/api/loan")
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from localhost:3000
public class LoanController {
	
    @Autowired
    private LoanService loanService;
    
    
    @PostMapping
    public ResponseEntity<String> createLoan(@RequestBody LoanRequestDTO loanDTO){
    	loanService.createLoan(loanDTO);
    	return ResponseEntity.ok("Loan created successfully!");
    	
    }
    
    
    @GetMapping("/loans")
    public List<LoanDTO> getAllLoans(){
    	return loanService.getAllLoans();
    	
    }
    
    
    @GetMapping("/{id}")
    public LoanDTO getLoanById(@PathVariable Long id) {
    	return loanService.getLoanById(id);
    }
   
    
    
    
}

