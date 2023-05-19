package com.meters.controller;

import com.meters.requests.RoleRequest;
import com.meters.entities.Role;
import com.meters.service.RoleService;
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
@RequestMapping("/rest/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<Object> getAllRoles() {
        List<Role> roles = roleService.findAll();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Object> getAllRolesWithPageAndSort(@PathVariable int page) {

        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").ascending());

        Page<Role> roles = roleService.findAll(pageable);

        if (roles.hasContent()) {
            return new ResponseEntity<>(roles, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Role>> getRoleById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Optional<Role>> createRole(@Valid @RequestBody RoleRequest roleRequest) {
        Optional<Role> role = roleService.createRole(roleRequest);
        return new ResponseEntity<>(role, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Optional<Role>> updateRole(@Valid @RequestBody RoleRequest roleRequest, @PathVariable("id") Long id) {
        Optional<Role> role = roleService.updateRole(id, roleRequest);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @PutMapping("/{id}/soft_delete")
    public ResponseEntity<String> deactivateRole(@PathVariable("id") Long id) {
        roleService.deactivate(id);
        return new ResponseEntity<>(id + " id is deleted", HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteRole(@PathVariable("id") Long id) {
        roleService.deleteById(id);
        return new ResponseEntity<>(id + " id is deleted forever", HttpStatus.OK);
    }

    @PutMapping("/{id}/restore")
    public ResponseEntity<Optional<Role>> restoreDeletedRole(@PathVariable("id") Long id) {
        Optional<Role> role = roleService.restoreDeletedRole(id);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }
}
