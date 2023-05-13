package com.meters.mappers;

import com.meters.dto.CompanyDto;
import com.meters.entities.Company;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class CompanyMapper {

    private final ModelMapper modelMapper;

    public Company toEntity(CompanyDto companyDto) {
        return modelMapper.map(companyDto, Company.class);
    }

    public CompanyDto toDto(Company company) {
        return modelMapper.map(company, CompanyDto.class);
    }
    public Company updateCompany(CompanyDto companyDto, Company company) {

        if(companyDto.getCompanyName() != null) {
            company.setCompanyName(companyDto.getCompanyName());
        }
        if(companyDto.getUnpNum() != null) {
            company.setUnpNum(companyDto.getUnpNum());
        }
        if(companyDto.getDirectorFullName() != null) {
            company.setDirectorFullName(companyDto.getDirectorFullName());
        }
        if(companyDto.getPhoneNum() != null) {
            company.setPhoneNum(companyDto.getPhoneNum());
        }
        if(companyDto.getDateCreateCompany() != null) {
            company.setDateCreateCompany(companyDto.getDateCreateCompany());
        }
        if(companyDto.getAddress() != null) {
            company.setAddress(companyDto.getAddress());
        }
        if(companyDto.getDirectorName() != null) {
            company.setDirectorName(companyDto.getDirectorName());
        }
        if(companyDto.getDirectorSurname() != null) {
            company.setDirectorSurname(companyDto.getDirectorSurname());
        }
        if(companyDto.getCheckingAccount() != null) {
            company.setCheckingAccount(companyDto.getCheckingAccount());
        }
        if(companyDto.getBankName() != null) {
            company.setBankName(companyDto.getBankName());
        }
        company.setChanged(Timestamp.valueOf(LocalDateTime.now()));

        return company;
    }
}
