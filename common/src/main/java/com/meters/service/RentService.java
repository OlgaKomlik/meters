package com.meters.service;

import com.meters.entities.Rent;
import com.meters.requests.create.RentRequest;
import com.meters.requests.update.RentUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RentService {

    Rent createRent(RentRequest rentRequest);

    Rent updateRent(Long id, RentUpdateRequest rentRequest);

    List<Rent> findAll();

    Page<Rent> findAll(Pageable pageable);

    Optional<Rent> findById(Long id);

    void deleteById(Long id);
}
