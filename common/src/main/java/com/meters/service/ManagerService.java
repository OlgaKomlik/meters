package com.meters.service;

import com.meters.dto.ManagerDto;
import com.meters.entities.Manager;

import java.util.List;
import java.util.Optional;

public interface ManagerService {

    Optional<Manager> createManager(ManagerDto managerDto);

    Optional<Manager> updateManager(Long id, ManagerDto managerDto);

    List<Manager> findAll();

   /* List<Manager> findAll(int page, int size);*/

    Optional<Manager> findById(Long id);
    Optional<Manager> restoreDeletedManager(Long id);

    void deleteById(Long id);
    Manager softDelete(Long id);
    Optional<Manager> setUserRole(Long managerId, String roleName);
}
