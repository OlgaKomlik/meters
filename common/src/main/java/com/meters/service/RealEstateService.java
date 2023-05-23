package com.meters.service;

import com.meters.entities.RealEstate;
import com.meters.requests.create.RealEstateRequest;
import com.meters.requests.update.RealEstateUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RealEstateService {
    RealEstate createRealEstate(RealEstateRequest realEstateRequest);

    RealEstate updateRealEstate(Long id, RealEstateUpdateRequest realEstateRequest);

    List<RealEstate> findAll();

    Page<RealEstate> findAll(Pageable pageable);

    Optional<RealEstate> findById(Long id);

    void deleteById(Long id);

}
