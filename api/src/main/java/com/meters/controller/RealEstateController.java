package com.meters.controller;

import com.meters.requests.RealEstateRequest;
import com.meters.entities.RealEstate;
import com.meters.service.RealEstateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/real-estates")
@RequiredArgsConstructor
public class RealEstateController {
    private final RealEstateService realEstateService;

    @Value("${real-estate.page-capacity}")
    private Integer pageCapacity;

    @GetMapping
    public ResponseEntity<Object> getAllRealEstates() {
        List<RealEstate> realEstates = realEstateService.findAll();
        return new ResponseEntity<>(realEstates, HttpStatus.OK);
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Object> getAllRealEstatesWithPageAndSort(@PathVariable int page) {

        Pageable pageable = PageRequest.of(page, pageCapacity, Sort.by("id").ascending());

        Page<RealEstate> realEstates = realEstateService.findAll(pageable);

        if (realEstates.hasContent()) {
            return new ResponseEntity<>(realEstates, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<RealEstate>> getRealEstateById(@PathVariable Long id) {
        return ResponseEntity.ok(realEstateService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<Optional<RealEstate>> createRealEstate(@Valid @RequestBody RealEstateRequest realEstateRequest) {
        Optional<RealEstate> realEstate = realEstateService.createRealEstate(realEstateRequest);
        return new ResponseEntity<>(realEstate, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<RealEstate>> updateRealEstate(@Valid @RequestBody RealEstateRequest realEstateRequest, @PathVariable("id") Long id) {
        Optional<RealEstate> realEstate = realEstateService.updateRealEstate(id, realEstateRequest);
        return new ResponseEntity<>(realEstate, HttpStatus.OK);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivateRealEstate(@PathVariable("id") Long id) {
        realEstateService.deactivate(id);
        return new ResponseEntity<>(id + " id is deleted", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRealEstate(@PathVariable("id") Long id) {
        realEstateService.deleteById(id);
        return new ResponseEntity<>(id + " id is deleted forever", HttpStatus.OK);
    }

    @PutMapping("/{id}/restore")
    public ResponseEntity<Optional<RealEstate>> activateRealEstate(@PathVariable("id") Long id) {
        Optional<RealEstate> realEstate = realEstateService.activateRealEstate(id);
        return new ResponseEntity<>(realEstate, HttpStatus.OK);
    }
}
