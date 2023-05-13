package com.meters.controller;

import com.meters.dto.ManagerDto;
import com.meters.entities.Manager;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/managers")
@RequiredArgsConstructor
public class ManagerController {
    private final ManagerService managerService;

    @GetMapping
    public ResponseEntity<Object> getAllManagers() {
        List<Manager> managers = managerService.findAll();
        return new ResponseEntity<>(managers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Manager>> getManagerById(@PathVariable Long id) {
        return ResponseEntity.ok(managerService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Optional<Manager>> createManager(@Valid @RequestBody ManagerDto managerDto) {
        Optional<Manager> manager = managerService.createManager(managerDto);
        return new ResponseEntity<>(manager, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Optional<Manager>> updateManager(@Valid @RequestBody ManagerDto managerDto, @PathVariable("id") Long id) {
        Optional<Manager> manager = managerService.updateManager(id, managerDto);
        return new ResponseEntity<>(manager, HttpStatus.OK);
    }

    @PutMapping("/{id}/soft_delete")
    public ResponseEntity<String> softDeleteManager(@PathVariable("id") Long id) {
        managerService.softDelete(id);
        return new ResponseEntity<>(id + " id is deleted", HttpStatus.OK);
    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteManager(@PathVariable("id") Long id) {
        managerService.deleteById(id);
        return new ResponseEntity<>(id + " id is deleted forever", HttpStatus.OK);
    }

    @PutMapping("/{id}/restore")
    public ResponseEntity<Optional<Manager>> restoreDeletedManager(@PathVariable("id") Long id) {
        Optional<Manager> manager = managerService.restoreDeletedManager(id);
        return new ResponseEntity<>(manager, HttpStatus.OK);
    }

    @PostMapping("/{managerId}/set_role")
    public ResponseEntity<Manager> setManagersRole(@PathVariable Long managerId, @RequestParam String roleName) {
        Optional<Manager> manager = managerService.setUserRole(managerId, roleName);
        return manager.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
