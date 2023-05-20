package com.meters.service;

import com.meters.requests.LocationRequest;
import com.meters.entities.Location;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface LocationService {

    Optional<Location> createLocation(LocationRequest locationRequest);

    Optional<Location> updateLocation(Long id, LocationRequest locationRequest);

    @Cacheable("locations")
    List<Location> findAll();
    Page<Location> findAll(Pageable pageable);

    Optional<Location> findById(Long id);
    Optional<Location> activateLocation(Long id);

    void deleteById(Long id);
    Location deactivate(Long id);
}
