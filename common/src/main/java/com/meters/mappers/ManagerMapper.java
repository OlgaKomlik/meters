package com.meters.mappers;

import com.meters.entities.AuthenticationInfo;
import com.meters.entities.Manager;
import com.meters.requests.create.ManagerRequest;
import com.meters.requests.update.ManagerUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class ManagerMapper {

    private final ModelMapper modelMapper;

    public Manager toEntity(ManagerRequest managerRequest) {
        Manager manager = modelMapper.map(managerRequest, Manager.class);
        manager.setAuthenticationInfo(new AuthenticationInfo(managerRequest.getEmail(), managerRequest.getPassword()));
        manager.setFullName(managerRequest.getManagerName() + " " + managerRequest.getSurname());
        return manager;
    }

    public Manager updateManager(ManagerUpdateRequest managerRequest, Manager manager) {
        if (managerRequest.getManagerName() != null) {
            manager.setManagerName(managerRequest.getManagerName());
        }
        if (managerRequest.getSurname() != null) {
            manager.setSurname(managerRequest.getSurname());
        }
        if (managerRequest.getBirthDate() != null) {
            manager.setBirthDate(managerRequest.getBirthDate());
        }
        if (managerRequest.getEmail() != null) {
            manager.getAuthenticationInfo().setEmail(managerRequest.getEmail());
        }
        if (managerRequest.getPassword() != null) {
            manager.getAuthenticationInfo().setPassword(managerRequest.getPassword());
        }
        if (managerRequest.getGender() != null) {
            manager.setGender(managerRequest.getGender());
        }
        if (managerRequest.getIsDeleted() != null) {
            manager.setDeleted(managerRequest.getIsDeleted());
        }
        manager.setFullName(manager.getManagerName() + " " + manager.getSurname());
        manager.setChanged(Timestamp.valueOf(LocalDateTime.now()));

        return manager;
    }
}
