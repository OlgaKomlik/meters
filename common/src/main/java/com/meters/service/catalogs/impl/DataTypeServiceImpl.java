package com.meters.service.catalogs.impl;

import com.meters.entities.catalogs.DealType;
import com.meters.exceptoins.EntityIsDeletedException;
import com.meters.exceptoins.EntityNotFoundException;
import com.meters.mappers.catalogs.DealTypeMapper;
import com.meters.repository.catalogs.DealTypeRepository;
import com.meters.requests.catalogs.DealTypeRequest;
import com.meters.service.catalogs.DealTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DataTypeServiceImpl implements DealTypeService {

    private final DealTypeRepository dealTypeRepository;

    private final DealTypeMapper dealTypeMapper;

    @Override
    @Transactional
    public Optional<DealType> createDealType(DealTypeRequest dealTypeRequest) {
        DealType dealType = dealTypeMapper.toEntity(dealTypeRequest);
        dealType.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        dealType.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(dealTypeRepository.save(dealType));
    }

    @Override
    @Transactional
    public Optional<DealType> updateDealType(Long id, DealTypeRequest dealTypeRequest) {
        DealType dealType = findDealType(id);
        dealTypeMapper.updateDealType(dealTypeRequest, dealType);
        return Optional.of(dealTypeRepository.save(dealType));
    }

    @Override
    public List<DealType> findAll() {
        return dealTypeRepository.findAll();
    }

    @Override
    public Optional<DealType> findById(Long id) {
        DealType dealType = findDealType(id);
        if (dealType.isDeleted()) {
            throw new EntityIsDeletedException("DealType is deleted");
        }
        return Optional.of(dealType);
    }

    @Override
    @Transactional
    public Optional<DealType> activateDealType(Long id) {
        DealType dealType = findDealType(id);
        dealType.setDeleted(false);
        dealType.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(dealTypeRepository.save(dealType));
    }

    @Override
    public void deleteById(Long id) {
        dealTypeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public DealType deactivate(Long id) {
        DealType dealType = findDealType(id);
        dealType.setDeleted(true);
        dealType.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return dealTypeRepository.save(dealType);
    }

    private DealType findDealType(Long id) {
        return dealTypeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("DealType could not be found"));
    }
}
