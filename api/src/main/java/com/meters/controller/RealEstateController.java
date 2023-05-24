package com.meters.controller;

import com.meters.entities.RealEstate;
import com.meters.exceptoins.ValidationException;
import com.meters.requests.create.RealEstateRequest;
import com.meters.requests.update.RealEstateUpdateRequest;
import com.meters.service.RealEstateService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/rest/estates")
@Tag(name = "RealEstateController", description = "Real Estate management methods")
@RequiredArgsConstructor
public class RealEstateController {

    private final RealEstateService realEstateService;

    @Value("${page-capacity.real-estate}")
    private Integer pageCapacity;

    @GetMapping
    public ResponseEntity<List<RealEstate>> getAllRealEstates() {
        List<RealEstate> realEstates = realEstateService.findAll();
        return new ResponseEntity<>(realEstates, HttpStatus.OK);
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Page<RealEstate>> getAllRealEstatesWithPageAndSort(@PathVariable int page) {

        Pageable pageable = PageRequest.of(page, pageCapacity, Sort.by("id").ascending());

        Page<RealEstate> realEstates = realEstateService.findAll(pageable);

        if (realEstates.hasContent()) {
            return new ResponseEntity<>(realEstates, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RealEstate> getRealEstateById(@PathVariable Long id) {
        Optional<RealEstate> realEstate = realEstateService.findById(id);
        if (realEstate.isPresent()) {
            return new ResponseEntity<>(realEstate.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<RealEstate> createRealEstate(@Valid @RequestBody RealEstateRequest realEstateRequest,
                                                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

        RealEstate realEstate = realEstateService.createRealEstate(realEstateRequest);
        return new ResponseEntity<>(realEstate, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RealEstate> updateRealEstate(@Valid @RequestBody RealEstateUpdateRequest realEstateRequest,
                                                       @PathVariable("id") Long id, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

        RealEstate realEstate = realEstateService.updateRealEstate(id, realEstateRequest);
        return new ResponseEntity<>(realEstate, HttpStatus.OK);
    }
}
