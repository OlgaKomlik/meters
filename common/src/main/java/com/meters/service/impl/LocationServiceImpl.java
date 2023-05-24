package com.meters.service.impl;

import com.meters.entities.Location;
import com.meters.exceptoins.EntityIsDeletedException;
import com.meters.exceptoins.EntityNotFoundException;
import com.meters.mappers.LocationMapper;
import com.meters.repository.LocationRepository;
import com.meters.requests.create.LocationRequest;
import com.meters.requests.update.LocationUpdateRequest;
import com.meters.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    private final LocationMapper locationMapper;

    @Override
    @Transactional
    public Location createLocation(LocationRequest locationRequest) {
        Location location = locationMapper.toEntity(locationRequest);
        return locationRepository.save(location);
    }

    @Override
    @Transactional
    public Location updateLocation(Long id, LocationUpdateRequest locationRequest) {
        Location location = findLocation(id);
        locationMapper.updateLocation(locationRequest, location);
        return locationRepository.save(location);
    }

    @Override
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    @Override
    public Page<Location> findAll(Pageable pageable) {
        return locationRepository.findAll(pageable);
    }

    @Override
    public Optional<Location> findById(Long id) {
        Location location = findLocation(id);
        if (location.isDeleted()) {
            throw new EntityIsDeletedException("Location is deleted");
        }
        return Optional.of(location);
    }

    @Override
    public void deleteById(Long id) {
        locationRepository.deleteById(id);
    }

    private Location findLocation(Long id) {
        return locationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Location could not be found"));
    }
}
