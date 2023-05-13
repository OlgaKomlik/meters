package com.meters.mappers;

import com.meters.dto.LocationDto;
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

    public Location toEntity(LocationDto locationDto) {
        return modelMapper.map(locationDto, Location.class);
    }

    public LocationDto toDto(Location location) {
        return modelMapper.map(location, LocationDto.class);
    }
    public Location updateLocation(LocationDto locationDto, Location location) {
        if(locationDto.getCountry() != null) {
            location.setCountry(locationDto.getCountry());
        }
        if(locationDto.getCity() != null) {
            location.setCity(locationDto.getCity());
        }
        if(locationDto.getDistrict() != null) {
            location.setDistrict(locationDto.getDistrict());
        }
        if(locationDto.getRegion() != null) {
            location.setRegion(locationDto.getRegion());
        }

        location.setChanged(Timestamp.valueOf(LocalDateTime.now()));

        return location;
    }
}
