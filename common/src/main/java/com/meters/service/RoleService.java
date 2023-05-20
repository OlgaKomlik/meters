package com.meters.service;

import com.meters.requests.RoleRequest;
import com.meters.entities.Role;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Optional<Role> createRole(RoleRequest roleRequest);

    Optional<Role> updateRole(Long id, RoleRequest roleRequest);

    @Cacheable("roles")
    List<Role> findAll();
    Page<Role> findAll(Pageable pageable);

    Optional<Role> findById(Long id);
    Optional<Role> activateRole(Long id);

    void deleteById(Long id);
    Role deactivate(Long id);
}
