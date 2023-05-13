package com.meters.controller;

import com.meters.dto.RentDto;
import com.meters.entities.Rent;
import com.meters.service.RentService;
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
@RequestMapping("/rest/rents")
@RequiredArgsConstructor
public class RentController {

    private final RentService rentService;

    @GetMapping
    public ResponseEntity<Object> getAllRents() {
        List<Rent> rents = rentService.findAll();
        return new ResponseEntity<>(rents, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Rent>> getRentById(@PathVariable Long id) {
        return ResponseEntity.ok(rentService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Optional<Rent>> createRent(@Valid @RequestBody RentDto rentDto) {
        Optional<Rent> rent = rentService.createRent(rentDto);
        return new ResponseEntity<>(rent, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Optional<Rent>> updateRent(@Valid @RequestBody RentDto rentDto, @PathVariable("id") Long id) {
        Optional<Rent> rent = rentService.updateRent(id, rentDto);
        return new ResponseEntity<>(rent, HttpStatus.OK);
    }

    @PutMapping("/{id}/soft_delete")
    public ResponseEntity<String> softDeleteRent(@PathVariable("id") Long id) {
        rentService.softDelete(id);
        return new ResponseEntity<>(id + " id is deleted", HttpStatus.OK);
    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteRent(@PathVariable("id") Long id) {
        rentService.deleteById(id);
        return new ResponseEntity<>(id + " id is deleted forever", HttpStatus.OK);
    }

    @PutMapping("/{id}/restore")
    public ResponseEntity<Optional<Rent>> restoreDeletedRent(@PathVariable("id") Long id) {
        Optional<Rent> rent = rentService.restoreDeletedRent(id);
        return new ResponseEntity<>(rent, HttpStatus.OK);
    }
}
