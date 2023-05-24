package com.meters.mappers.catalogs;

import com.meters.entities.catalogs.DealType;
import com.meters.requests.create.catalogs.DealTypeRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class DealTypeMapper {

    private final ModelMapper modelMapper;

    public DealType toEntity(DealTypeRequest dealTypeRequest) {
        return modelMapper.map(dealTypeRequest, DealType.class);
    }

    public DealType updateDealType(DealTypeRequest dealTypeRequest, DealType dealType) {

        if (dealTypeRequest.getTypeName() != null) {
            dealType.setTypeName(dealTypeRequest.getTypeName());
        }

        dealType.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return dealType;
    }
}
