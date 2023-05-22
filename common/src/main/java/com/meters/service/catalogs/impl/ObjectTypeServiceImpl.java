package com.meters.service.catalogs.impl;

import com.meters.requests.create.catalogs.ObjectTypeRequest;
import com.meters.entities.catalogs.ObjectType;
import com.meters.exceptoins.EntityIsDeletedException;
import com.meters.exceptoins.EntityNotFoundException;
import com.meters.mappers.catalogs.ObjectTypeMapper;
import com.meters.repository.catalogs.ObjectTypeRepository;
import com.meters.service.catalogs.ObjectTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ObjectTypeServiceImpl implements ObjectTypeService {
    private final ObjectTypeRepository objectTypeRepository;

    private final ObjectTypeMapper objectTypeMapper;

    @Override
    @Transactional
    public ObjectType createObjectType(ObjectTypeRequest objectTypeRequest) {
        ObjectType objectType = objectTypeMapper.toEntity(objectTypeRequest);
        return objectTypeRepository.save(objectType);
    }

    @Override
    @Transactional
    public ObjectType updateObjectType(Long id, ObjectTypeRequest objectTypeRequest) {
        ObjectType objectType = findObjectType(id);
        objectTypeMapper.updateObjectType(objectTypeRequest, objectType);
        return objectTypeRepository.save(objectType);
    }

    @Override
    public List<ObjectType> findAll() {
        return objectTypeRepository.findAll();
    }

    @Override
    public Optional<ObjectType> findById(Long id) {
        ObjectType objectType = findObjectType(id);
        if(objectType.isDeleted()) {
            throw new EntityIsDeletedException("ObjectType is deleted");
        }
        return Optional.of(objectType);
    }

    @Override
    public void deleteById(Long id) {
        objectTypeRepository.deleteById(id);
    }


    private ObjectType findObjectType(Long id) {
        return objectTypeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("ObjectType could not be found"));
    }
}
