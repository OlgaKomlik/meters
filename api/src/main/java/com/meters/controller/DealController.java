package com.meters.controller;

import com.meters.requests.create.DealRequest;
import com.meters.entities.Deal;
import com.meters.requests.update.DealUpdateRequest;
import com.meters.service.DealService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/rest/deals")
@RequiredArgsConstructor
public class DealController {

    private final DealService dealService;

    @Value("${page-capacity.deal}")
    private Integer pageCapacity;

    @GetMapping
    public ResponseEntity<List<Deal>> getAllDeals() {
        List<Deal> deals = dealService.findAll();
        return new ResponseEntity<>(deals, HttpStatus.OK);
    }


    @GetMapping("/page/{page}")
    public ResponseEntity<Page<Deal>> getAllDealsWithPageAndSort(@PathVariable int page) {

        Pageable pageable = PageRequest.of(page, pageCapacity, Sort.by("id").ascending());

        Page<Deal> deals = dealService.findAll(pageable);

        if (deals.hasContent()) {
            return new ResponseEntity<>(deals, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Deal> getDealById(@PathVariable Long id) {

        Optional<Deal> deal = dealService.findById(id);
        if(deal.isPresent()) {
            return new ResponseEntity<> (deal.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Deal> createDeal(@Valid @RequestBody DealRequest dealRequest,
                                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

        Deal deal = dealService.createDeal(dealRequest);
        return new ResponseEntity<>(deal, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Deal> updateDeal(@Valid @RequestBody DealUpdateRequest dealRequest, @PathVariable("id") Long id ,
                                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

        Deal deal = dealService.updateDeal(id, dealRequest);
        return new ResponseEntity<>(deal, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDeal(@PathVariable("id") Long id) {
        dealService.deleteById(id);
        return new ResponseEntity<>(id + " id is deleted forever", HttpStatus.OK);
    }
}
