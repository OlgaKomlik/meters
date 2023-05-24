package com.meters.controller;

import com.meters.entities.Location;
import com.meters.exceptoins.ValidationException;
import com.meters.requests.create.LocationRequest;
import com.meters.requests.update.LocationUpdateRequest;
import com.meters.service.LocationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
@RequestMapping("/rest/locations")
@Tag(name = "LocationController", description = "Location management methods")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @Value("${page-capacity.location}")
    private Integer pageCapacity;

    @GetMapping
    public ResponseEntity<List<Location>> getAllLocations() {
        List<Location> locations = locationService.findAll();
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Page<Location>> getAllLocationsWithPageAndSort(@PathVariable int page) {

        Pageable pageable = PageRequest.of(page, pageCapacity, Sort.by("id").ascending());

        Page<Location> locations = locationService.findAll(pageable);

        if (locations.hasContent()) {
            return new ResponseEntity<>(locations, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable Long id) {
        Optional<Location> location = locationService.findById(id);
        if (location.isPresent()) {
            return new ResponseEntity<>(location.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Location> createLocation(@Valid @RequestBody LocationRequest locationRequest,
                                                   BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

        Location location = locationService.createLocation(locationRequest);
        return new ResponseEntity<>(location, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Location> updateLocation(@Valid @RequestBody LocationUpdateRequest locationRequest,
                                                   @PathVariable("id") Long id, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

        Location location = locationService.updateLocation(id, locationRequest);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }
}
