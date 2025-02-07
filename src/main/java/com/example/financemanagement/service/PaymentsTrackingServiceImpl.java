package com.example.financemanagement.service;

import com.example.financemanagement.dto.PaymentsTrackingDTO;
import com.example.financemanagement.mapper.PaymentsTrackingMapper;
import com.example.financemanagement.repository.PaymentsTrackingRepository;
import com.example.financemanagement.service.PaymentsTrackingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentsTrackingServiceImpl implements PaymentsTrackingService {

    private final PaymentsTrackingRepository paymentsTrackingRepository;
    private final PaymentsTrackingMapper paymentsTrackingMapper;

    public PaymentsTrackingServiceImpl(PaymentsTrackingRepository paymentsTrackingRepository, PaymentsTrackingMapper paymentsTrackingMapper) {
        this.paymentsTrackingRepository = paymentsTrackingRepository;
        this.paymentsTrackingMapper = paymentsTrackingMapper;
    }

    @Override
    public List<PaymentsTrackingDTO> getAllPayments() {
        return paymentsTrackingRepository.findAll()
                .stream()
                .map(paymentsTrackingMapper::toDTO)  // Use MapStruct mapper to convert entities to DTOs
                .collect(Collectors.toList());
    }
}
