package com.meters.service.impl;

import com.meters.entities.Manager;
import com.meters.entities.Role;
import com.meters.exceptoins.EntityIsDeletedException;
import com.meters.exceptoins.EntityNotFoundException;
import com.meters.mappers.ManagerMapper;
import com.meters.repository.ManagerRepository;
import com.meters.repository.RoleRepository;
import com.meters.requests.ManagerRequest;
import com.meters.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

    @Override
    public Page<Manager> findAll(Pageable pageable) {
        return managerRepository.findAll(pageable);
    }

    @Override
    public Optional<Manager> findById(Long id) {
        Manager manager = findManager(id);
        if (manager.isDeleted()) {
            throw new EntityIsDeletedException("Manager is deleted");
        }
        return Optional.of(manager);
    }

    @Override
    @Transactional
    public Optional<Manager> createManager(ManagerRequest managerRequest) {
        Manager manager = managerMapper.toEntity(managerRequest);
        manager.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        manager.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        Role role = roleRepository.findById(DEFAULT_ROLE_ID).orElseThrow(() -> new EntityNotFoundException("Role not exist"));
        manager.getRoles().add(role);
        return Optional.of(managerRepository.save(manager));
    }

    @Override
    @Transactional
    public Optional<Manager> updateManager(Long id, ManagerRequest managerRequest) {
        Manager manager = findManager(id);
        managerMapper.updateManager(managerRequest, manager);
        return Optional.of(managerRepository.save(manager));
    }

    @Override
    public void deleteById(Long id) {
        managerRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Manager deactivate(Long id) {
        Manager manager = findManager(id);
        manager.setDeleted(true);
        manager.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return managerRepository.save(manager);
    }

    @Override
    @Transactional
    public Optional<Manager> activateManager(Long id) {
        Manager manager = findManager(id);
        manager.setDeleted(false);
        manager.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(managerRepository.save(manager));
    }

    private Manager findManager(Long id) {
        return managerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Manager could not be found"));
    }

    @Override
    @Transactional
    public Optional<Manager> setUserRole(Long managerId, String roleName) {
        Manager manager = findManager(managerId);
        Role managerRole = roleRepository.findByRoleName(roleName).orElseThrow(() -> new EntityNotFoundException("Role not exist"));
        manager.getRoles().add(managerRole);
        return Optional.of(managerRepository.save(manager));
    }

    @Override
    public List<Manager> findBirthDayManagers(LocalDateTime localDateTime) {
        Integer day = localDateTime.getDayOfMonth();
        Integer month = localDateTime.getMonthValue();
        return managerRepository.findBirthDayManagers(day, month);
    }

    @Override
    public Map<String, Double> getAverageFeeOfManagers() {
        List<Object[]> result = managerRepository.getAverageFeeOfManagers();
        Map<String, Double> managersFee = new LinkedHashMap<>();
        for (Object[] row : result) {
            Manager manager = managerRepository.findById((Long) row[0]).orElseThrow(() -> new EntityNotFoundException("Manager could not be found"));
            Double averageFee = Math.ceil((Double) row[1]);
            managersFee.put(manager.getFullName(), averageFee);
        }
        return managersFee;
    }

    @Override
    public List<Manager> findByFullNameContainingIgnoreCase(String query) {
        return managerRepository.findByFullNameContainingIgnoreCase(query);
    }

    @Override
    public Manager getBestSellerOfTheMonth(int month, int year) {
        return managerRepository.getBestSellerOfTheMonth(month, year);
    }

/*    public List<Object []> getBestSellersOfTheMonth(int month, int year) {
        return managerRepository.getBestSellersOfTheMonth(month, year);
    }*/
}
