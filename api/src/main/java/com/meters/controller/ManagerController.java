package com.meters.controller;

import com.meters.entities.Manager;
import com.meters.requests.ManagerRequest;
import com.meters.service.ManagerService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/rest/managers")
@RequiredArgsConstructor
public class ManagerController {
    private final ManagerService managerService;

    @Value("${manager.page-capacity}")
    private Integer pageCapacity;

    @GetMapping
    public ResponseEntity<Object> getAllManagers() {
        List<Manager> managers = managerService.findAll();
        return new ResponseEntity<>(managers, HttpStatus.OK);
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Object> getAllManagersWithPageAndSort(@PathVariable int page) {

        Pageable pageable = PageRequest.of(page, pageCapacity, Sort.by("id").ascending());

        Page<Manager> managers = managerService.findAll(pageable);

        if (managers.hasContent()) {
            return new ResponseEntity<>(managers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Manager>> getManagerById(@PathVariable Long id) {
        return ResponseEntity.ok(managerService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<Optional<Manager>> createManager(@Valid @RequestBody ManagerRequest managerRequest) {
        Optional<Manager> manager = managerService.createManager(managerRequest);
        return new ResponseEntity<>(manager, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<Manager>> updateManager(@Valid @RequestBody ManagerRequest managerRequest, @PathVariable("id") Long id) {
        Optional<Manager> manager = managerService.updateManager(id, managerRequest);
        return new ResponseEntity<>(manager, HttpStatus.OK);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivateManager(@PathVariable("id") Long id) {
        managerService.deactivate(id);
        return new ResponseEntity<>(id + " id is deleted", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteManager(@PathVariable("id") Long id) {
        managerService.deleteById(id);
        return new ResponseEntity<>(id + " id is deleted forever", HttpStatus.OK);
    }

    @PutMapping("/{id}/restore")
    public ResponseEntity<Optional<Manager>> activateManager(@PathVariable("id") Long id) {
        Optional<Manager> manager = managerService.activateManager(id);
        return new ResponseEntity<>(manager, HttpStatus.OK);
    }

    @PostMapping("/{managerId}/set_role")
    public ResponseEntity<Manager> setManagersRole(@PathVariable Long managerId, @RequestParam String roleName) {
        Optional<Manager> manager = managerService.setUserRole(managerId, roleName);
        return manager.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/birth_day_today")
    public ResponseEntity<Object> getBirthDayManagers() {
        List<Manager> managers = managerService.findBirthDayManagers(LocalDateTime.now());
        return new ResponseEntity<>(managers, HttpStatus.OK);
    }

    @GetMapping("/average_fee")
    public ResponseEntity<Map<String, Double>> getAverageFeeOfManagers() {
        return ResponseEntity.ok(managerService.getAverageFeeOfManagers());
    }

    @GetMapping("/full_name")
    public ResponseEntity<Object> findByFullName(@RequestParam String query) {
        return ResponseEntity.ok(managerService.findByFullNameContainingIgnoreCase(query));
    }

    @GetMapping("/best_month_seller")
    public ResponseEntity<Object> findBestSeller(@RequestParam int month, int year) {
        return ResponseEntity.ok(managerService.getBestSellerOfTheMonth(month, year));
    }

/*    @GetMapping("/best_month_sellers")
    public ResponseEntity<Object> findBestSellers(@RequestParam int month, int year) {
        return ResponseEntity.ok(managerService.getBestSellersOfTheMonth(month, year));
    }*/
}
