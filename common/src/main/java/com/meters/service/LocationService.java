package com.meters.service;

import com.meters.dto.LocationDto;
import com.meters.entities.Location;

import java.util.List;
import java.util.Optional;

public interface LocationService {

    Optional<Location> createLocation(LocationDto locationDto);

    Optional<Location> updateLocation(Long id, LocationDto locationDto);

    List<Location> findAll();
    //List<Location> findAll(int page, int size);

    Optional<Location> findById(Long id);
    Optional<Location> restoreDeletedLocation(Long id);

    void deleteById(Long id);
    Location softDelete(Long id);
}
