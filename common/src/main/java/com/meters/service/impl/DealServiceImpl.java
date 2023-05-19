package com.meters.service.impl;

import com.meters.requests.DealRequest;
import com.meters.entities.Deal;
import com.meters.exceptoins.EntityIsDeletedException;
import com.meters.exceptoins.EntityNotFoundException;
import com.meters.mappers.DealMapper;
import com.meters.repository.DealRepository;
import com.meters.service.DealService;
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
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;
    private final DealMapper dealMapper;

    @Override
    public Optional<Deal> createDeal(DealRequest dealRequest) {
        Deal deal = dealMapper.toEntity(dealRequest);
        deal.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        deal.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(dealRepository.save(deal));
    }

    @Override
    public Optional<Deal> updateDeal(Long id, DealRequest dealRequest) {
        Deal deal = findDeal(id);
        dealMapper.updateDeal(dealRequest, deal);
        return Optional.of(dealRepository.save(deal));
    }

    @Override
    public List<Deal> findAll() {
        return dealRepository.findAll();
    }

    @Override
    public Page<Deal> findAll(Pageable pageable) {
        return dealRepository.findAll(pageable);
    }

    @Override
    public Optional<Deal> findById(Long id) {
        Deal deal = findDeal(id);
        if(deal.isDeleted()) {
            throw new EntityIsDeletedException("Deal is deleted");
        }
        return Optional.of(deal);
    }

    @Override
    public Optional<Deal> restoreDeletedDeal(Long id) {
        Deal deal = findDeal(id);
        deal.setDeleted(false);
        deal.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(dealRepository.save(deal));
    }

    @Override
    public void deleteById(Long id) {
        dealRepository.deleteById(id);
    }

    @Override
    public Deal deactivate(Long id) {
        Deal deal = findDeal(id);
        deal.setDeleted(true);
        deal.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return dealRepository.save(deal);
    }

    private Deal findDeal(Long id) {
        return dealRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Deal could not be found"));
    }
}
