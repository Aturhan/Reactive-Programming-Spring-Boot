package com.abdullah.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
@Component
public class JwtUtil {
    private String SECRET_KEY =
            "GWjtA5DqSZ4dwrLcy7gvTruNxbgca3OiWsAxIhiQn2c3yDMV6TO5zQvEtMqyt5nfxqdSPQURXSD63bYCqXpbqYe8EPkiFDsL4EjntqKAZouJIG/qQNvQg+R+O6JJqyseUh6OF9UmNmxeALmAP39yzoUieiuANGCYboiRc3DcgUc=";


    private Key key;

    @Autowired
    public JwtUtil() {
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJwt(token).getBody();
    }

    private boolean isExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    public void validate(final String token){
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
