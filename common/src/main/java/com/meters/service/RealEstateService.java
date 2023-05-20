package com.meters.service;

import com.meters.requests.RealEstateRequest;
import com.meters.entities.RealEstate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RealEstateService {
    Optional<RealEstate> createRealEstate(RealEstateRequest realEstateRequest);

    Optional<RealEstate> updateRealEstate(Long id, RealEstateRequest realEstateRequest);

    List<RealEstate> findAll();
    Page<RealEstate> findAll(Pageable pageable);

    Optional<RealEstate> findById(Long id);
    Optional<RealEstate> activateRealEstate(Long id);

    void deleteById(Long id);
    RealEstate deactivate(Long id);
}
