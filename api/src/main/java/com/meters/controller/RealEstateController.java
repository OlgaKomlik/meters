package com.meters.controller;

import com.meters.dto.RealEstateDto;
import com.meters.entities.RealEstate;
import com.meters.service.RealEstateService;
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
@RequestMapping("/rest/real-estates")
@RequiredArgsConstructor
public class RealEstateController {
    private final RealEstateService realEstateService;

    @GetMapping
    public ResponseEntity<Object> getAllRealEstates() {
        List<RealEstate> realEstates = realEstateService.findAll();
        return new ResponseEntity<>(realEstates, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<RealEstate>> getRealEstateById(@PathVariable Long id) {
        return ResponseEntity.ok(realEstateService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Optional<RealEstate>> createRealEstate(@Valid @RequestBody RealEstateDto realEstateDto) {
        Optional<RealEstate> realEstate = realEstateService.createRealEstate(realEstateDto);
        return new ResponseEntity<>(realEstate, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Optional<RealEstate>> updateRealEstate(@Valid @RequestBody RealEstateDto realEstateDto, @PathVariable("id") Long id) {
        Optional<RealEstate> realEstate = realEstateService.updateRealEstate(id, realEstateDto);
        return new ResponseEntity<>(realEstate, HttpStatus.OK);
    }

    @PutMapping("/{id}/soft_delete")
    public ResponseEntity<String> softDeleteRealEstate(@PathVariable("id") Long id) {
        realEstateService.softDelete(id);
        return new ResponseEntity<>(id + " id is deleted", HttpStatus.OK);
    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteRealEstate(@PathVariable("id") Long id) {
        realEstateService.deleteById(id);
        return new ResponseEntity<>(id + " id is deleted forever", HttpStatus.OK);
    }

    @PutMapping("/{id}/restore")
    public ResponseEntity<Optional<RealEstate>> restoreDeletedRealEstate(@PathVariable("id") Long id) {
        Optional<RealEstate> realEstate = realEstateService.restoreDeletedRealEstate(id);
        return new ResponseEntity<>(realEstate, HttpStatus.OK);
    }
}
