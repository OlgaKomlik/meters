package com.meters.mappers;

import com.meters.requests.LocationRequest;
import com.meters.entities.Location;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class LocationMapper {
    private final ModelMapper modelMapper;

    public Location toEntity(LocationRequest locationRequest) {
        return modelMapper.map(locationRequest, Location.class);
    }

    public Location updateLocation(LocationRequest locationRequest, Location location) {
        if(locationRequest.getCountry() != null) {
            location.setCountry(locationRequest.getCountry());
        }
        if(locationRequest.getCity() != null) {
            location.setCity(locationRequest.getCity());
        }
        if(locationRequest.getDistrict() != null) {
            location.setDistrict(locationRequest.getDistrict());
        }
        if(locationRequest.getRegion() != null) {
            location.setRegion(locationRequest.getRegion());
        }

        location.setChanged(Timestamp.valueOf(LocalDateTime.now()));

        return location;
    }
}
