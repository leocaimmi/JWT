package com.security.jwt.services;


import com.security.jwt.DTO.LoginUsuarioDto;
import com.security.jwt.entities.Usuario;
import com.security.jwt.repositories.RoleRepository;
import com.security.jwt.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public AuthService(UsuarioService usuarioService, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario authenticate(LoginUsuarioDto input) {
        try {
            // Busca al usuario en la base de datos por su email
            Usuario usuarioEntidad= usuarioRepository.findByNombreUsuario(input.getNombre())
                    .orElseThrow(() -> new UsernameNotFoundException("Email no fue encontrado en el sistema"));


            // Autentica las credenciales utilizando el AuthenticationManager
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getNombre(),
                            input.getPassword()
                    )
            );
            return usuarioEntidad;

        } catch (BadCredentialsException ex) {
            // Si las credenciales son incorrectas, lanza una excepción personalizada
            throw new BadCredentialsException("Correo o contraseña incorrectos", ex);
        } catch (Exception ex) {
            throw new RuntimeException("Error en el proceso de autenticación", ex);
        }
    }

}
