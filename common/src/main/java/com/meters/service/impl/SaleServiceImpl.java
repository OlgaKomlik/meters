package com.meters.service.impl;

import com.meters.requests.create.SaleRequest;
import com.meters.entities.Sale;
import com.meters.exceptoins.EntityIsDeletedException;
import com.meters.exceptoins.EntityNotFoundException;
import com.meters.mappers.SaleMapper;
import com.meters.repository.SaleRepository;
import com.meters.requests.update.SaleUpdateRequest;
import com.meters.service.SaleService;
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
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final SaleMapper saleMapper;

    @Override
    @Transactional
    public Sale createSale(SaleRequest saleRequest) {
        Sale sale = saleMapper.toEntity(saleRequest);
        sale.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        sale.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return saleRepository.save(sale);
    }

    @Override
    @Transactional
    public Sale updateSale(Long id, SaleUpdateRequest saleRequest) {
        Sale sale = findSale(id);
        saleMapper.updateSale(saleRequest, sale);
        return saleRepository.save(sale);
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
    public void deleteById(Long id) {
        saleRepository.deleteById(id);
    }


    private Sale findSale(Long id) {
        return saleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Sale could not be found"));
    }
}
