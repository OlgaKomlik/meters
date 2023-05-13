package com.meters.service.impl;

import com.meters.dto.RentDto;
import com.meters.entities.Rent;
import com.meters.exceptoins.EntityIsDeletedException;
import com.meters.exceptoins.EntityNotFoundException;
import com.meters.mappers.RentMapper;
import com.meters.repository.RentRepository;
import com.meters.service.RentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public Optional<Rent> createRent(RentDto rentDto) {
        Rent rent = rentMapper.toEntity(rentDto);
        rent.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        rent.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(rentRepository.save(rent));
    }

    @Override
    public Optional<Rent> updateRent(Long id, RentDto rentDto) {
        Rent rent = findRent(id);
        rentMapper.updateRent(rentDto, rent);
        return Optional.of(rentRepository.save(rent));
    }

    @Override
    public List<Rent> findAll() {
        return rentRepository.findAll();
    }

    @Override
    public Optional<Rent> findById(Long id) {
        Rent rent = findRent(id);
        if(rent.getIsDeleted().equals(Boolean.TRUE)) {
            throw new EntityIsDeletedException("Rent is deleted");
        }
        return Optional.of(rent);
    }

    @Override
    public Optional<Rent> restoreDeletedRent(Long id) {
        Rent rent = findRent(id);
        rent.setIsDeleted(false);
        rent.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(rentRepository.save(rent));
    }

    @Override
    public void deleteById(Long id) {
        rentRepository.deleteById(id);
    }

    @Override
    public Rent softDelete(Long id) {
        Rent rent = findRent(id);
        rent.setIsDeleted(true);
        rent.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return rentRepository.save(rent);
    }

    private Rent findRent(Long id) {
        return rentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Rent could not be found"));
    }
}
