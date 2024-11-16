package com.example.financemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.financemanagement.model.Guarantor;

public interface GuarantorRepository extends JpaRepository<Guarantor, Long> {

}
