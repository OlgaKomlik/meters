package com.meters.service.impl;

import com.meters.entities.Company;
import com.meters.exceptoins.EntityIsDeletedException;
import com.meters.exceptoins.EntityNotFoundException;
import com.meters.mappers.CompanyMapper;
import com.meters.repository.CompanyRepository;
import com.meters.requests.create.CompanyRequest;
import com.meters.requests.update.CompanyUpdateRequest;
import com.meters.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    @Transactional
    public Company createCompany(CompanyRequest companyRequest) {
        Company company = companyMapper.toEntity(companyRequest);
        return companyRepository.save(company);
    }

    @Override
    @Transactional
    public Company updateCompany(Long id, CompanyUpdateRequest companyRequest) {
        Company company = findCompany(id);
        companyMapper.updateCompany(companyRequest, company);
        return companyRepository.save(company);
    }

    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public Page<Company> findAll(Pageable pageable) {
        return companyRepository.findAll(pageable);
    }

    @Override
    public Optional<Company> findById(Long id) {
        Company company = findCompany(id);
        if (company.isDeleted()) {
            throw new EntityIsDeletedException("Company is deleted");
        }
        return Optional.of(company);
    }


    @Override
    public void deleteById(Long id) {
        companyRepository.deleteById(id);
    }


    private Company findCompany(Long id) {
        return companyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Company could not be found"));
    }

    public Optional<Company> findCompanyByUnpNum(String unpNum) {
        return companyRepository.findCompanyByUnpNum(unpNum);
    }

    public List<Company> findCompaniesByCompanyNameIsContainingIgnoreCase(String query) {
        return companyRepository.findCompaniesByCompanyNameIsContainingIgnoreCase(query);
    }
}
