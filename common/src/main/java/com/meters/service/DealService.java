package com.meters.service;

import com.meters.requests.create.DealRequest;
import com.meters.entities.Deal;
import com.meters.requests.update.DealUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DealService {

    Deal createDeal(DealRequest dealRequest);

    Deal updateDeal(Long id, DealUpdateRequest dealRequest);

    List<Deal> findAll();
    Page<Deal> findAll(Pageable pageable);
    Optional<Deal> findById(Long id);

    void deleteById(Long id);

}
