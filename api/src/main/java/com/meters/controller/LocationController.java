package com.meters.controller;

import com.meters.dto.LocationDto;
import com.meters.entities.Location;
import com.meters.service.LocationService;
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
@RequestMapping("/rest/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @GetMapping
    public ResponseEntity<Object> getAllLocations() {
        List<Location> locations = locationService.findAll();
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Location>> getLocationById(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Optional<Location>> createLocation(@Valid @RequestBody LocationDto locationDto) {
        Optional<Location> location = locationService.createLocation(locationDto);
        return new ResponseEntity<>(location, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Optional<Location>> updateLocation(@Valid @RequestBody LocationDto locationDto, @PathVariable("id") Long id) {
        Optional<Location> location = locationService.updateLocation(id, locationDto);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @PutMapping("/{id}/soft_delete")
    public ResponseEntity<String> softDeleteLocation(@PathVariable("id") Long id) {
        locationService.softDelete(id);
        return new ResponseEntity<>(id + " id is deleted", HttpStatus.OK);
    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteLocation(@PathVariable("id") Long id) {
        locationService.deleteById(id);
        return new ResponseEntity<>(id + " id is deleted forever", HttpStatus.OK);
    }

    @PutMapping("/{id}/restore")
    public ResponseEntity<Optional<Location>> restoreDeletedLocation(@PathVariable("id") Long id) {
        Optional<Location> location = locationService.restoreDeletedLocation(id);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }
}
