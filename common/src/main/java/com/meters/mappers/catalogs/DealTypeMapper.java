package com.meters.mappers.catalogs;

import com.meters.dto.catalogs.DealTypeDto;
import com.meters.entities.catalogs.DealType;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class DealTypeMapper {

    private final ModelMapper modelMapper;

    public DealType toEntity(DealTypeDto dealTypeDto) {
        return modelMapper.map(dealTypeDto, DealType.class);
    }

    public DealTypeDto toDto(DealType dealType) {
        return modelMapper.map(dealType, DealTypeDto.class);
    }
    public DealType updateDealType(DealTypeDto dealTypeDto, DealType dealType) {
        if(dealTypeDto.getTypeName() != null) {
            dealType.setTypeName(dealTypeDto.getTypeName());
        }
        dealType.setChanged(Timestamp.valueOf(LocalDateTime.now()));

        return dealType;
    }
}
