package com.meters.service.catalogs;

import com.meters.requests.create.catalogs.ObjectTypeRequest;
import com.meters.entities.catalogs.ObjectType;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Optional;

public interface ObjectTypeService {
    ObjectType createObjectType(ObjectTypeRequest objectTypeRequest);

    ObjectType updateObjectType(Long id, ObjectTypeRequest objectTypeRequest);

    @Cacheable("c_object_type")
    List<ObjectType> findAll();

    Optional<ObjectType> findById(Long id);

    void deleteById(Long id);
}
