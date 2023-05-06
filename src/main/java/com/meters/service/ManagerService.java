package com.meters.service;

import com.meters.dto.ManagerDto;
import com.meters.entities.Manager;

import java.util.List;
import java.util.Optional;

public interface ManagerService {
    List<Manager> findAll();

    Optional<Manager> findById(Long id);

    Optional<Manager> createManager(ManagerDto managerDto);

    Manager updateManager(Long id, ManagerDto managerDto);

    void deleteById(Long id);
}
