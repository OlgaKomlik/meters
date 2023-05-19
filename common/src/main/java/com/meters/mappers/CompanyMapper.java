package com.meters.mappers;

import com.meters.requests.CompanyRequest;
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

    public Company toEntity(CompanyRequest companyRequest) {
        Company company= modelMapper.map(companyRequest, Company.class);
        company.setDirectorFullName(companyRequest.getDirectorName() + " " + companyRequest.getDirectorSurname());
        return company;
    }

    public Company updateCompany(CompanyRequest companyRequest, Company company) {

        if(companyRequest.getCompanyName() != null) {
            company.setCompanyName(companyRequest.getCompanyName());
        }
        if(companyRequest.getUnpNum() != null) {
            company.setUnpNum(companyRequest.getUnpNum());
        }
        if(companyRequest.getPhoneNum() != null) {
            company.setPhoneNum(companyRequest.getPhoneNum());
        }
        if(companyRequest.getDateCreateCompany() != null) {
            company.setDateCreateCompany(companyRequest.getDateCreateCompany());
        }
        if(companyRequest.getAddress() != null) {
            company.setAddress(companyRequest.getAddress());
        }
        if(companyRequest.getDirectorName() != null) {
            company.setDirectorName(companyRequest.getDirectorName());
        }
        if(companyRequest.getDirectorSurname() != null) {
            company.setDirectorSurname(companyRequest.getDirectorSurname());
        }
        if(companyRequest.getCheckingAccount() != null) {
            company.setCheckingAccount(companyRequest.getCheckingAccount());
        }
        if(companyRequest.getBankName() != null) {
            company.setBankName(companyRequest.getBankName());
        }
        company.setDirectorFullName(company.getDirectorName() + " " + company.getDirectorSurname());
        company.setChanged(Timestamp.valueOf(LocalDateTime.now()));

        return company;
    }
}
