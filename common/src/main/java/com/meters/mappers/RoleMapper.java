package com.meters.mappers;

import com.meters.dto.RoleDto;
import com.meters.entities.Role;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class RoleMapper {
    private final ModelMapper modelMapper;

    public Role toEntity(RoleDto roleDto) {
        return modelMapper.map(roleDto, Role.class);
    }

    public RoleDto toDto(Role role) {
        return modelMapper.map(role, RoleDto.class);
    }
    public Role updateRole(RoleDto roleDto, Role role) {
        if(roleDto.getRoleName() != null) {
            role.setRoleName(roleDto.getRoleName());
        }
        role.setChanged(Timestamp.valueOf(LocalDateTime.now()));

        return role;
    }
}
