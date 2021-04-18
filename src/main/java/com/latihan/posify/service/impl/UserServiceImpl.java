package com.latihan.posify.service.impl;

import com.latihan.posify.dto.UserDto;
import com.latihan.posify.exception.NotFoundException;
import com.latihan.posify.mapper.UserMapper;
import com.latihan.posify.model.PasswordResetToken;
import com.latihan.posify.model.User;
import com.latihan.posify.repository.PasswordResetTokenRepository;
import com.latihan.posify.repository.UserRepository;
import com.latihan.posify.security.jwt.JwtProvider;
import com.latihan.posify.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserDto> getAllUser() {
        List<User> users = userRepository.findAll();
        return userMapper.entityListToDtoList(users);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isPresent()){
            return userMapper.entityToDto(userOptional.get());
        }
        else{
            throw new NotFoundException("User with username "+username+" Not Found");
        }

    }



}
