package com.meters.service;

import com.meters.entities.Deal;
import com.meters.requests.DealRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DealService {

    Optional<Deal> createDeal(DealRequest dealRequest);

    Optional<Deal> updateDeal(Long id, DealRequest dealRequest);

    List<Deal> findAll();

    Page<Deal> findAll(Pageable pageable);

    Optional<Deal> findById(Long id);

    Optional<Deal> activateDeal(Long id);

    void deleteById(Long id);

    Deal deactivate(Long id);
}
