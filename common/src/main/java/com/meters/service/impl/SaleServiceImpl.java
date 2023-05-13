package com.meters.service.impl;

import com.meters.dto.SaleDto;
import com.meters.entities.Sale;
import com.meters.exceptoins.EntityIsDeletedException;
import com.meters.exceptoins.EntityNotFoundException;
import com.meters.mappers.SaleMapper;
import com.meters.repository.SaleRepository;
import com.meters.service.SaleService;
import lombok.RequiredArgsConstructor;
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
    public Optional<Sale> createSale(SaleDto saleDto) {
        Sale sale = saleMapper.toEntity(saleDto);
        sale.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        sale.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(saleRepository.save(sale));
    }

    @Override
    public Optional<Sale> updateSale(Long id, SaleDto saleDto) {
        Sale sale = findSale(id);
        saleMapper.updateSale(saleDto, sale);
        return Optional.of(saleRepository.save(sale));
    }

    @Override
    public List<Sale> findAll() {
        return saleRepository.findAll();
    }

    @Override
    public Optional<Sale> findById(Long id) {
        Sale sale = findSale(id);
        if(sale.getIsDeleted().equals(Boolean.TRUE)) {
            throw new EntityIsDeletedException("Sale is deleted");
        }
        return Optional.of(sale);
    }

    @Override
    public Optional<Sale> restoreDeletedSale(Long id) {
        Sale sale = findSale(id);
        sale.setIsDeleted(false);
        sale.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(saleRepository.save(sale));
    }

    @Override
    public void deleteById(Long id) {
        saleRepository.deleteById(id);
    }

    @Override
    public Sale softDelete(Long id) {
        Sale sale = findSale(id);
        sale.setIsDeleted(true);
        sale.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return saleRepository.save(sale);
    }

    private Sale findSale(Long id) {
        return saleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Sale could not be found"));
    }
}
