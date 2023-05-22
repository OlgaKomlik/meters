package com.meters.controller;

import com.meters.requests.create.SaleRequest;
import com.meters.entities.Sale;
import com.meters.requests.update.SaleUpdateRequest;
import com.meters.service.SaleService;
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
@RequestMapping("/rest/sales")
@RequiredArgsConstructor
public class SaleController {
    private final SaleService saleService;

    @Value("${page-capacity.sale}")
    private Integer pageCapacity;

    @GetMapping
    public ResponseEntity<List<Sale>> getAllSales() {
        List<Sale> sales = saleService.findAll();
        return new ResponseEntity<>(sales, HttpStatus.OK);
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Page<Sale>> getAllSalesWithPageAndSort(@PathVariable int page) {

        Pageable pageable = PageRequest.of(page, pageCapacity, Sort.by("id").ascending());

        Page<Sale> sales = saleService.findAll(pageable);

        if (sales.hasContent()) {
            return new ResponseEntity<>(sales, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sale> getSaleById(@PathVariable Long id) {

        Optional<Sale> sale = saleService.findById(id);
        if(sale.isPresent()) {
            return new ResponseEntity<> (sale.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping
    public ResponseEntity<Sale> createSale(@Valid @RequestBody SaleRequest saleRequest,
                                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }


        Sale sale = saleService.createSale(saleRequest);
        return new ResponseEntity<>(sale, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sale> updateSale(@Valid @RequestBody SaleUpdateRequest saleRequest, @PathVariable("id") Long id,
                                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }


        Sale sale = saleService.updateSale(id, saleRequest);
        return new ResponseEntity<>(sale, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSale(@PathVariable("id") Long id) {
        saleService.deleteById(id);
        return new ResponseEntity<>(id + " id is deleted forever", HttpStatus.OK);
    }

}
