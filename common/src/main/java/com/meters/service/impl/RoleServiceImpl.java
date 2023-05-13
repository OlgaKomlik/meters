package com.meters.service.impl;

import com.meters.dto.RoleDto;
import com.meters.entities.Role;
import com.meters.mappers.RoleMapper;
import com.meters.repository.RoleRepository;
import com.meters.service.RoleService;
import com.meters.exceptoins.EntityIsDeletedException;
import com.meters.exceptoins.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public Optional<Role> createRole(RoleDto roleDto) {
        Role role = roleMapper.toEntity(roleDto);
        role.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        role.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(roleRepository.save(role));
    }

    @Override
    public Optional<Role> updateRole(Long id, RoleDto roleDto) {
        Role role = findRole(id);
        roleMapper.updateRole(roleDto, role);
        return Optional.of(roleRepository.save(role));
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> findById(Long id) {
        Role role = findRole(id);
        if(role.getIsDeleted().equals(Boolean.TRUE)) {
            throw new EntityIsDeletedException("Role is deleted");
        }
        return Optional.of(role);
    }

    @Override
    public Optional<Role> restoreDeletedRole(Long id) {
        Role role = findRole(id);
        role.setIsDeleted(false);
        role.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(roleRepository.save(role));
    }

    @Override
    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Role softDelete(Long id) {
        Role role = findRole(id);
        role.setIsDeleted(true);
        role.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return roleRepository.save(role);
    }

    private Role findRole(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Role could not be found"));
    }
}
