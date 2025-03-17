package com.security.jwt.controller;

import com.security.jwt.data.DTO.request.AuthenticationRequest;
import com.security.jwt.data.DTO.request.RegisterRequest;
import com.security.jwt.data.DTO.response.AuthenticationResponse;
import com.security.jwt.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v3/auth")

public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param request Datos del usuario a registrar.
     * @return Respuesta con el token de autenticación del usuario.
     */
    @Operation(summary = "Registrar un nuevo usuario", description = "Permite registrar un nuevo usuario en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario registrado con éxito"),
            @ApiResponse(responseCode = "400", description = "Error en la solicitud")
    })
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(authService.register(request));
    }

    /**
     * Autentica a un usuario y genera un token JWT.
     *
     * @param request Datos de inicio de sesión del usuario.
     * @return El token de autenticación JWT generado para el usuario.
     */
    @Operation(summary = "Autenticar un usuario", description = "Permite autenticar un usuario y generar un token JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso"),
            @ApiResponse(responseCode = "401", description = "Credenciales incorrectas")
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody AuthenticationRequest request
    ){
        String token = authService.authenticate(request);
        return ResponseEntity.ok(token);
    }
}
