package com.meters.service.impl;

import com.meters.entities.Role;
import com.meters.exceptoins.EntityIsDeletedException;
import com.meters.exceptoins.EntityNotFoundException;
import com.meters.mappers.RoleMapper;
import com.meters.repository.RoleRepository;
import com.meters.requests.RoleRequest;
import com.meters.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    @Transactional
    public Optional<Role> createRole(RoleRequest roleRequest) {
        Role role = roleMapper.toEntity(roleRequest);
        role.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        role.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(roleRepository.save(role));
    }

    @Override
    @Transactional
    public Optional<Role> updateRole(Long id, RoleRequest roleRequest) {
        Role role = findRole(id);
        roleMapper.updateRole(roleRequest, role);
        return Optional.of(roleRepository.save(role));
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
    @Transactional
    public Optional<Role> activateRole(Long id) {
        Role role = findRole(id);
        role.setDeleted(false);
        role.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(roleRepository.save(role));
    }

    @Override
    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Role deactivate(Long id) {
        Role role = findRole(id);
        role.setDeleted(true);
        role.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return roleRepository.save(role);
    }

    private Role findRole(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Role could not be found"));
    }
}
