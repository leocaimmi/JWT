package com.security.jwt.config.security;

import com.security.jwt.repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {


    private final UsuarioRepository usuarioRepositorio;

    public JpaUserDetailsService(UsuarioRepository usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepositorio.findByNombreUsuario(username).orElseThrow(()->new UsernameNotFoundException("Usuario "+username+" no encontrado"));
    }
}
