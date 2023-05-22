package com.meters.controller.catalogs;


import com.meters.requests.create.catalogs.DealTypeRequest;
import com.meters.entities.catalogs.DealType;
import com.meters.service.catalogs.DealTypeService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/rest/deal-types")
@RequiredArgsConstructor
public class DealTypeController {

    private final DealTypeService dealTypeService;

    @GetMapping
    public ResponseEntity<List<DealType>> getAllDealTypes() {
        List<DealType> dealTypes = dealTypeService.findAll();
        return new ResponseEntity<>(dealTypes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DealType> getDealTypeById(@PathVariable Long id) {
        Optional<DealType> dealType = dealTypeService.findById(id);
        if(dealType.isPresent()) {
            return new ResponseEntity<> (dealType.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<DealType> createDealType(@Valid @RequestBody DealTypeRequest dealTypeRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

        DealType dealType = dealTypeService.createDealType(dealTypeRequest);
        return new ResponseEntity<>(dealType, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DealType> updateDealType(@Valid @RequestBody DealTypeRequest dealTypeRequest, @PathVariable("id") Long id, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

        DealType dealType = dealTypeService.updateDealType(id, dealTypeRequest);
        return new ResponseEntity<>(dealType, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDealType(@PathVariable("id") Long id) {
        dealTypeService.deleteById(id);
        return new ResponseEntity<>(id + " id is deleted forever", HttpStatus.OK);
    }

}
