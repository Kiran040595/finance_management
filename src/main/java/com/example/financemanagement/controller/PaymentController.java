package com.example.financemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired; // To inject services
import org.springframework.data.domain.Page; // To represent paginated results
import org.springframework.data.domain.PageRequest; // For pagination
import org.springframework.data.domain.Pageable; // Represents pagination information
import org.springframework.data.domain.Sort; // For sorting
import org.springframework.http.ResponseEntity; // For HTTP responses
import org.springframework.web.bind.annotation.CrossOrigin; // For handling CORS
import org.springframework.web.bind.annotation.GetMapping; // For HTTP GET
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping; // To map the base path
import org.springframework.web.bind.annotation.RequestParam; // For query parameters
import org.springframework.web.bind.annotation.RestController; // To mark it as a REST controller

import com.example.financemanagement.dto.PaymentDTO;
import com.example.financemanagement.dto.PaymentRequestDTO;
import com.example.financemanagement.dto.PaymentResponse;
import com.example.financemanagement.model.LoanEmi;
import com.example.financemanagement.service.LoanService;
import com.example.financemanagement.service.PaymentService;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;
	
	@Autowired
    private LoanService loanService;

	@GetMapping("/payments")
	public ResponseEntity<PaymentResponse> getPayments(
	        @RequestParam(defaultValue = "") String searchQuery,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size,
	        @RequestParam(defaultValue = "fileNumber") String sortKey,
	        @RequestParam(defaultValue = "asc") String sortDirection) {

	    Pageable pageable = PageRequest.of(page-1, size, 
	            "desc".equalsIgnoreCase(sortDirection) 
	            ? Sort.by(sortKey).descending() 
	            : Sort.by(sortKey).ascending());
	    PaymentResponse response = paymentService.getAllPayments(searchQuery, pageable);
	    return ResponseEntity.ok(response);
	}


	@GetMapping("/api/payment/{loanId}")
	public PaymentDTO getPaymentDetails(@PathVariable Long loanId) {
		return paymentService.getPaymentDetails(loanId);
	}
	
	// New endpoint: Get loan details by fileNumber
    @GetMapping("/payments/{fileNumber}")
    public PaymentDTO getLoanDetailsByFileNumber(@PathVariable String fileNumber) {
    	return paymentService.getLoanDetailsByFileNumber(fileNumber);
        
    }
    
    @PostMapping("/payments/pay/{fileNumber}")
    public LoanEmi payEmi(@RequestBody  PaymentRequestDTO paymentRequest) {
        return paymentService.payEmi(paymentRequest.getFileNumber(), paymentRequest.getEmiNumber(), paymentRequest.getPaymentAmount());
    }
    
    
    

}
