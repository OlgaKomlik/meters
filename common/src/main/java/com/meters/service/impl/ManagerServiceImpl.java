package com.meters.service.impl;

import com.meters.dto.ManagerDto;
import com.meters.entities.Manager;
import com.meters.entities.Role;
import com.meters.mappers.ManagerMapper;
import com.meters.exceptoins.EntityIsDeletedException;
import com.meters.exceptoins.EntityNotFoundException;
import com.meters.repository.ManagerRepository;
import com.meters.repository.RoleRepository;
import com.meters.service.ManagerService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.Cacheable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ManagerServiceImpl implements ManagerService {
    private static final Long DEFAULT_ROLE_ID = 1L;

    private final ManagerRepository managerRepository;
    private final ManagerMapper managerMapper;
    private final RoleRepository roleRepository;

        @Override
    public List<Manager> findAll() {
        return managerRepository.findAll();
    }

   /* @Cacheable("managers")
    @Override
    public List<Manager> findAll(int page, int size) {
        Page<Manager> managersPage = managerRepository.findAll(PageRequest.of(page, size));
        return managersPage.stream().toList();
    }*/

    @Override
    public Optional<Manager> findById(Long id) {
        Manager manager = findManager(id);
        if (manager.getIsDeleted().equals(Boolean.TRUE)) {
            throw new EntityIsDeletedException("Manager is deleted");
        }
        return Optional.of(manager);
    }

    @Override
    public Optional<Manager> createManager(ManagerDto managerDto) {
        Manager manager = managerMapper.toEntity(managerDto);
        manager.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        manager.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        Role role = roleRepository.findById(DEFAULT_ROLE_ID).orElseThrow(() -> new EntityNotFoundException("Role not exist"));
        manager.getRoles().add(role);
        return Optional.of(managerRepository.save(manager));
    }

    @Override
    public Optional<Manager> updateManager(Long id, ManagerDto managerDto) {
        Manager manager = findManager(id);
        managerMapper.updateManager(managerDto, manager);
        return Optional.of(managerRepository.save(manager));
    }

    @Override
    public void deleteById(Long id) {
        managerRepository.deleteById(id);
    }

    @Override
    public Manager softDelete(Long id) {
        Manager manager = findManager(id);
        manager.setIsDeleted(true);
        manager.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return managerRepository.save(manager);
    }

    @Override
    public Optional<Manager> restoreDeletedManager(Long id) {
        Manager manager = findManager(id);
        manager.setIsDeleted(false);
        manager.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(managerRepository.save(manager));
    }
    private Manager findManager(Long id) {
        return managerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Manager could not be found"));
    }

    public Optional<Manager> setUserRole(Long managerId, String roleName) {
        Manager manager = findManager(managerId);
        Role managerRole = roleRepository.findByRoleName(roleName).orElseThrow(() -> new EntityNotFoundException("Role not exist"));
        manager.getRoles().add(managerRole);
        return Optional.of(managerRepository.save(manager));
    }
}
