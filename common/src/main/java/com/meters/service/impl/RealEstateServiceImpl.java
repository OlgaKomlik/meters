package com.meters.service.impl;

import com.meters.requests.RealEstateRequest;
import com.meters.entities.RealEstate;
import com.meters.exceptoins.EntityIsDeletedException;
import com.meters.exceptoins.EntityNotFoundException;
import com.meters.mappers.RealEstateMapper;
import com.meters.repository.RealEstateRepository;
import com.meters.service.RealEstateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RealEstateServiceImpl implements RealEstateService {

    private final RealEstateRepository realEstateRepository;
    private final RealEstateMapper realEstateMapper;

    @Override
    public Optional<RealEstate> createRealEstate(RealEstateRequest realEstateRequest) {
        RealEstate realEstate = realEstateMapper.toEntity(realEstateRequest);
        realEstate.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        realEstate.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(realEstateRepository.save(realEstate));
    }

    @Override
    public Optional<RealEstate> updateRealEstate(Long id, RealEstateRequest realEstateRequest) {
        RealEstate realEstate = findRealEstate(id);
        realEstateMapper.updateRealEstate(realEstateRequest, realEstate);
        return Optional.of(realEstateRepository.save(realEstate));
    }

    @Override
    public List<RealEstate> findAll() {
        return realEstateRepository.findAll();
    }

    @Override
    public Page<RealEstate> findAll(Pageable pageable) {
        return realEstateRepository.findAll(pageable);
    }

    @Override
    public Optional<RealEstate> findById(Long id) {
        RealEstate realEstate = findRealEstate(id);
        if(realEstate.isDeleted()) {
            throw new EntityIsDeletedException("RealEstate is deleted");
        }
        return Optional.of(realEstate);
    }

    @Override
    public Optional<RealEstate> restoreDeletedRealEstate(Long id) {
        RealEstate realEstate = findRealEstate(id);
        realEstate.setDeleted(false);
        realEstate.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(realEstateRepository.save(realEstate));
    }

    @Override
    public void deleteById(Long id) {
        realEstateRepository.deleteById(id);
    }

    @Override
    public RealEstate deactivate(Long id) {
        RealEstate realEstate = findRealEstate(id);
        realEstate.setDeleted(true);
        realEstate.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return realEstateRepository.save(realEstate);
    }

    private RealEstate findRealEstate(Long id) {
        return realEstateRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("RealEstate could not be found"));
    }
}
