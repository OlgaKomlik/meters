package com.meters.service;

import com.meters.entities.Company;
import com.meters.requests.CompanyRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CompanyService {
    Optional<Company> createCompany(CompanyRequest companyRequest);

    Optional<Company> updateCompany(Long id, CompanyRequest companyRequest);

    List<Company> findAll();

    Page<Company> findAll(Pageable pageable);

    Optional<Company> findById(Long id);

    Optional<Company> activateCompany(Long id);

    void deleteById(Long id);

    Company deactivate(Long id);

    Optional<Company> findCompanyByUnpNum(String unpNum);

    List<Company> findCompaniesByCompanyNameIsContainingIgnoreCase(String query);
}
