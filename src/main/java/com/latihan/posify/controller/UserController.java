package com.latihan.posify.controller;

import com.latihan.posify.dto.UserDto;
import com.latihan.posify.dto.request.ResetPasswordRequest;
import com.latihan.posify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserDto>> getUserList() {
        List<UserDto> userDtos  = userService.getAllUser();
        return new ResponseEntity<List<UserDto>>(userDtos, HttpStatus.OK);
    }
    @GetMapping("/view")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDto> getUserByUsername(@RequestParam(name = "username") String username) {
        UserDto userDto  = userService.getUserByUsername(username);
        return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
    }


}
