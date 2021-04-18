package com.latihan.posify.controller;
import com.latihan.posify.dto.ForgotPasswordDto;
import com.latihan.posify.dto.UserDto;
import com.latihan.posify.dto.request.LoginRequest;
import com.latihan.posify.dto.request.ResetPasswordRequest;
import com.latihan.posify.dto.request.SignUpRequest;
import com.latihan.posify.dto.response.JwtResponse;
import com.latihan.posify.model.Role;
import com.latihan.posify.model.RoleName;
import com.latihan.posify.model.User;
import com.latihan.posify.repository.RoleRepository;
import com.latihan.posify.repository.UserRepository;
import com.latihan.posify.security.jwt.JwtProvider;
import com.latihan.posify.security.services.UserPrinciple;
import com.latihan.posify.service.AuthService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    AuthService authService;

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        UserPrinciple userDetails = (UserPrinciple) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(userDetails.getId(), jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<String>("Fail -> Username is already taken!",
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<String>("Fail -> Email is already in use!",
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        strRoles.forEach(role -> {
            switch(role) {
                case "admin":
                    Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(adminRole);

                    break;
                case "superadmin":
                    Role saRole = roleRepository.findByName(RoleName.ROLE_SUPERADMIN)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(saRole);

                    break;
                default:
                    Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(userRole);
            }
        });

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok().body("User registered successfully!");
    }
    @PostMapping("/reset-password")
    public ResponseEntity<String> getResetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest){
        String message=authService.getResetPassword(resetPasswordRequest.getEmail());
        return new ResponseEntity<String>(message, HttpStatus.OK);
    }
    @GetMapping("/validate-token")
    public ResponseEntity<UserDto> getValidateToken(@RequestParam(name = "token") String token){
        UserDto userDto = authService.validateToken(token);
        return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
    }
    @PostMapping("/new-password")
    public ResponseEntity<String> getNewPassword(@RequestBody ForgotPasswordDto forgotPasswordDto){
        String message=authService.getNewPassword(forgotPasswordDto);
        return new ResponseEntity<String>(message,HttpStatus.OK);
    }

}
