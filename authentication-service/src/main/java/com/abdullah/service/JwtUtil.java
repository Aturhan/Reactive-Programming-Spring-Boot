package com.abdullah.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {
    //@Value("${jwt.secret}")
    private String SECRET_KEY =
            "GWjtA5DqSZ4dwrLcy7gvTruNxbgca3OiWsAxIhiQn2c3yDMV6TO5zQvEtMqyt5nfxqdSPQURXSD63bYCqXpbqYe8EPkiFDsL4EjntqKAZouJIG/qQNvQg+R+O6JJqyseUh6OF9UmNmxeALmAP39yzoUieiuANGCYboiRc3DcgUc=";
   // @Value("${jwt.expiration}")
    private String EXPIRATION ="86400";

    private Key key;
    @Autowired
    public JwtUtil() {
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJwt(token).getBody();
    }

    public Date getExpiration(String token) {
        return getClaims(token).getExpiration();
    }

    private boolean isExpired(String token) {
        return getExpiration(token).before(new Date());
    }

    public String generate(String userId,String email,String tokenType) {
        Map<String ,String> claims = Map.of("id",userId,"email",email);
        return buildToken(claims,tokenType);
    }

    private String buildToken(Map<String,String> claims,String tokenType) {
        long expMills = "ACCESS".equalsIgnoreCase(tokenType)
                ? Long.parseLong(EXPIRATION) * 1000
                : Long.parseLong(EXPIRATION) * 1000 * 5;
        final Date now = new Date();
        final Date exp = new Date(now.getTime() * expMills);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(claims.get("email"))
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key)
                .compact();
    }
}
