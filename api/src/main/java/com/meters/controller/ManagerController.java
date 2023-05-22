package com.meters.controller;

import com.meters.requests.create.ManagerRequest;
import com.meters.entities.Manager;
import com.meters.requests.update.ManagerUpdateRequest;
import com.meters.service.ManagerService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/rest/managers")
@RequiredArgsConstructor
public class ManagerController {
    private final ManagerService managerService;

    @Value("${page-capacity.manager}")
    private Integer pageCapacity;

    @GetMapping
    public ResponseEntity<List<Manager>> getAllManagers() {
        List<Manager> managers = managerService.findAll();
        return new ResponseEntity<>(managers, HttpStatus.OK);
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Page<Manager>> getAllManagersWithPageAndSort(@PathVariable int page) {

        Pageable pageable = PageRequest.of(page, pageCapacity, Sort.by("id").ascending());

        Page<Manager> managers = managerService.findAll(pageable);

        if (managers.hasContent()) {
            return new ResponseEntity<>(managers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Manager> getManagerById(@PathVariable Long id) {

        Optional<Manager> manager = managerService.findById(id);
        if(manager.isPresent()) {
            return new ResponseEntity<> (manager.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Manager> createManager(@Valid @RequestBody ManagerRequest managerRequest,
                                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

        Manager manager = managerService.createManager(managerRequest);
        return new ResponseEntity<>(manager, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Manager> updateManager(@Valid @RequestBody ManagerUpdateRequest managerRequest,
                                                           @PathVariable("id") Long id, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

        Manager manager = managerService.updateManager(id, managerRequest);
        return new ResponseEntity<>(manager, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteManager(@PathVariable("id") Long id) {
        managerService.deleteById(id);
        return new ResponseEntity<>(id + " id is deleted forever", HttpStatus.OK);
    }

    @PostMapping("/{managerId}/role")
    public ResponseEntity<Manager> setManagersRole(@PathVariable Long managerId, @RequestParam String roleName) {
        Manager manager = managerService.setUserRole(managerId, roleName);
        return new ResponseEntity<>(manager, HttpStatus.OK);
    }

    @GetMapping("/stat/birthday")
    public ResponseEntity<List<Manager>> getBirthDayManagers() {
        List<Manager> managers = managerService.findBirthDayManagers(LocalDateTime.now());
        return new ResponseEntity<>(managers, HttpStatus.OK);
    }

    @GetMapping("/stat/fee")
    public ResponseEntity<Map<String, Double>> getAverageFeeOfManagers() {
        Map<String, Double> statistic = managerService.getAverageFeeOfManagers();
        return ResponseEntity.ok(statistic);
    }

    @GetMapping("/search/fullname")
    public ResponseEntity<List<Manager>> findByFullName(@RequestParam String query) {
        List<Manager> managers = managerService.findByFullNameContainingIgnoreCase(query);
                return ResponseEntity.ok(managers);
    }

    @GetMapping("/stat/sellers")
    public ResponseEntity<List<Manager>> findBestSellers(@RequestParam int month, int year) {
        return ResponseEntity.ok(managerService.getBestSellersOfTheMonth(month, year));
    }
}
