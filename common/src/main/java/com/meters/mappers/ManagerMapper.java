package com.meters.mappers;

import com.meters.dto.ManagerDto;
import com.meters.entities.AuthenticationInfo;
import com.meters.entities.Manager;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class ManagerMapper {
    private final ModelMapper modelMapper;

    public Manager toEntity(ManagerDto managerDto) {
        Manager manager = modelMapper.map(managerDto, Manager.class);
        manager.setAuthenticationInfo(new AuthenticationInfo(managerDto.getEmail(), managerDto.getPassword()));
        return manager;
    }

    public ManagerDto toDto(Manager manager) {
        ManagerDto managerDto = modelMapper.map(manager, ManagerDto.class);
        managerDto.setEmail(manager.getAuthenticationInfo().getEmail());
        managerDto.setPassword(manager.getAuthenticationInfo().getPassword());
        return managerDto;
    }
    public Manager updateManager(ManagerDto managerDto, Manager manager) {
        if(managerDto.getManagerName() != null) {
            manager.setManagerName(managerDto.getManagerName());
        }
        if(managerDto.getSurname() != null) {
            manager.setSurname(managerDto.getSurname());
        }
        if(managerDto.getBirthDate() != null) {
            manager.setBirthDate(managerDto.getBirthDate());
        }
        if(managerDto.getEmail() != null) {
            manager.getAuthenticationInfo().setEmail(managerDto.getEmail());
        }
        if(managerDto.getPassword() != null) {
            manager.getAuthenticationInfo().setPassword(managerDto.getPassword());
        }
        if(managerDto.getGender() != null) {
            manager.setGender(managerDto.getGender());
        }
        if(managerDto.getFullName() != null) {
            manager.setFullName(managerDto.getFullName());
        }
        manager.setChanged(Timestamp.valueOf(LocalDateTime.now()));

        return manager;
    }
}
