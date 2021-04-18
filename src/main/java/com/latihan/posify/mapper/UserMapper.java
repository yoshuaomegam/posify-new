package com.latihan.posify.mapper;

import com.latihan.posify.dto.RoleDto;
import com.latihan.posify.dto.UserDto;
import com.latihan.posify.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class UserMapper {
    @Autowired
    private RoleMapper roleMapper;
    public UserDto entityToDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        if(user.getRoles()!=null){
            userDto.setRole(roleMapper.entityToDto(user.getRoles().iterator().next()));
        }
        return userDto;
    }
    public List<UserDto> entityListToDtoList(List<User> users){
        List<UserDto> userDtos = new ArrayList<>();
        users.forEach(user -> {
            userDtos.add(this.entityToDto(user));
        });
        return userDtos;
    }

}

