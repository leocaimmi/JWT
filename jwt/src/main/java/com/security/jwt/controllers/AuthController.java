package com.security.jwt.controllers;


import com.security.jwt.DTO.LoginUsuarioDto;
import com.security.jwt.DTO.NuevoUsuarioDto;
import com.security.jwt.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @GetMapping("/check")
    public ResponseEntity<String> check() {
        System.out.println("HOLAAAAA");
        return ResponseEntity.ok().body("Autenticado.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginUsuarioDto loginUsuarioDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Error en las credenciales.");
        }
        try {
            String jwt = authService.authenticate(loginUsuarioDto.getNombre(), loginUsuarioDto.getPassword());
            return ResponseEntity.ok(jwt);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody NuevoUsuarioDto nuevoUsuarioDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Revisar los campos.");
        }
        try {
            authService.register(nuevoUsuarioDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Registrado correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }





}
