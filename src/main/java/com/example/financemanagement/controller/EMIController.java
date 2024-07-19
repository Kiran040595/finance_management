package com.example.financemanagement.controller;

import com.example.financemanagement.model.EMI;
import com.example.financemanagement.service.EMIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emis")
public class EMIController {
    @Autowired
    private EMIService emiService;

    @GetMapping("/loan/{loanId}")
    public List<EMI> getEMIsByLoanId(@PathVariable Long loanId) {
        return emiService.getEMIsByLoanId(loanId);
    }

    @PostMapping
    public EMI createEMI(@RequestBody EMI emi) {
        return emiService.saveEMI(emi);
    }

    @PutMapping("/{id}")
    public EMI updateEMI(@PathVariable Long id, @RequestBody EMI emi) {
        emi.setId(id);
        return emiService.updateEMI(emi);
    }

    @DeleteMapping("/{id}")
    public void deleteEMI(@PathVariable Long id) {
        emiService.deleteEMI(id);
    }
}
