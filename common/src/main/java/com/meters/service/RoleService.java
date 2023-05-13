package com.meters.service;

import com.meters.dto.RoleDto;
import com.meters.entities.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Optional<Role> createRole(RoleDto roleDto);

    Optional<Role> updateRole(Long id, RoleDto roleDto);

    List<Role> findAll();
    //List<Role> findAll(int page, int size);

    Optional<Role> findById(Long id);
    Optional<Role> restoreDeletedRole(Long id);

    void deleteById(Long id);
    Role softDelete(Long id);
}
