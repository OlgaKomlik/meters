package com.meters.service.catalogs;

import com.meters.requests.catalogs.ObjectTypeRequest;
import com.meters.entities.catalogs.ObjectType;

import java.util.List;
import java.util.Optional;

public interface ObjectTypeService {
    Optional<ObjectType> createObjectType(ObjectTypeRequest objectTypeRequest);

    Optional<ObjectType> updateObjectType(Long id, ObjectTypeRequest objectTypeRequest);

    List<ObjectType> findAll();

    Optional<ObjectType> findById(Long id);
    Optional<ObjectType> restoreDeletedObjectType(Long id);

    void deleteById(Long id);
    ObjectType deactivate(Long id);
}
