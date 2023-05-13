package com.meters.mappers;

import com.meters.dto.RentDto;
import com.meters.entities.Rent;
import com.meters.exceptoins.EntityNotFoundException;
import com.meters.repository.RealEstateRepository;
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

    public Rent toEntity(RentDto rentDto) {
        Rent rent = modelMapper.map(rentDto, Rent.class);
        setRealEstate(rentDto,rent);
        return rent;
    }

    public RentDto toDto(Rent rent) {
        RentDto rentDto = modelMapper.map(rent, RentDto.class);
        rentDto.setRealEstate(rent.getRealEstate().getId());
        return rentDto;
    }

    public Rent updateRent(RentDto rentDto, Rent rent) {
        if(rentDto.getRentPerMonth() != null) {
            rent.setRentPerMonth(rentDto.getRentPerMonth());
        }
        if(rentDto.getMinPeriod() != null) {
            rent.setMinPeriod(rentDto.getMinPeriod());
        }
        setRealEstate(rentDto,rent);
        rent.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return rent;
    }
    public void setRealEstate(RentDto rentDto, Rent rent) {
        rent.setRealEstate(realEstateRepository.findById(rentDto.getRealEstate())
                .orElseThrow(() -> new EntityNotFoundException("RealEstate not exist")));
    }
}
