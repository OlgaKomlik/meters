package com.meters.service;

import com.meters.dto.SaleDto;
import com.meters.entities.Sale;

import java.util.List;
import java.util.Optional;

public interface SaleService {

    Optional<Sale> createSale(SaleDto saleDto);

    Optional<Sale> updateSale(Long id, SaleDto saleDto);

    List<Sale> findAll();
    //List<Sale> findAll(int page, int size);

    Optional<Sale> findById(Long id);
    Optional<Sale> restoreDeletedSale(Long id);

    void deleteById(Long id);
    Sale softDelete(Long id);
}
