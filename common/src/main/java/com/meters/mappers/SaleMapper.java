package com.meters.mappers;

import com.meters.dto.SaleDto;
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

    public Sale toEntity(SaleDto saleDto) {
        Sale sale = modelMapper.map(saleDto, Sale.class);
        setRealEstate(saleDto,sale);
        return sale;
    }

    public SaleDto toDto(Sale sale) {
        SaleDto saleDto = modelMapper.map(sale, SaleDto.class);
        saleDto.setRealEstate(sale.getRealEstate().getId());
        return saleDto;
    }

    public Sale updateSale(SaleDto saleDto, Sale sale) {
        if(saleDto.getPrice() != null) {
            sale.setPrice(saleDto.getPrice());
        }
        setRealEstate(saleDto,sale);
        sale.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return sale;
    }
    public void setRealEstate(SaleDto saleDto, Sale sale) {
        sale.setRealEstate(realEstateRepository.findById(saleDto.getRealEstate())
                .orElseThrow(() -> new EntityNotFoundException("RealEstate not exist")));
    }
}
