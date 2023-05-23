package com.meters.repository;

import com.meters.entities.RealEstate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RealEstateRepository extends JpaRepository<RealEstate, Long> {

    Optional<RealEstate> findRealEstateByAddressContainingIgnoreCase(String query);
}