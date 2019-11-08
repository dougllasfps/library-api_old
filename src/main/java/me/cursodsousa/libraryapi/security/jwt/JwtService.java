package me.cursodsousa.libraryapi.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import me.cursodsousa.libraryapi.LibraryApiApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${security.jwt.signingkey}")
    private String signingKey;

    @Value("${security.jwt.expiration}")
    private Long expiration;

    private static final String[] claimsKeys = {"sub", "created", "role"};

    public String generateToken( String user ){
        return
            Jwts
                .builder()
                .setClaims( tokenClaims(user) )
                .setExpiration( generateExpirationDateFromNow() )
                .signWith( SignatureAlgorithm.HS512, signingKey )
                .compact();
    }

    private Date generateExpirationDateFromNow() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiration = now.plusMinutes(this.expiration);
        return Date.from(expiration.atZone(ZoneId.systemDefault()).toInstant());
    }

    public Claims getClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody();
    }

    public String getUserFromToken(String token){
        Claims claimsFromToken = getClaimsFromToken(token);
        if(claimsFromToken == null){
            return null;
        }

        return claimsFromToken.getSubject();
    }

    public LocalDateTime getExpiration(String token){
        Claims claimsFromToken = getClaimsFromToken(token);
        if(claimsFromToken == null){
            return null;
        }
        Date expiration = claimsFromToken.getExpiration();
        return expiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public boolean isTokenExpirado(String token){
        LocalDateTime expiration = getExpiration(token);
        return LocalDateTime.now().isAfter(expiration);
    }

    private Map<String, Object> tokenClaims(String userName) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(claimsKeys[0], userName);
        claims.put(claimsKeys[1], new Date());
        claims.put(claimsKeys[2], "USER");
        return claims;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext app = SpringApplication.run(LibraryApiApplication.class);
        JwtService service = app.getBean(JwtService.class);
        String token = service.generateToken("dougllas.sousa");
        System.out.println(token);

        Claims claimsFromToken = service.getClaimsFromToken(token);
        System.out.println(claimsFromToken);

        boolean tokenExpirado = service.isTokenExpirado(token);
        System.out.println(tokenExpirado);

        System.out.println(service.getUserFromToken(token));
    }
}
