package com.security.jwt.services;

import com.security.jwt.entities.Usuario;
import com.security.jwt.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UsuarioService implements UserDetailsService {

    private UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByNombreUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario " + username + " no encontrado"));
        System.out.println(usuario);
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(usuario.getRole().getNombre().toString());

        return new User(
                usuario.getNombreUsuario(),
                usuario.getPassword(),
                Collections.singleton(authority)
        );

    }
    public boolean existeUsuario(String nombreUsuario) {
        return usuarioRepository.existsByNombreUsuario(nombreUsuario);
    }
    //Metodo para poder guardar un usuario sin la necesidad de usar el repositorio en otra clase
    public void save(Usuario usuario) {
        usuarioRepository.save(usuario);
    }
}


