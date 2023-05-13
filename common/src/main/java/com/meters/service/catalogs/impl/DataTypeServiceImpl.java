package com.meters.service.catalogs.impl;

import com.meters.dto.catalogs.DealTypeDto;

import com.meters.entities.catalogs.DealType;
import com.meters.exceptoins.EntityIsDeletedException;
import com.meters.exceptoins.EntityNotFoundException;
import com.meters.mappers.catalogs.DealTypeMapper;
import com.meters.repository.catalogs.DealTypeRepository;
import com.meters.service.catalogs.DealTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public Optional<DealType> createDealType(DealTypeDto dealTypeDto) {
        DealType dealType = dealTypeMapper.toEntity(dealTypeDto);
        dealType.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        dealType.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(dealTypeRepository.save(dealType));
    }

    @Override
    public Optional<DealType> updateDealType(Long id, DealTypeDto dealTypeDto) {
        DealType dealType = findDealType(id);
        dealTypeMapper.updateDealType(dealTypeDto, dealType);
        return Optional.of(dealTypeRepository.save(dealType));
    }

    @Override
    public List<DealType> findAll() {
        return dealTypeRepository.findAll();
    }

    @Override
    public Optional<DealType> findById(Long id) {
        DealType dealType = findDealType(id);
        if(dealType.getIsDeleted().equals(Boolean.TRUE)) {
            throw new EntityIsDeletedException("DealType is deleted");
        }
        return Optional.of(dealType);
    }

    @Override
    public Optional<DealType> restoreDeletedDealType(Long id) {
        DealType dealType = findDealType(id);
        dealType.setIsDeleted(false);
        dealType.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(dealTypeRepository.save(dealType));
    }

    @Override
    public void deleteById(Long id) {
        dealTypeRepository.deleteById(id);
    }

    @Override
    public DealType softDelete(Long id) {
        DealType dealType = findDealType(id);
        dealType.setIsDeleted(true);
        dealType.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return dealTypeRepository.save(dealType);
    }

    private DealType findDealType(Long id) {
        return dealTypeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("DealType could not be found"));
    }
}