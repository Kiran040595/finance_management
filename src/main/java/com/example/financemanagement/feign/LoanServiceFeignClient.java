package com.example.financemanagement.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.financemanagement.dto.LoanDTO;
import com.example.financemanagement.dto.LoanRequestDTO;
import com.example.financemanagement.dto.LoanResponseDTO;

@FeignClient(name = "loan-service")
public interface LoanServiceFeignClient {
	
	@PostMapping("/api/loans")
	ResponseEntity<String> createLoan(@RequestBody LoanRequestDTO loanRequestDTO);
	
	@GetMapping("api/loans/loans")
	List<LoanResponseDTO> getAllLoans();
}
