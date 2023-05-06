package com.meters.entities.mappers;

import com.meters.dto.ManagerDto;
import com.meters.entities.Manager;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ManagerMapper {
    private final ModelMapper modelMapper;

    public Manager toEntity(ManagerDto managerDto) {
        return modelMapper.map(managerDto, Manager.class);
    }

    public ManagerDto toDto(Manager manager) {
        return modelMapper.map(manager, ManagerDto.class);
    }
    public Manager updateManager(ManagerDto newManager, Manager changeManager) {
        modelMapper.map(newManager, changeManager);
        return changeManager;
    }
}
