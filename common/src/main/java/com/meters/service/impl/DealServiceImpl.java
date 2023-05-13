package com.meters.service.impl;

import com.meters.dto.DealDto;
import com.meters.entities.Deal;
import com.meters.exceptoins.EntityIsDeletedException;
import com.meters.exceptoins.EntityNotFoundException;
import com.meters.mappers.DealMapper;
import com.meters.repository.DealRepository;
import com.meters.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;
    private final DealMapper dealMapper;

    @Override
    public Optional<Deal> createDeal(DealDto dealDto) {
        Deal deal = dealMapper.toEntity(dealDto);
        deal.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        deal.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(dealRepository.save(deal));
    }

    @Override
    public Optional<Deal> updateDeal(Long id, DealDto dealDto) {
        Deal deal = findDeal(id);
        dealMapper.updateDeal(dealDto, deal);
        return Optional.of(dealRepository.save(deal));
    }

    @Override
    public List<Deal> findAll() {
        return dealRepository.findAll();
    }

    @Override
    public Optional<Deal> findById(Long id) {
        Deal deal = findDeal(id);
        if(deal.getIsDeleted().equals(Boolean.TRUE)) {
            throw new EntityIsDeletedException("Deal is deleted");
        }
        return Optional.of(deal);
    }

    @Override
    public Optional<Deal> restoreDeletedDeal(Long id) {
        Deal deal = findDeal(id);
        deal.setIsDeleted(false);
        deal.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(dealRepository.save(deal));
    }

    @Override
    public void deleteById(Long id) {
        dealRepository.deleteById(id);
    }

    @Override
    public Deal softDelete(Long id) {
        Deal deal = findDeal(id);
        deal.setIsDeleted(true);
        deal.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return dealRepository.save(deal);
    }

    private Deal findDeal(Long id) {
        return dealRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Deal could not be found"));
    }
}
