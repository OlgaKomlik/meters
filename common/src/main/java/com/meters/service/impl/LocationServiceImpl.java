package com.meters.service.impl;

import com.meters.dto.LocationDto;
import com.meters.entities.Location;
import com.meters.exceptoins.EntityIsDeletedException;
import com.meters.exceptoins.EntityNotFoundException;
import com.meters.mappers.LocationMapper;
import com.meters.repository.LocationRepository;
import com.meters.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public Optional<Location> createLocation(LocationDto locationDto) {
        Location location = locationMapper.toEntity(locationDto);
        location.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        location.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(locationRepository.save(location));
    }

    @Override
    public Optional<Location> updateLocation(Long id, LocationDto locationDto) {
        Location location = findLocation(id);
        locationMapper.updateLocation(locationDto, location);
        return Optional.of(locationRepository.save(location));
    }

    @Override
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    @Override
    public Optional<Location> findById(Long id) {
        Location location = findLocation(id);
        if(location.getIsDeleted().equals(Boolean.TRUE)) {
            throw new EntityIsDeletedException("Location is deleted");
        }
        return Optional.of(location);
    }

    @Override
    public Optional<Location> restoreDeletedLocation(Long id) {
        Location location = findLocation(id);
        location.setIsDeleted(false);
        location.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(locationRepository.save(location));
    }

    @Override
    public void deleteById(Long id) {
        locationRepository.deleteById(id);
    }

    @Override
    public Location softDelete(Long id) {
        Location location = findLocation(id);
        location.setIsDeleted(true);
        location.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return locationRepository.save(location);
    }

    private Location findLocation(Long id) {
        return locationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Location could not be found"));
    }
}
