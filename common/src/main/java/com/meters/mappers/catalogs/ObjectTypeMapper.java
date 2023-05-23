package com.meters.mappers.catalogs;

import com.meters.entities.catalogs.ObjectType;
import com.meters.requests.create.catalogs.ObjectTypeRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class ObjectTypeMapper {
    private final ModelMapper modelMapper;

    public ObjectType toEntity(ObjectTypeRequest objectTypeRequest) {
        return modelMapper.map(objectTypeRequest, ObjectType.class);
    }

    public ObjectType updateObjectType(ObjectTypeRequest objectTypeRequest, ObjectType objectType) {
        if (objectTypeRequest.getTypeName() != null) {
            objectType.setTypeName(objectTypeRequest.getTypeName());
        }
        objectType.setChanged(Timestamp.valueOf(LocalDateTime.now()));

        return objectType;
    }
}
