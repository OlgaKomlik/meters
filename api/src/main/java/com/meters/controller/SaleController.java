package com.meters.controller;

import com.meters.entities.Sale;
import com.meters.requests.SaleRequest;
import com.meters.service.SaleService;
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
@RequestMapping("/rest/sales")
@RequiredArgsConstructor
public class SaleController {
    private final SaleService saleService;

    @Value("${sale.page-capacity}")
    private Integer pageCapacity;

    @GetMapping
    public ResponseEntity<Object> getAllSales() {
        List<Sale> sales = saleService.findAll();
        return new ResponseEntity<>(sales, HttpStatus.OK);
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Object> getAllSalesWithPageAndSort(@PathVariable int page) {

        Pageable pageable = PageRequest.of(page, pageCapacity, Sort.by("id").ascending());

        Page<Sale> sales = saleService.findAll(pageable);

        if (sales.hasContent()) {
            return new ResponseEntity<>(sales, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Sale>> getSaleById(@PathVariable Long id) {
        return ResponseEntity.ok(saleService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<Optional<Sale>> createSale(@Valid @RequestBody SaleRequest saleRequest) {
        Optional<Sale> sale = saleService.createSale(saleRequest);
        return new ResponseEntity<>(sale, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<Sale>> updateSale(@Valid @RequestBody SaleRequest saleRequest, @PathVariable("id") Long id) {
        Optional<Sale> sale = saleService.updateSale(id, saleRequest);
        return new ResponseEntity<>(sale, HttpStatus.OK);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivateSale(@PathVariable("id") Long id) {
        saleService.deactivate(id);
        return new ResponseEntity<>(id + " id is deleted", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSale(@PathVariable("id") Long id) {
        saleService.deleteById(id);
        return new ResponseEntity<>(id + " id is deleted forever", HttpStatus.OK);
    }

    @PutMapping("/{id}/restore")
    public ResponseEntity<Optional<Sale>> activateSale(@PathVariable("id") Long id) {
        Optional<Sale> sale = saleService.activateSale(id);
        return new ResponseEntity<>(sale, HttpStatus.OK);
    }
}
