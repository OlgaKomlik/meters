package com.meters.service;

import com.meters.dto.DealDto;
import com.meters.entities.Deal;

import java.util.List;
import java.util.Optional;

public interface DealService {

    Optional<Deal> createDeal(DealDto dealDto);

    Optional<Deal> updateDeal(Long id, DealDto dealDto);

    List<Deal> findAll();
    //List<Deal> findAll(int page, int size);

    Optional<Deal> findById(Long id);
    Optional<Deal> restoreDeletedDeal(Long id);

    void deleteById(Long id);
    Deal softDelete(Long id);
}
