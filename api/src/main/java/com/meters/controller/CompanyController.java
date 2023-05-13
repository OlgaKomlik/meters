package com.meters.controller;

import com.meters.dto.CompanyDto;
import com.meters.entities.Company;
import com.meters.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping
    public ResponseEntity<Object> getAllCompanies() {
        List<Company> companies = companyService.findAll();
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Company>> getCompanyById(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Optional<Company>> createCompany(@Valid @RequestBody CompanyDto companyDto) {
        Optional<Company> company = companyService.createCompany(companyDto);
        return new ResponseEntity<>(company, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Optional<Company>> updateCompany(@Valid @RequestBody CompanyDto companyDto, @PathVariable("id") Long id) {
        Optional<Company> company = companyService.updateCompany(id, companyDto);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @PutMapping("/{id}/soft_delete")
    public ResponseEntity<String> softDeleteCompany(@PathVariable("id") Long id) {
        companyService.softDelete(id);
        return new ResponseEntity<>(id + " id is deleted", HttpStatus.OK);
    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteCompany(@PathVariable("id") Long id) {
        companyService.deleteById(id);
        return new ResponseEntity<>(id + " id is deleted forever", HttpStatus.OK);
    }

    @PutMapping("/{id}/restore")
    public ResponseEntity<Optional<Company>> restoreDeletedCompany(@PathVariable("id") Long id) {
        Optional<Company> company = companyService.restoreDeletedCompany(id);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }
}