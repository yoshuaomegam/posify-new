package com.latihan.posify.service;

import com.latihan.posify.dto.UserDto;

import java.util.List;

public interface UserService {
    public List<UserDto> getAllUser();
    public UserDto getUserByUsername(String username);

}
