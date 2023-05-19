package com.meters.mappers;

import com.meters.requests.SaleRequest;
import com.meters.entities.Sale;
import com.meters.exceptoins.EntityNotFoundException;
import com.meters.repository.RealEstateRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class SaleMapper {

    private final ModelMapper modelMapper;

    private final RealEstateRepository realEstateRepository;

    public Sale toEntity(SaleRequest saleRequest) {
        Sale sale = modelMapper.map(saleRequest, Sale.class);
        setRealEstate(saleRequest,sale);
        return sale;
    }

    public Sale updateSale(SaleRequest saleRequest, Sale sale) {
        if(saleRequest.getPrice() != null) {
            sale.setPrice(saleRequest.getPrice());
        }
        setRealEstate(saleRequest,sale);
        sale.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return sale;
    }
    public void setRealEstate(SaleRequest saleRequest, Sale sale) {
        sale.setRealEstate(realEstateRepository.findById(saleRequest.getRealEstate())
                .orElseThrow(() -> new EntityNotFoundException("RealEstate not exist")));
    }
}
