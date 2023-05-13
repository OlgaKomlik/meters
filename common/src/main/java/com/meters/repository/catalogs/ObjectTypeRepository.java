package com.meters.repository.catalogs;

import com.meters.entities.catalogs.ObjectType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObjectTypeRepository extends JpaRepository<ObjectType, Long> {
}