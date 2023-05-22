package com.meters.mappers;

import com.meters.requests.create.RentRequest;
import com.meters.entities.Rent;
import com.meters.exceptoins.EntityNotFoundException;
import com.meters.repository.RealEstateRepository;
import com.meters.requests.update.RentUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class RentMapper {

    private final ModelMapper modelMapper;

    private final RealEstateRepository realEstateRepository;

    public Rent toEntity(RentRequest rentRequest) {
        Rent rent = modelMapper.map(rentRequest, Rent.class);
        setRealEstate(rentRequest,rent);
        return rent;
    }

    public Rent updateRent(RentUpdateRequest rentRequest, Rent rent) {
        if(rentRequest.getRentPerMonth() != null) {
            rent.setRentPerMonth(rentRequest.getRentPerMonth());
        }
        if(rentRequest.getMinPeriod() != null) {
            rent.setMinPeriod(rentRequest.getMinPeriod());
        }
        if (rentRequest.getIsDeleted() != null){
            rent.setDeleted(rentRequest.getIsDeleted());
        }
        setRealEstate(rentRequest,rent);
        rent.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return rent;
    }
    public void setRealEstate(RentRequest rentRequest, Rent rent) {
        rent.setRealEstate(realEstateRepository.findById(rentRequest.getRealEstate())
                .orElseThrow(() -> new EntityNotFoundException("RealEstate not exist")));
    }
}
