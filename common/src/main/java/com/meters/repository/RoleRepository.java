package com.meters.repository;

import com.meters.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface RoleRepository extends
        JpaRepository<Role, Long>,
        PagingAndSortingRepository<Role, Long>,
        CrudRepository<Role, Long> {
    Optional<Role> findByRoleName(String roleName);
}
