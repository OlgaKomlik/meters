package com.meters.service.impl;

import com.meters.requests.SaleRequest;
import com.meters.entities.Sale;
import com.meters.exceptoins.EntityIsDeletedException;
import com.meters.exceptoins.EntityNotFoundException;
import com.meters.mappers.SaleMapper;
import com.meters.repository.SaleRepository;
import com.meters.service.SaleService;
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
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final SaleMapper saleMapper;

    @Override
    public Optional<Sale> createSale(SaleRequest saleRequest) {
        Sale sale = saleMapper.toEntity(saleRequest);
        sale.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        sale.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(saleRepository.save(sale));
    }

    @Override
    public Optional<Sale> updateSale(Long id, SaleRequest saleRequest) {
        Sale sale = findSale(id);
        saleMapper.updateSale(saleRequest, sale);
        return Optional.of(saleRepository.save(sale));
    }

    @Override
    public List<Sale> findAll() {
        return saleRepository.findAll();
    }

    @Override
    public Page<Sale> findAll(Pageable pageable) {
        return saleRepository.findAll(pageable);
    }
    @Override
    public Optional<Sale> findById(Long id) {
        Sale sale = findSale(id);
        if(sale.isDeleted()) {
            throw new EntityIsDeletedException("Sale is deleted");
        }
        return Optional.of(sale);
    }

    @Override
    public Optional<Sale> restoreDeletedSale(Long id) {
        Sale sale = findSale(id);
        sale.setDeleted(false);
        sale.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(saleRepository.save(sale));
    }

    @Override
    public void deleteById(Long id) {
        saleRepository.deleteById(id);
    }

    @Override
    public Sale deactivate(Long id) {
        Sale sale = findSale(id);
        sale.setDeleted(true);
        sale.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return saleRepository.save(sale);
    }

    private Sale findSale(Long id) {
        return saleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Sale could not be found"));
    }
}
