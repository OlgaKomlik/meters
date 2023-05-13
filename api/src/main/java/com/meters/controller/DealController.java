package com.meters.controller;

import com.meters.dto.DealDto;
import com.meters.entities.Deal;
import com.meters.service.DealService;
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
@RequestMapping("/rest/deals")
@RequiredArgsConstructor
public class DealController {

    private final DealService dealService;

    @GetMapping
    public ResponseEntity<Object> getAllDeals() {
        List<Deal> deals = dealService.findAll();
        return new ResponseEntity<>(deals, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Deal>> getDealById(@PathVariable Long id) {
        return ResponseEntity.ok(dealService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Optional<Deal>> createDeal(@Valid @RequestBody DealDto dealDto) {
        Optional<Deal> deal = dealService.createDeal(dealDto);
        return new ResponseEntity<>(deal, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Optional<Deal>> updateDeal(@Valid @RequestBody DealDto dealDto, @PathVariable("id") Long id) {
        Optional<Deal> deal = dealService.updateDeal(id, dealDto);
        return new ResponseEntity<>(deal, HttpStatus.OK);
    }

    @PutMapping("/{id}/soft_delete")
    public ResponseEntity<String> softDeleteDeal(@PathVariable("id") Long id) {
        dealService.softDelete(id);
        return new ResponseEntity<>(id + " id is deleted", HttpStatus.OK);
    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteDeal(@PathVariable("id") Long id) {
        dealService.deleteById(id);
        return new ResponseEntity<>(id + " id is deleted forever", HttpStatus.OK);
    }

    @PutMapping("/{id}/restore")
    public ResponseEntity<Optional<Deal>> restoreDeletedDeal(@PathVariable("id") Long id) {
        Optional<Deal> deal = dealService.restoreDeletedDeal(id);
        return new ResponseEntity<>(deal, HttpStatus.OK);
    }
}
