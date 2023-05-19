package com.meters.service.catalogs;

import com.meters.requests.catalogs.DealTypeRequest;
import com.meters.entities.catalogs.DealType;

import java.util.List;
import java.util.Optional;

public interface DealTypeService {
    Optional<DealType> createDealType(DealTypeRequest dealTypeRequest);

    Optional<DealType> updateDealType(Long id, DealTypeRequest dealTypeRequest);

    List<DealType> findAll();

    Optional<DealType> findById(Long id);
    Optional<DealType> restoreDeletedDealType(Long id);

    void deleteById(Long id);
    DealType deactivate(Long id);
}
