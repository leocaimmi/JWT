package com.security.jwt.config.security;

import com.security.jwt.entities.Usuario;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import com.security.jwt.repositories.UsuarioRepository;


@Service
public class JwtServiceImpl implements JwtService {
    private final UsuarioRepository usuarioRepositorio;
    @Value("${jwt.secret}")
    private String jwtSecretKey;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    /*@Value("${jwt.expirationNegocio}")
    private Long jwtExpirationNegocio;*/
    public JwtServiceImpl(UsuarioRepository usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    private Key getSignInKey() {
        //Logica para obtener la clave secreta jwt, para la firma
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    // - - - - - - Validaciones token - - - - - - //

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        Date fechaVto = extractExpiration(token);
        return fechaVto.before(new Date());
    }

    // - - - - - - Claims  - - - - - - //

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    @Override
    public String extractUsername(String token) {
        //extraemos el email
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        //logica para obtener todos los claims de un token jwt

        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    // - - - - - - Creacion de token - - - - - - //

    @Override
    public String generateToken(
            UserDetails usuarioAutenticado) {

        Map<String,Object> claims = createExtraClaims(usuarioAutenticado);

        return buildToken(claims, usuarioAutenticado);
    }

    private Map<String,Object> createExtraClaims(UserDetails usuarioAutenticado) throws EntityNotFoundException{

        //buscamos la entidad
        Usuario usuarioEntidad = usuarioRepositorio.findByNombreUsuario(usuarioAutenticado.getUsername())
                .orElseThrow(()-> new EntityNotFoundException("Usuario : "+usuarioAutenticado.getUsername()+" no encontrado."));

        //creamos el map de claims
        Map<String, Object> claims = new HashMap<>();

        //agregamos extra claims
        claims.put("roles", usuarioEntidad.getAuthorities());
        claims.put("name",usuarioEntidad.getUsername());
        claims.put("id", usuarioEntidad.getId());

        return claims;
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    )
    {
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
    /**
     *
     * @Este metodo se utilizaria si tengo roles con distinta expiracion
     *
     * @*/
    /*private long getExpirationTime(UserDetails userDetails) {
        Optional<Usuario> usuarioOpt = usuarioRepositorio.findByNombreUsuario(userDetails.getUsername());

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (usuario.getRole().getNombre() == ROLE_USER) {
                return jwtExpiration;
            } else if (usuario.getRole().getNombre() == RolUsuarioEnum.NEGOCIO) {
                return jwtExpirationNegocio;
            }
        }
        return jwtExpiration; //Si es otro rol uso el mismo que el cliente
    }*/




}
