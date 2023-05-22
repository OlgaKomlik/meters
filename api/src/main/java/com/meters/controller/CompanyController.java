package com.meters.controller;

import com.meters.requests.create.CompanyRequest;
import com.meters.entities.Company;
import com.meters.requests.update.CompanyUpdateRequest;
import com.meters.service.CompanyService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/rest/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @Value("${page-capacity.company}")
    private Integer pageCapacity;

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        List<Company> companies = companyService.findAll();
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Page<Company>> getAllCompaniesWithPageAndSort(@PathVariable int page) {

        Pageable pageable = PageRequest.of(page, pageCapacity, Sort.by("id").ascending());

        Page<Company> companies = companyService.findAll(pageable);

        if (companies.hasContent()) {
            return new ResponseEntity<>(companies, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {

        Optional<Company> company = companyService.findById(id);
        if(company.isPresent()) {
            return new ResponseEntity<> (company.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Company> createCompany(@Valid @RequestBody CompanyRequest companyRequest,
                                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

        Company company = companyService.createCompany(companyRequest);
        return new ResponseEntity<>(company, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(@Valid @RequestBody CompanyUpdateRequest companyRequest,
                                                 @PathVariable("id") Long id, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

        Company company = companyService.updateCompany(id, companyRequest);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable("id") Long id) {
        companyService.deleteById(id);
        return new ResponseEntity<>(id + " id is deleted forever", HttpStatus.OK);
    }

    @GetMapping("/search/unp")
    public ResponseEntity<Company> getCompanyByUnp(@RequestParam String unp) {

        Optional<Company> company = companyService.findCompanyByUnpNum(unp);
        if(company.isPresent()) {
            return new ResponseEntity<> (company.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/search/name")
    public ResponseEntity<List<Company>> getCompaniesByName(@RequestParam String query) {
        List<Company> companies = companyService.findCompaniesByCompanyNameIsContainingIgnoreCase(query);
        return ResponseEntity.ok(companies);
    }
}