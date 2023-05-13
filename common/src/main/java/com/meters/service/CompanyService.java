package com.meters.service;

import com.meters.dto.CompanyDto;
import com.meters.entities.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyService {
    Optional<Company> createCompany(CompanyDto companyDto);

    Optional<Company> updateCompany(Long id, CompanyDto companyDto);

    List<Company> findAll();
    //List<Company> findAll(int page, int size);

    Optional<Company> findById(Long id);
    Optional<Company> restoreDeletedCompany(Long id);

    void deleteById(Long id);
    Company softDelete(Long id);
}
