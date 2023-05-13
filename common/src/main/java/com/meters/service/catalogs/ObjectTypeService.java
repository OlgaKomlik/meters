package com.meters.service.catalogs;

import com.meters.dto.catalogs.ObjectTypeDto;
import com.meters.entities.catalogs.ObjectType;

import java.util.List;
import java.util.Optional;

public interface ObjectTypeService {
    Optional<ObjectType> createObjectType(ObjectTypeDto objectTypeDto);

    Optional<ObjectType> updateObjectType(Long id, ObjectTypeDto objectTypeDto);

    List<ObjectType> findAll();
    //List<ObjectType> findAll(int page, int size);

    Optional<ObjectType> findById(Long id);
    Optional<ObjectType> restoreDeletedObjectType(Long id);

    void deleteById(Long id);
    ObjectType softDelete(Long id);
}
