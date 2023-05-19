package com.meters.controller;

import com.meters.requests.CompanyRequest;
import com.meters.entities.Company;
import com.meters.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/page/{page}")
    public ResponseEntity<Object> getAllCompaniesWithPageAndSort(@PathVariable int page) {

        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").ascending());

        Page<Company> companies = companyService.findAll(pageable);

        if (companies.hasContent()) {
            return new ResponseEntity<>(companies, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Company>> getCompanyById(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Optional<Company>> createCompany(@Valid @RequestBody CompanyRequest companyRequest) {
        Optional<Company> company = companyService.createCompany(companyRequest);
        return new ResponseEntity<>(company, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Optional<Company>> updateCompany(@Valid @RequestBody CompanyRequest companyRequest, @PathVariable("id") Long id) {
        Optional<Company> company = companyService.updateCompany(id, companyRequest);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @PutMapping("/{id}/soft_delete")
    public ResponseEntity<String> deactivateCompany(@PathVariable("id") Long id) {
        companyService.deactivate(id);
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

    @GetMapping("/unp/{unp}")
    public ResponseEntity<Optional<Company>> getCompanyByUnp(@PathVariable String unp) {
        return ResponseEntity.ok(companyService.findCompanyByUnpNum(unp));
    }

    @GetMapping("/name")
    public ResponseEntity<List<Company>> getCompaniesByName(@RequestParam String query) {
        return ResponseEntity.ok(companyService.findCompaniesByCompanyNameIsContainingIgnoreCase(query));
    }
}