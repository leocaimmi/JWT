package com.security.jwt.services;

import com.security.jwt.entities.Usuario;
import com.security.jwt.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    public boolean existeUsuario(String nombreUsuario) {
        return usuarioRepository.existsByNombreUsuario(nombreUsuario);
    }
    //Metodo para poder guardar un usuario sin la necesidad de usar el repositorio en otra clase
    public void save(Usuario usuario) {
        usuarioRepository.save(usuario);
    }
}


