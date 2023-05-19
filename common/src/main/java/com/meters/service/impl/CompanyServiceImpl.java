package com.meters.service.impl;

import com.meters.requests.CompanyRequest;
import com.meters.entities.Company;
import com.meters.exceptoins.EntityIsDeletedException;
import com.meters.exceptoins.EntityNotFoundException;
import com.meters.mappers.CompanyMapper;
import com.meters.repository.CompanyRepository;
import com.meters.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    public Optional<Company> createCompany(CompanyRequest companyRequest) {
        Company company = companyMapper.toEntity(companyRequest);
        company.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        company.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(companyRepository.save(company));
    }

    @Override
    public Optional<Company> updateCompany(Long id, CompanyRequest companyRequest) {
        Company company = findCompany(id);
        companyMapper.updateCompany(companyRequest, company);
        return Optional.of(companyRepository.save(company));
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
        if(company.isDeleted()) {
            throw new EntityIsDeletedException("Company is deleted");
        }
        return Optional.of(company);
    }

    @Override
    public Optional<Company> restoreDeletedCompany(Long id) {
        Company company = findCompany(id);
        company.setDeleted(false);
        company.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(companyRepository.save(company));
    }

    @Override
    public void deleteById(Long id) {
        companyRepository.deleteById(id);
    }

    @Override
    public Company deactivate(Long id) {
        Company company = findCompany(id);
        company.setDeleted(true);
        company.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return companyRepository.save(company);
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
