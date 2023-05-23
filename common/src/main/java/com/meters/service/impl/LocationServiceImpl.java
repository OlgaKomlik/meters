package com.meters.service.impl;

import com.meters.entities.Location;
import com.meters.exceptoins.EntityIsDeletedException;
import com.meters.exceptoins.EntityNotFoundException;
import com.meters.mappers.LocationMapper;
import com.meters.repository.LocationRepository;
import com.meters.requests.LocationRequest;
import com.meters.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    @Override
    @Transactional
    public Optional<Location> createLocation(LocationRequest locationRequest) {
        Location location = locationMapper.toEntity(locationRequest);
        location.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        location.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(locationRepository.save(location));
    }

    @Override
    @Transactional
    public Optional<Location> updateLocation(Long id, LocationRequest locationRequest) {
        Location location = findLocation(id);
        locationMapper.updateLocation(locationRequest, location);
        return Optional.of(locationRepository.save(location));
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
    @Transactional
    public Optional<Location> activateLocation(Long id) {
        Location location = findLocation(id);
        location.setDeleted(false);
        location.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(locationRepository.save(location));
    }

    @Override
    public void deleteById(Long id) {
        locationRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Location deactivate(Long id) {
        Location location = findLocation(id);
        location.setDeleted(true);
        location.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return locationRepository.save(location);
    }

    private Location findLocation(Long id) {
        return locationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Location could not be found"));
    }
}
