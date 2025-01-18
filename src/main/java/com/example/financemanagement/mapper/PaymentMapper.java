package com.example.financemanagement.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.financemanagement.dto.PaymentDTO;
import com.example.financemanagement.model.Loan;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
	
	@Mapping(source = "id", target = "loanId")
    @Mapping(source = "fileNumber", target = "fileNumber")
    @Mapping(source = "loanAmount", target = "loanAmount")
    @Mapping(source = "emi", target = "emi")
    @Mapping(source = "tenure", target = "tenure")
    @Mapping(source = "paidEmiCount", target = "paidEmiCount")
    @Mapping(source = "remainingEmiCount", target = "remainingEmi")
    @Mapping(source = "customer.name", target = "customerName", defaultValue = "N/A")
    @Mapping(source = "vehicle.vehicleNumber", target = "vehicleNumber", defaultValue = "N/A")
	PaymentDTO loanToPaymentDTO(Loan loan);

}
