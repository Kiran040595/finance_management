package com.example.financemanagement.service;

import com.example.financemanagement.exception.CustomerNotFoundException;
import com.example.financemanagement.model.Customer;
import com.example.financemanagement.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        Customer customer =  customerRepository.findById(id)
        		.orElseThrow(()-> new CustomerNotFoundException("Customer With ID"+id+"not Found"));
        return Optional.ofNullable(customer);
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
