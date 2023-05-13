package com.meters.service.catalogs;

import com.meters.dto.catalogs.DealTypeDto;
import com.meters.entities.catalogs.DealType;

import java.util.List;
import java.util.Optional;

public interface DealTypeService {
    Optional<DealType> createDealType(DealTypeDto dealTypeDto);

    Optional<DealType> updateDealType(Long id, DealTypeDto dealTypeDto);

    List<DealType> findAll();
    //List<DealType> findAll(int page, int size);

    Optional<DealType> findById(Long id);
    Optional<DealType> restoreDeletedDealType(Long id);

    void deleteById(Long id);
    DealType softDelete(Long id);
}
