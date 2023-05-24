package com.meters.service.catalogs;

import com.meters.entities.catalogs.DealType;
import com.meters.requests.create.catalogs.DealTypeRequest;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Optional;

public interface DealTypeService {

    DealType createDealType(DealTypeRequest dealTypeRequest);

    DealType updateDealType(Long id, DealTypeRequest dealTypeRequest);

    @Cacheable("c_deal_type")
    List<DealType> findAll();

    Optional<DealType> findById(Long id);

    void deleteById(Long id);
}
