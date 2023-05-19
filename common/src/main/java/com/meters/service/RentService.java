package com.meters.service;

import com.meters.requests.RentRequest;
import com.meters.entities.Rent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RentService {
    Optional<Rent> createRent(RentRequest rentRequest);

    Optional<Rent> updateRent(Long id, RentRequest rentRequest);

    List<Rent> findAll();

    Page<Rent> findAll(Pageable pageable);

    Optional<Rent> findById(Long id);
    Optional<Rent> restoreDeletedRent(Long id);

    void deleteById(Long id);
    Rent deactivate(Long id);
}
