package com.security.jwt.controllers;



import com.security.jwt.DTO.LoginUsuarioDto;
import com.security.jwt.config.security.JwtServiceImpl;
import com.security.jwt.entities.Usuario;
import com.security.jwt.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtServiceImpl jwtServiceImpl;
    private final AuthService authService;

    public AuthController(JwtServiceImpl jwtServiceImpl, AuthService authService) {
        this.jwtServiceImpl = jwtServiceImpl;
        this.authService = authService;
    }

    /**Se utiliza POST para no enviar la contraseña por URL eso hace que sea mas seguro el proceso*/
    @PostMapping("/login")
    @Operation(summary = "Login de un usuario, Se necesita email y contraseña")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El usuario con los datos solicitados fue devuelto"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos")
    })
    public ResponseEntity<String> login(
            @RequestBody LoginUsuarioDto usuarioLoginRequest) {

        Usuario usuarioAutenticado = authService.authenticate(usuarioLoginRequest);

        String jwtToken = jwtServiceImpl.generateToken(usuarioAutenticado);

        return ResponseEntity.ok(jwtToken); // 200 OK con usuario y credenciales
    }






}
