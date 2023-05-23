package com.meters.service.impl;

import com.meters.entities.Role;
import com.meters.exceptoins.EntityIsDeletedException;
import com.meters.exceptoins.EntityNotFoundException;
import com.meters.mappers.RoleMapper;
import com.meters.repository.RoleRepository;
import com.meters.requests.create.RoleRequest;
import com.meters.requests.update.RoleUpdateRequest;
import com.meters.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    @Transactional
    public Role createRole(RoleRequest roleRequest) {
        Role role = roleMapper.toEntity(roleRequest);
        return roleRepository.save(role);
    }

    @Override
    @Transactional
    public Role updateRole(Long id, RoleUpdateRequest roleRequest) {
        Role role = findRole(id);
        roleMapper.updateRole(roleRequest, role);
        return roleRepository.save(role);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Page<Role> findAll(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    @Override
    public Optional<Role> findById(Long id) {
        Role role = findRole(id);
        if (role.isDeleted()) {
            throw new EntityIsDeletedException("Role is deleted");
        }
        return Optional.of(role);
    }

    @Override
    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }


    private Role findRole(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Role could not be found"));
    }
}
