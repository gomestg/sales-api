package io.github.gomestg.security.jwt;

import io.github.gomestg.domain.entity.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.expiration}")
    private String expiration;
    @Value("${security.jwt.signature-key}")
    private String signatureKey;

    public String generateToken(Users user) {
        LocalDateTime dateTimeExpiration = LocalDateTime.now().plusMinutes(Long.valueOf(expiration));
        Date date = Date.from(dateTimeExpiration.atZone(ZoneId.systemDefault()).toInstant());
        return Jwts
                .builder()
                .setSubject(user.getLogin())
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, signatureKey)
                .compact();
    }

    private Claims getClaims(String token) throws ExpiredJwtException {
        return Jwts
                .parser()
                .setSigningKey(signatureKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validToken(String token) {
        try {
            LocalDateTime date = getClaims(token).getExpiration().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(date);
        } catch (Exception e) {
            return false;
        }
    }

    public String getLoginUser(String token) throws ExpiredJwtException {
        return (String) getClaims(token).getSubject();
    }

}
