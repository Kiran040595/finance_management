package com.example.financemanagement.mapper;

import com.example.financemanagement.dto.LoanDTO;
import com.example.financemanagement.model.Loan;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanMapper {
    Loan toEntity(LoanDTO loanDTO);
    LoanDTO toDto(Loan loan);
}
