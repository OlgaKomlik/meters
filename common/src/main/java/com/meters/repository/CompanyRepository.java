package com.meters.repository;

import com.meters.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findCompanyByUnpNum(String unpNum);

    List<Company> findCompaniesByCompanyNameIsContainingIgnoreCase(String query);
}