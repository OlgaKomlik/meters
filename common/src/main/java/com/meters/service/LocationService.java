package com.meters.service;

import com.meters.requests.create.LocationRequest;
import com.meters.entities.Location;
import com.meters.requests.update.LocationUpdateRequest;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface LocationService {

   Location createLocation(LocationRequest locationRequest);

    Location updateLocation(Long id, LocationUpdateRequest locationRequest);

    @Cacheable("locations")
    List<Location> findAll();
    Page<Location> findAll(Pageable pageable);

    Optional<Location> findById(Long id);

    void deleteById(Long id);

}
