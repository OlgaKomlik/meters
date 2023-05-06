package com.meters.service.impl;

import com.meters.dto.ManagerDto;
import com.meters.entities.Manager;
import com.meters.entities.mappers.ManagerMapper;
import com.meters.repository.ManagerRepository;
import com.meters.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ManagerServiceImpl implements ManagerService {
    private final ManagerRepository managerRepository;
    private final ManagerMapper managerMapper;


    @Override
    public List<Manager> findAll() {
        return managerRepository.findAll();
    }

    @Override
    public Optional<Manager> findById(Long id) {
        return managerRepository.findById(id);
    }

    @Override
    public Optional<Manager> createManager(ManagerDto managerDto) {
        Manager manager = managerMapper.toEntity(managerDto);
        manager.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        manager.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        managerRepository.save(manager);
        return findById(manager.getId());
    }

    @Override
    public Manager updateManager(Long id, ManagerDto managerDto) {
        Manager manager = managerRepository.findById(id).orElseThrow();
        managerMapper.updateManager(managerDto, manager);
        manager.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        managerRepository.save(manager);
        return manager;
    }

    @Override
    public void deleteById(Long id) {
        managerRepository.deleteById(id);
    }
}
