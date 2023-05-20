package com.meters.controller;

import com.meters.requests.RentRequest;
import com.meters.entities.Rent;
import com.meters.service.RentService;
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
@RequestMapping("/rest/rents")
@RequiredArgsConstructor
public class RentController {

    private final RentService rentService;

    @Value("${rent.page-capacity}")
    private Integer pageCapacity;

    @GetMapping
    public ResponseEntity<Object> getAllRents() {
        List<Rent> rents = rentService.findAll();
        return new ResponseEntity<>(rents, HttpStatus.OK);
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Object> getAllRentsWithPageAndSort(@PathVariable int page) {

        Pageable pageable = PageRequest.of(page, pageCapacity, Sort.by("id").ascending());

        Page<Rent> rents = rentService.findAll(pageable);

        if (rents.hasContent()) {
            return new ResponseEntity<>(rents, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Rent>> getRentById(@PathVariable Long id) {
        return ResponseEntity.ok(rentService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<Optional<Rent>> createRent(@Valid @RequestBody RentRequest rentRequest) {
        Optional<Rent> rent = rentService.createRent(rentRequest);
        return new ResponseEntity<>(rent, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<Rent>> updateRent(@Valid @RequestBody RentRequest rentRequest, @PathVariable("id") Long id) {
        Optional<Rent> rent = rentService.updateRent(id, rentRequest);
        return new ResponseEntity<>(rent, HttpStatus.OK);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivateRent(@PathVariable("id") Long id) {
        rentService.deactivate(id);
        return new ResponseEntity<>(id + " id is deleted", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRent(@PathVariable("id") Long id) {
        rentService.deleteById(id);
        return new ResponseEntity<>(id + " id is deleted forever", HttpStatus.OK);
    }

    @PutMapping("/{id}/restore")
    public ResponseEntity<Optional<Rent>> activateRent(@PathVariable("id") Long id) {
        Optional<Rent> rent = rentService.activateRent(id);
        return new ResponseEntity<>(rent, HttpStatus.OK);
    }
}
