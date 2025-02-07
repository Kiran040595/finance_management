package com.example.financemanagement.mapper;

import com.example.financemanagement.dto.PaymentsTrackingDTO;
import com.example.financemanagement.model.PaymentsTracking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentsTrackingMapper {

    @Mapping(source = "loan.fileNumber", target = "fileNumber") // Maps Loan's fileNumber to DTO
    PaymentsTrackingDTO toDTO(PaymentsTracking payment);

    @Mapping(source = "transactionType", target = "transactionType")
    @Mapping(source = "billNumber", target = "billNumber")
    @Mapping(source = "transactionDate", target = "transactionDate")
    @Mapping(source = "amount", target = "amount")
    PaymentsTracking toEntity(PaymentsTrackingDTO paymentsTrackingDTO);
}
