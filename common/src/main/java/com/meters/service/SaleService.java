package com.meters.service;

import com.meters.requests.create.SaleRequest;
import com.meters.entities.Sale;
import com.meters.requests.update.SaleUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SaleService {

   Sale createSale(SaleRequest saleRequest);

    Sale updateSale(Long id, SaleUpdateRequest saleRequest);

    List<Sale> findAll();
    Page<Sale> findAll(Pageable pageable);
    Optional<Sale> findById(Long id);
    void deleteById(Long id);
   }
