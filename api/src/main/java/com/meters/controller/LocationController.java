package com.meters.controller;

import com.meters.requests.LocationRequest;
import com.meters.entities.Location;
import com.meters.service.LocationService;
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
@RequestMapping("/rest/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @Value("${location.page-capacity}")
    private Integer pageCapacity;

    @GetMapping
    public ResponseEntity<Object> getAllLocations() {
        List<Location> locations = locationService.findAll();
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }


    @GetMapping("/page/{page}")
    public ResponseEntity<Object> getAllLocationsWithPageAndSort(@PathVariable int page) {

        Pageable pageable = PageRequest.of(page, pageCapacity, Sort.by("id").ascending());

        Page<Location> locations = locationService.findAll(pageable);

        if (locations.hasContent()) {
            return new ResponseEntity<>(locations, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Location>> getLocationById(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<Optional<Location>> createLocation(@Valid @RequestBody LocationRequest locationRequest) {
        Optional<Location> location = locationService.createLocation(locationRequest);
        return new ResponseEntity<>(location, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<Location>> updateLocation(@Valid @RequestBody LocationRequest locationRequest, @PathVariable("id") Long id) {
        Optional<Location> location = locationService.updateLocation(id, locationRequest);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivateLocation(@PathVariable("id") Long id) {
        locationService.deactivate(id);
        return new ResponseEntity<>(id + " id is deleted", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLocation(@PathVariable("id") Long id) {
        locationService.deleteById(id);
        return new ResponseEntity<>(id + " id is deleted forever", HttpStatus.OK);
    }

    @PutMapping("/{id}/restore")
    public ResponseEntity<Optional<Location>> activateLocation(@PathVariable("id") Long id) {
        Optional<Location> location = locationService.activateLocation(id);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }
}
