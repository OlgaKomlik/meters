package com.meters.service;

import com.meters.entities.Role;
import com.meters.requests.create.RoleRequest;
import com.meters.requests.update.RoleUpdateRequest;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    Role createRole(RoleRequest roleRequest);

    Role updateRole(Long id, RoleUpdateRequest roleRequest);

    @Cacheable("roles")
    List<Role> findAll();

    Page<Role> findAll(Pageable pageable);

    Optional<Role> findById(Long id);

    void deleteById(Long id);
}
