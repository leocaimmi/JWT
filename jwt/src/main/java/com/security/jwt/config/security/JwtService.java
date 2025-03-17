package com.security.jwt.config.security;

import com.security.jwt.model.Usuario;
import com.security.jwt.repository.UsuarioRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {
    /*TODO HACER UNA INTERFACE PARA METODOS DE JWT*/
    /*https://jwtsecret.com/generate#*/
    private final UsuarioRepository usuarioRepository;

    @Value("${jwt.secret}")
    private String jwtSecretKey;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    public JwtService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    //  - - - - Validaciones token - - - -  //

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }


    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // - - - - - - Claims  - - - - - - //
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {

        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }


    //  - - - - Creacion de token - - - -  //

    public String generateToken(
            UserDetails usuarioAutenticado) {

        Map<String, Object> claims = createExtraClaims(usuarioAutenticado);

        return buildToken(claims, usuarioAutenticado);
    }

    private Map<String, Object> createExtraClaims(UserDetails usuarioAutenticado) throws EntityNotFoundException {

        //buscamos la entidad
        Usuario usuario = usuarioRepository.findByEmail(usuarioAutenticado.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("Usuario con email: " + usuarioAutenticado.getUsername() + " no encontrado."));

        //creamos el map de claims
        Map<String, Object> claims = new HashMap<>();

        //agregamos extra claims
        claims.put("roles", usuario.getAuthorities());
        claims.put("name", usuario.getNombre());
        claims.put("id", usuario.getId());

        return claims;
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails) {
        long expirationTime = jwtExpiration; // Usa el tiempo de expiración basado en el rol

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis())) //fecha inicio
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) //fecha vto
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)  //firma con la clave secreta
                .compact();
    }


}
