package com.meters.mappers.catalogs;

import com.meters.dto.catalogs.ObjectTypeDto;
import com.meters.entities.catalogs.ObjectType;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class ObjectTypeMapper {
    private final ModelMapper modelMapper;

    public ObjectType toEntity(ObjectTypeDto objectTypeDto) {
        return modelMapper.map(objectTypeDto, ObjectType.class);
    }

    public ObjectTypeDto toDto(ObjectType objectType) {
        return modelMapper.map(objectType, ObjectTypeDto.class);
    }
    public ObjectType updateObjectType(ObjectTypeDto objectTypeDto, ObjectType objectType) {
        if(objectTypeDto.getTypeName() != null) {
            objectType.setTypeName(objectTypeDto.getTypeName());
        }
        objectType.setChanged(Timestamp.valueOf(LocalDateTime.now()));

        return objectType;
    }
}
