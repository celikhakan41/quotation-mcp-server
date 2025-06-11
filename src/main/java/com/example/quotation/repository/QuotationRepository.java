package com.example.quotation.repository;

import com.example.quotation.model.QuotationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuotationRepository extends JpaRepository<QuotationEntity, String> {}