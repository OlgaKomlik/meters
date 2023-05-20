package com.meters.service.impl;

import com.meters.requests.RentRequest;
import com.meters.entities.Rent;
import com.meters.exceptoins.EntityIsDeletedException;
import com.meters.exceptoins.EntityNotFoundException;
import com.meters.mappers.RentMapper;
import com.meters.repository.RentRepository;
import com.meters.service.RentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RentServiceImpl implements RentService {
    private final RentRepository rentRepository;
    private final RentMapper rentMapper;

    @Override
    @Transactional
    public Optional<Rent> createRent(RentRequest rentRequest) {
        Rent rent = rentMapper.toEntity(rentRequest);
        rent.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        rent.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(rentRepository.save(rent));
    }

    @Override
    @Transactional
    public Optional<Rent> updateRent(Long id, RentRequest rentRequest) {
        Rent rent = findRent(id);
        rentMapper.updateRent(rentRequest, rent);
        return Optional.of(rentRepository.save(rent));
    }

    @Override
    public List<Rent> findAll() {
        return rentRepository.findAll();
    }

    @Override
    public Page<Rent> findAll(Pageable pageable) {
        return rentRepository.findAll(pageable);
    }

    @Override
    public Optional<Rent> findById(Long id) {
        Rent rent = findRent(id);
        if(rent.isDeleted()) {
            throw new EntityIsDeletedException("Rent is deleted");
        }
        return Optional.of(rent);
    }

    @Override
    @Transactional
    public Optional<Rent> activateRent(Long id) {
        Rent rent = findRent(id);
        rent.setDeleted(false);
        rent.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(rentRepository.save(rent));
    }

    @Override
    public void deleteById(Long id) {
        rentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Rent deactivate(Long id) {
        Rent rent = findRent(id);
        rent.setDeleted(true);
        rent.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return rentRepository.save(rent);
    }

    private Rent findRent(Long id) {
        return rentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Rent could not be found"));
    }
}
