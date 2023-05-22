package com.meters.service;

import com.meters.requests.create.ManagerRequest;
import com.meters.entities.Manager;
import com.meters.requests.update.ManagerUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ManagerService {

    Manager createManager(ManagerRequest managerRequest);

    Manager updateManager(Long id, ManagerUpdateRequest managerRequest);

    List<Manager> findAll();
    Page<Manager> findAll(Pageable pageable);

    Optional<Manager> findById(Long id);

    void deleteById(Long id);

    Manager setUserRole(Long managerId, String roleName);

    List<Manager> findBirthDayManagers(LocalDateTime localDateTime);

    Map<String, Double> getAverageFeeOfManagers();

    List<Manager> findByFullNameContainingIgnoreCase(String query);

    List<Manager> getBestSellersOfTheMonth(int month, int year);
}
