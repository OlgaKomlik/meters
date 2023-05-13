package com.meters.service;

import com.meters.dto.RealEstateDto;
import com.meters.entities.RealEstate;

import java.util.List;
import java.util.Optional;

public interface RealEstateService {
    Optional<RealEstate> createRealEstate(RealEstateDto realEstateDto);

    Optional<RealEstate> updateRealEstate(Long id, RealEstateDto realEstateDto);

    List<RealEstate> findAll();
    //List<RealEstate> findAll(int page, int size);

    Optional<RealEstate> findById(Long id);
    Optional<RealEstate> restoreDeletedRealEstate(Long id);

    void deleteById(Long id);
    RealEstate softDelete(Long id);
}
