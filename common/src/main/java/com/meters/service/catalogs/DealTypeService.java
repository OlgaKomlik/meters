package com.meters.service.catalogs;

import com.meters.entities.catalogs.DealType;
import com.meters.requests.catalogs.DealTypeRequest;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Optional;

public interface DealTypeService {
    Optional<DealType> createDealType(DealTypeRequest dealTypeRequest);

    Optional<DealType> updateDealType(Long id, DealTypeRequest dealTypeRequest);

    @Cacheable("c_deal_type")
    List<DealType> findAll();

    Optional<DealType> findById(Long id);

    Optional<DealType> activateDealType(Long id);

    void deleteById(Long id);

    DealType deactivate(Long id);
}
