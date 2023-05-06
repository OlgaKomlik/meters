package com.meters.controller;

import com.meters.dto.ManagerDto;
import com.meters.entities.Manager;
import com.meters.repository.ManagerRepository;
import com.meters.service.ManagerService;
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

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("managers")
@RequiredArgsConstructor
public class ManagerController {
    private final ManagerService managerService;
    private final ManagerRepository managerRepository;

    @GetMapping
    public ResponseEntity<Object> getAllManagers() {
        List<Manager> managers = managerService.findAll();
        return new ResponseEntity<>(managers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Manager> getManagerById(@PathVariable Long id) {
        Optional<Manager> manager = managerService.findById(id);
        return manager.map(ResponseEntity::ok).orElseThrow();
    }

    @PostMapping("/create")
    public ResponseEntity<Optional<Manager>> createManager(@Valid @RequestBody ManagerDto managerDto) {
        Optional<Manager> manager = managerService.createManager(managerDto);
        return new ResponseEntity<>(manager, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Manager> updateManager(@Valid @RequestBody ManagerDto managerDto, @PathVariable("id") Long id) {
        Manager manager = managerService.updateManager(id, managerDto);
        return new ResponseEntity<>(manager, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteManager(@PathVariable("id") Long id) {

        Optional<Manager> managerOptional = managerService.findById(id);

        if (managerOptional.isPresent()) {
            managerService.deleteById(id);
            return new ResponseEntity<>("Manager delete", HttpStatus.OK);
        } else {
            throw new EntityNotFoundException("Manager with" + id + "not found!");
        }
    }
}
