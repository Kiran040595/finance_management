package com.example.financemanagement.service;

import com.example.financemanagement.model.EMI;
import com.example.financemanagement.repository.EMIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EMIService {
    @Autowired
    private EMIRepository emiRepository;

    public List<EMI> getEMIsByLoanId(Long loanId) {
        return emiRepository.findByLoanId(loanId);
    }

    public EMI saveEMI(EMI emi) {
        return emiRepository.save(emi);
    }

    public void deleteEMI(Long id) {
        emiRepository.deleteById(id);
    }

    public EMI updateEMI(EMI emi) {
        return emiRepository.save(emi);
    }
}
