package com.example.financemanagement.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.financemanagement.dto.LoanDTO;
import com.example.financemanagement.service.LoanService;

@RestController
@RequestMapping("/api/loans")
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from localhost:3000
public class LoanController {
	
    @Autowired
    private LoanService loanService;
    
    
    @PostMapping
    public ResponseEntity<LoanDTO> createLoan(@RequestBody LoanDTO loanDTO){
    	LoanDTO createdLoan = loanService.createLoan(loanDTO);
    	return ResponseEntity.status(HttpStatus.CREATED).body(createdLoan);
    	
    }

   
    
    
    
}

