package com.meters.service.catalogs.impl;

import com.meters.dto.catalogs.ObjectTypeDto;
import com.meters.entities.catalogs.ObjectType;
import com.meters.exceptoins.EntityIsDeletedException;
import com.meters.exceptoins.EntityNotFoundException;
import com.meters.mappers.catalogs.ObjectTypeMapper;
import com.meters.repository.catalogs.ObjectTypeRepository;
import com.meters.service.catalogs.ObjectTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ObjectTypeServiceImpl implements ObjectTypeService {
    private final ObjectTypeRepository objectTypeRepository;

    private final ObjectTypeMapper objectTypeMapper;

    @Override
    public Optional<ObjectType> createObjectType(ObjectTypeDto objectTypeDto) {
        ObjectType objectType = objectTypeMapper.toEntity(objectTypeDto);
        objectType.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        objectType.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(objectTypeRepository.save(objectType));
    }

    @Override
    public Optional<ObjectType> updateObjectType(Long id, ObjectTypeDto objectTypeDto) {
        ObjectType objectType = findObjectType(id);
        objectTypeMapper.updateObjectType(objectTypeDto, objectType);
        return Optional.of(objectTypeRepository.save(objectType));
    }

    @Override
    public List<ObjectType> findAll() {
        return objectTypeRepository.findAll();
    }

    @Override
    public Optional<ObjectType> findById(Long id) {
        ObjectType objectType = findObjectType(id);
        if(objectType.getIsDeleted().equals(Boolean.TRUE)) {
            throw new EntityIsDeletedException("ObjectType is deleted");
        }
        return Optional.of(objectType);
    }

    @Override
    public Optional<ObjectType> restoreDeletedObjectType(Long id) {
        ObjectType objectType = findObjectType(id);
        objectType.setIsDeleted(false);
        objectType.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(objectTypeRepository.save(objectType));
    }

    @Override
    public void deleteById(Long id) {
        objectTypeRepository.deleteById(id);
    }

    @Override
    public ObjectType softDelete(Long id) {
        ObjectType objectType = findObjectType(id);
        objectType.setIsDeleted(true);
        objectType.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return objectTypeRepository.save(objectType);
    }

    private ObjectType findObjectType(Long id) {
        return objectTypeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("ObjectType could not be found"));
    }
}
