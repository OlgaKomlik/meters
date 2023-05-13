package com.meters.service.impl;

import com.meters.dto.CompanyDto;
import com.meters.entities.Company;
import com.meters.exceptoins.EntityIsDeletedException;
import com.meters.exceptoins.EntityNotFoundException;
import com.meters.mappers.CompanyMapper;
import com.meters.repository.CompanyRepository;
import com.meters.service.CompanyService;
import lombok.RequiredArgsConstructor;
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
    public Optional<Company> createCompany(CompanyDto companyDto) {
        Company company = companyMapper.toEntity(companyDto);
        company.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        company.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(companyRepository.save(company));
    }

    @Override
    public Optional<Company> updateCompany(Long id, CompanyDto companyDto) {
        Company company = findCompany(id);
        companyMapper.updateCompany(companyDto, company);
        return Optional.of(companyRepository.save(company));
    }

    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public Optional<Company> findById(Long id) {
        Company company = findCompany(id);
        if(company.getIsDeleted().equals(Boolean.TRUE)) {
            throw new EntityIsDeletedException("Company is deleted");
        }
        return Optional.of(company);
    }

    @Override
    public Optional<Company> restoreDeletedCompany(Long id) {
        Company company = findCompany(id);
        company.setIsDeleted(false);
        company.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(companyRepository.save(company));
    }

    @Override
    public void deleteById(Long id) {
        companyRepository.deleteById(id);
    }

    @Override
    public Company softDelete(Long id) {
        Company company = findCompany(id);
        company.setIsDeleted(true);
        company.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return companyRepository.save(company);
    }

    private Company findCompany(Long id) {
        return companyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Company could not be found"));
    }
}
