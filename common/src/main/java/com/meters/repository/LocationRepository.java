package com.meters.repository;

import com.meters.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findLocationByCountryAndCityAndRegionAndDistrict(String country, String city, String region, String district);
}