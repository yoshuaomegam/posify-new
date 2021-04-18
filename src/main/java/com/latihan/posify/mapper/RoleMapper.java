package com.latihan.posify.mapper;

import com.latihan.posify.dto.RoleDto;
import com.latihan.posify.model.Role;
import org.springframework.stereotype.Service;

@Service
public class RoleMapper {
    public RoleDto entityToDto(Role role){
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setName(role.getName().name());
        return roleDto;
    }
}
