package com.security.jwt.services;

import com.security.jwt.config.security.JwtService;
import com.security.jwt.data.DTO.request.AuthenticationRequest;
import com.security.jwt.data.DTO.response.AuthenticationResponse;
import com.security.jwt.data.DTO.request.RegisterRequest;
import com.security.jwt.model.Usuario;
import com.security.jwt.repository.UsuarioRepository;
import com.security.jwt.utils.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(RegisterRequest request) {
        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .rol(Role.USUARIO)
                .build();

        usuarioRepository.save(usuario);

        String token = jwtService.generateToken(usuario);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    public String authenticate(AuthenticationRequest request) {

        Usuario usuario = usuarioRepository.findByEmail(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(request.getUsername()));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        return jwtService.generateToken(usuario);


    }

}

