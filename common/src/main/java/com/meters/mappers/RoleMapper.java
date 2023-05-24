package com.meters.mappers;

import com.meters.entities.Role;
import com.meters.requests.create.RoleRequest;
import com.meters.requests.update.RoleUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class RoleMapper {

    private final ModelMapper modelMapper;

    public Role toEntity(RoleRequest roleRequest) {
        return modelMapper.map(roleRequest, Role.class);
    }

    public Role updateRole(RoleUpdateRequest roleRequest, Role role) {

        if (roleRequest.getRoleName() != null) {
            role.setRoleName(roleRequest.getRoleName());
        }

        if (roleRequest.getIsDeleted() != null) {
            role.setDeleted(roleRequest.getIsDeleted());
        }
        role.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return role;
    }
}
