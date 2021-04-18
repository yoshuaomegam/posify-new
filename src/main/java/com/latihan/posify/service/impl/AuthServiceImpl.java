package com.latihan.posify.service.impl;

import com.latihan.posify.dto.ForgotPasswordDto;
import com.latihan.posify.dto.UserDto;
import com.latihan.posify.dto.request.EmailRequest;
import com.latihan.posify.exception.NotFoundException;
import com.latihan.posify.mapper.UserMapper;
import com.latihan.posify.model.PasswordResetToken;
import com.latihan.posify.model.User;
import com.latihan.posify.repository.PasswordResetTokenRepository;
import com.latihan.posify.repository.UserRepository;
import com.latihan.posify.security.jwt.JwtProvider;
import com.latihan.posify.service.AuthService;
import com.latihan.posify.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    private MailService mailService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    PasswordEncoder encoder;
    @Override
    public String getResetPassword(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if(userOptional.isPresent()){
            String token = jwtProvider.generateRequestPasswordToken(userOptional.get().getUsername());
            PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByUser(userOptional.get());
            if(passwordResetToken==null){
                PasswordResetToken passwordResetToken1= new PasswordResetToken();
                passwordResetToken1.setUser(userOptional.get());
                passwordResetToken1.setToken(token);
                passwordResetTokenRepository.save(passwordResetToken1);
            }
            else{
                passwordResetToken.setToken(token);
                passwordResetTokenRepository.save(passwordResetToken);
            }
            EmailRequest emailRequest = new EmailRequest("Forgot Password POSIFY", userOptional.get().getEmail(), "To complete the password reset process, please click here: http://localhost:8000/auth/validate-token?token="+token);
            mailService.send(emailRequest);
            return "Please check email to reset password";
        }
        else{
            throw new NotFoundException("User with email "+email+" Not Found");
        }
    }

    @Override
    public UserDto validateToken(String token) {
        Optional<PasswordResetToken> passwordResetTokenOptional = passwordResetTokenRepository.findByToken(token);
        if(passwordResetTokenOptional.isPresent()){
            UserDto userDto = userMapper.entityToDto(passwordResetTokenOptional.get().getUser());
            return  userDto;
        }
        else{
            throw new NotFoundException("Token Not Found");
        }
    }


    @Override
    public String getNewPassword(ForgotPasswordDto forgotPasswordDto) {
        Optional<User> userOptional = userRepository.findByUsername(forgotPasswordDto.getUsername());
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setPassword(encoder.encode(forgotPasswordDto.getPassword()));
            userRepository.save(user);
            return "Password Has been changed";
        }
        else{
            throw new NotFoundException("User Not Found");
        }
    }


}
