package com.meters.controller.catalogs;


import com.meters.dto.catalogs.DealTypeDto;
import com.meters.entities.catalogs.DealType;
import com.meters.service.catalogs.DealTypeService;
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
@RequestMapping("/rest/deal-types")
@RequiredArgsConstructor
public class DealTypeController {

    private final DealTypeService dealTypeService;

    @GetMapping
    public ResponseEntity<Object> getAllDealTypes() {
        List<DealType> dealTypes = dealTypeService.findAll();
        return new ResponseEntity<>(dealTypes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<DealType>> getDealTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(dealTypeService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Optional<DealType>> createDealType(@Valid @RequestBody DealTypeDto dealTypeDto) {
        Optional<DealType> dealType = dealTypeService.createDealType(dealTypeDto);
        return new ResponseEntity<>(dealType, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Optional<DealType>> updateDealType(@Valid @RequestBody DealTypeDto dealTypeDto, @PathVariable("id") Long id) {
        Optional<DealType> dealType = dealTypeService.updateDealType(id, dealTypeDto);
        return new ResponseEntity<>(dealType, HttpStatus.OK);
    }

    @PutMapping("/{id}/soft_delete")
    public ResponseEntity<String> softDeleteDealType(@PathVariable("id") Long id) {
        dealTypeService.softDelete(id);
        return new ResponseEntity<>(id + " id is deleted", HttpStatus.OK);
    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteDealType(@PathVariable("id") Long id) {
        dealTypeService.deleteById(id);
        return new ResponseEntity<>(id + " id is deleted forever", HttpStatus.OK);
    }

    @PutMapping("/{id}/restore")
    public ResponseEntity<Optional<DealType>> restoreDeletedDealType(@PathVariable("id") Long id) {
        Optional<DealType> dealType = dealTypeService.restoreDeletedDealType(id);
        return new ResponseEntity<>(dealType, HttpStatus.OK);
    }
}