package com.meters.service;

import com.meters.requests.SaleRequest;
import com.meters.entities.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SaleService {

    Optional<Sale> createSale(SaleRequest saleRequest);

    Optional<Sale> updateSale(Long id, SaleRequest saleRequest);

    List<Sale> findAll();
    Page<Sale> findAll(Pageable pageable);
    Optional<Sale> findById(Long id);
    Optional<Sale> activateSale(Long id);

    void deleteById(Long id);
    Sale deactivate(Long id);
}
