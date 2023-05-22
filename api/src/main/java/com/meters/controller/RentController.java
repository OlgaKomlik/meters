package com.meters.controller;

import com.meters.requests.create.RentRequest;
import com.meters.entities.Rent;
import com.meters.requests.update.RentUpdateRequest;
import com.meters.service.RentService;
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
@RequestMapping("/rest/rents")
@RequiredArgsConstructor
public class RentController {

    private final RentService rentService;

    @Value("${page-capacity.rent}")
    private Integer pageCapacity;

    @GetMapping
    public ResponseEntity<List<Rent>> getAllRents() {
        List<Rent> rents = rentService.findAll();
        return new ResponseEntity<>(rents, HttpStatus.OK);
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Page<Rent>> getAllRentsWithPageAndSort(@PathVariable int page) {

        Pageable pageable = PageRequest.of(page, pageCapacity, Sort.by("id").ascending());

        Page<Rent> rents = rentService.findAll(pageable);

        if (rents.hasContent()) {
            return new ResponseEntity<>(rents, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rent> getRentById(@PathVariable Long id) {

        Optional<Rent> rent = rentService.findById(id);
        if(rent.isPresent()) {
            return new ResponseEntity<> (rent.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping
    public ResponseEntity<Rent> createRent(@Valid @RequestBody RentRequest rentRequest,
                                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

        Rent rent = rentService.createRent(rentRequest);
        return new ResponseEntity<>(rent, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rent> updateRent(@Valid @RequestBody RentUpdateRequest rentRequest,
                                           @PathVariable("id") Long id, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

        Rent rent = rentService.updateRent(id, rentRequest);
        return new ResponseEntity<>(rent, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRent(@PathVariable("id") Long id) {
        rentService.deleteById(id);
        return new ResponseEntity<>(id + " id is deleted forever", HttpStatus.OK);
    }
}
