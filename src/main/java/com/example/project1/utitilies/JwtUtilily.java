package com.example.project1.utitilies;

import com.example.project1.payload.response.JWTPayLoad;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Service
public class JwtUtilily {
    @Value("${jwt.secret}")
    private String SECRET_KEY;
    @Value("${jwt.signature-algorithm}")
    private SignatureAlgorithm ALGORITHM;
    public Claims extractAllClaim(String token){
        try {
            return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        } catch (UnsupportedJwtException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaim(token);
        return claimsResolver.apply(claims);
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String createToken(Map<String, Object> claims, String subject, int minutes) {
        LocalDateTime localDateTime= LocalDateTime.now();
        localDateTime = localDateTime.plusMinutes(minutes);
        Date expireTime = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expireTime)
                .signWith(ALGORITHM, SECRET_KEY).compact();
    }

    public String generateToken(JWTPayLoad jwtPayLoad, int minutes) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", jwtPayLoad.getUserID());
        claims.put("email", jwtPayLoad.getEmail());
        claims.put("session", jwtPayLoad.getSession());
        return createToken(claims, jwtPayLoad.getEmail(), minutes);
    }

    public String generateTokenForEmail(String email, int minutes) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, email, minutes);
    }
}
