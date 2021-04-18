package com.latihan.posify.service;

import com.latihan.posify.dto.ForgotPasswordDto;
import com.latihan.posify.dto.UserDto;

public interface AuthService {
    public String getResetPassword(String email);

    public UserDto validateToken(String token);
    public String getNewPassword(ForgotPasswordDto forgotPasswordDto);
}
