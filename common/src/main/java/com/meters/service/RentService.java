package com.meters.service;

import com.meters.dto.RentDto;
import com.meters.entities.Rent;

import java.util.List;
import java.util.Optional;

public interface RentService {
    Optional<Rent> createRent(RentDto rentDto);

    Optional<Rent> updateRent(Long id, RentDto rentDto);

    List<Rent> findAll();
    //List<Rent> findAll(int page, int size);

    Optional<Rent> findById(Long id);
    Optional<Rent> restoreDeletedRent(Long id);

    void deleteById(Long id);
    Rent softDelete(Long id);
}
