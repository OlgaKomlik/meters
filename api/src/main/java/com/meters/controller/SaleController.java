package com.meters.controller;

import com.meters.dto.SaleDto;
import com.meters.entities.Sale;
import com.meters.service.SaleService;
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
@RequestMapping("/rest/sales")
@RequiredArgsConstructor
public class SaleController {
    private final SaleService saleService;

    @GetMapping
    public ResponseEntity<Object> getAllSales() {
        List<Sale> sales = saleService.findAll();
        return new ResponseEntity<>(sales, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Sale>> getSaleById(@PathVariable Long id) {
        return ResponseEntity.ok(saleService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Optional<Sale>> createSale(@Valid @RequestBody SaleDto saleDto) {
        Optional<Sale> sale = saleService.createSale(saleDto);
        return new ResponseEntity<>(sale, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Optional<Sale>> updateSale(@Valid @RequestBody SaleDto saleDto, @PathVariable("id") Long id) {
        Optional<Sale> sale = saleService.updateSale(id, saleDto);
        return new ResponseEntity<>(sale, HttpStatus.OK);
    }

    @PutMapping("/{id}/soft_delete")
    public ResponseEntity<String> softDeleteSale(@PathVariable("id") Long id) {
        saleService.softDelete(id);
        return new ResponseEntity<>(id + " id is deleted", HttpStatus.OK);
    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteSale(@PathVariable("id") Long id) {
        saleService.deleteById(id);
        return new ResponseEntity<>(id + " id is deleted forever", HttpStatus.OK);
    }

    @PutMapping("/{id}/restore")
    public ResponseEntity<Optional<Sale>> restoreDeletedSale(@PathVariable("id") Long id) {
        Optional<Sale> sale = saleService.restoreDeletedSale(id);
        return new ResponseEntity<>(sale, HttpStatus.OK);
    }
}
