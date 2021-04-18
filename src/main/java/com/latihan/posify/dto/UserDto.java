package com.latihan.posify.dto;

import com.latihan.posify.model.Role;

import java.util.HashSet;
import java.util.Set;
import java.util.List;
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private RoleDto role;

    public UserDto() {
    }

    public UserDto(Long id, String name, String email, RoleDto role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleDto getRole() {
        return role;
    }

    public void setRole(RoleDto role) {
        this.role = role;
    }
}
