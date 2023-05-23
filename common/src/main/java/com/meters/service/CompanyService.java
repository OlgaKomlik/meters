package com.meters.service;

import com.meters.entities.Company;
import com.meters.requests.create.CompanyRequest;
import com.meters.requests.update.CompanyUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CompanyService {
    Company createCompany(CompanyRequest companyRequest);

    Company updateCompany(Long id, CompanyUpdateRequest companyRequest);

    List<Company> findAll();

    Page<Company> findAll(Pageable pageable);

    Optional<Company> findById(Long id);

    void deleteById(Long id);

    Optional<Company> findCompanyByUnpNum(String unpNum);

    List<Company> findCompaniesByCompanyNameIsContainingIgnoreCase(String query);
}
