package com.tobeto.hotelReservationSystem.services.concretes;

import com.tobeto.hotelReservationSystem.core.services.JwtService;
import com.tobeto.hotelReservationSystem.core.utils.exceptions.types.BusinessException;
import com.tobeto.hotelReservationSystem.entities.User;
import com.tobeto.hotelReservationSystem.repositories.AuthRepository;
import com.tobeto.hotelReservationSystem.services.abstracts.AuthService;
import com.tobeto.hotelReservationSystem.services.dtos.requests.auth.ChangePasswordRequest;
import com.tobeto.hotelReservationSystem.services.dtos.requests.auth.LoginAuthRequest;
import com.tobeto.hotelReservationSystem.services.dtos.requests.auth.RegisterAuthRequest;
import com.tobeto.hotelReservationSystem.services.dtos.responses.auth.ChangePasswordResponse;
import com.tobeto.hotelReservationSystem.services.dtos.responses.auth.LoginAuthResponse;
import com.tobeto.hotelReservationSystem.services.dtos.responses.auth.RegisterAuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public RegisterAuthResponse register(RegisterAuthRequest request) {

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPasswordConfirm(passwordEncoder.encode(request.getPasswordConfirm()));
        user.setUserEmail(request.getUserEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAuthorities(Collections.singletonList(request.getRole()));
        if(!request.getPassword().equals(request.getPasswordConfirm())) {
            throw new BusinessException("Şifreler eşleşmedi.");
        }

        authRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        RegisterAuthResponse response = new RegisterAuthResponse();
        response.setToken(jwtToken);
        return response;
    }

    @Override
    public LoginAuthResponse login(LoginAuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserEmail(),
                        request.getPassword()
                )
        );
        var user = authRepository.findByUserEmail(request.getUserEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        LoginAuthResponse response = new LoginAuthResponse();
        response.setToken(jwtToken);
        return response;
    }

    @Override
    public ChangePasswordResponse changePassword(ChangePasswordRequest request) {
        User user = authRepository.findById(request.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            authRepository.save(user);
            return new ChangePasswordResponse(true, "Password updated successfully");
        } else {
            return new ChangePasswordResponse(false, "Old password is incorrect");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authRepository.findByUserEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new org.springframework.security.core.userdetails.User(user.getUserEmail(), user.getPassword(), user.getAuthorities());
    }
}