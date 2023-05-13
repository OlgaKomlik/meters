package com.meters.repository;

import com.meters.entities.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ManagerRepository extends
        JpaRepository<Manager, Long>,
        PagingAndSortingRepository<Manager, Long>,
        CrudRepository<Manager, Long> {
}
