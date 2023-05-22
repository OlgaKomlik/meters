package com.meters.service.impl;

import com.meters.requests.create.RentRequest;
import com.meters.entities.Rent;
import com.meters.exceptoins.EntityIsDeletedException;
import com.meters.exceptoins.EntityNotFoundException;
import com.meters.mappers.RentMapper;
import com.meters.repository.RentRepository;
import com.meters.requests.update.RentUpdateRequest;
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
    public Rent createRent(RentRequest rentRequest) {
        Rent rent = rentMapper.toEntity(rentRequest);
        rent.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        rent.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return rentRepository.save(rent);
    }

    @Override
    @Transactional
    public Rent updateRent(Long id, RentUpdateRequest rentRequest) {
        Rent rent = findRent(id);
        rentMapper.updateRent(rentRequest, rent);
        return rentRepository.save(rent);
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
    public void deleteById(Long id) {
        rentRepository.deleteById(id);
    }



    private Rent findRent(Long id) {
        return rentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Rent could not be found"));
    }
}
