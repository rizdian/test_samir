package com.riz.test_samir.util;

import com.riz.test_samir.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtUtil {
    private static final String SECRET = "TmV3U2VjcmV0S2V5Rm9ySldUU2lnbmluZ1B1cnBvc2VzMTIzNDU2Nzg=\r\n" + "";

    public static User parseToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET.getBytes())
                .parseClaimsJws(token)
                .getBody();

        Long userId = claims.get("id", Long.class);
        String username = claims.getSubject();
        User user= new User();
        user.setId(userId);
        user.setUsername(username);
        return user;
    }
}
