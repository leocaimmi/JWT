package com.security.jwt.services;


import com.security.jwt.DTO.NuevoUsuarioDto;
import com.security.jwt.entities.Role;
import com.security.jwt.entities.Usuario;
import com.security.jwt.enums.RoleList;
import com.security.jwt.jwt.JwtUtil;
import com.security.jwt.repositories.RoleRepository;
import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioService usuarioService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(UsuarioService usuarioService, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.usuarioService = usuarioService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public String authenticate(String nombreUsuario, String password){
        // El nombreUsuario se valida con el UsuarioService, no es necesario acceder al UsuarioRepository directamente
        if (!usuarioService.existeUsuario(nombreUsuario)) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(nombreUsuario, password);
        System.out.println(authenticationToken);
        Authentication authResult = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authResult);
        System.out.println("JOLFOSIANFOSAINFSAOIF");
        System.out.println(jwtUtil.generateToken(authResult));
        return jwtUtil.generateToken(authResult);
    }

    public void register(NuevoUsuarioDto nuevoUsuarioDto){
        if(usuarioService.existeUsuario(nuevoUsuarioDto.getNombre())){
            throw new IllegalArgumentException("El nombre ya existe");
        }
        Role roleUsuario = roleRepository.findByNombre(RoleList.ROLE_USER).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        Usuario usuario = new Usuario(nuevoUsuarioDto.getNombre(), passwordEncoder.encode(nuevoUsuarioDto.getPassword()), roleUsuario);
        usuarioService.save(usuario);
    }
}
