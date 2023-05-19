package com.meters.controller;

import com.meters.requests.DealRequest;
import com.meters.entities.Deal;
import com.meters.service.DealService;
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
import org.springframework.web.bind.annotation.RestController;

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


    @GetMapping("/page/{page}")
    public ResponseEntity<Object> getAllDealsWithPageAndSort(@PathVariable int page) {

        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").ascending());

        Page<Deal> deals = dealService.findAll(pageable);

        if (deals.hasContent()) {
            return new ResponseEntity<>(deals, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Deal>> getDealById(@PathVariable Long id) {
        return ResponseEntity.ok(dealService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Optional<Deal>> createDeal(@Valid @RequestBody DealRequest dealRequest) {
        Optional<Deal> deal = dealService.createDeal(dealRequest);
        return new ResponseEntity<>(deal, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Optional<Deal>> updateDeal(@Valid @RequestBody DealRequest dealRequest, @PathVariable("id") Long id) {
        Optional<Deal> deal = dealService.updateDeal(id, dealRequest);
        return new ResponseEntity<>(deal, HttpStatus.OK);
    }

    @PutMapping("/{id}/soft_delete")
    public ResponseEntity<String> deactivateDeal(@PathVariable("id") Long id) {
        dealService.deactivate(id);
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
