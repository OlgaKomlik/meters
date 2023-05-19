package com.meters.service;

import com.meters.requests.ManagerRequest;
import com.meters.entities.Manager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ManagerService {

    Optional<Manager> createManager(ManagerRequest managerRequest);

    Optional<Manager> updateManager(Long id, ManagerRequest managerRequest);

    List<Manager> findAll();
    Page<Manager> findAll(Pageable pageable);

    Optional<Manager> findById(Long id);
    Optional<Manager> restoreDeletedManager(Long id);

    void deleteById(Long id);
    Manager deactivate(Long id);
    Optional<Manager> setUserRole(Long managerId, String roleName);

    List<Manager> findBirthDayManagers(LocalDateTime localDateTime);

    Map<String, Double> getAverageFeeOfManagers();

    List<Manager> findByFullNameContainingIgnoreCase(String query);

    Manager getBestSellerOfTheMonth(int month, int year);
}
