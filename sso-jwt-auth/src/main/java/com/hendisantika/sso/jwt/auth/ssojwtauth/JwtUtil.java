package com.hendisantika.sso.jwt.auth.ssojwtauth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Project : sso-jwt
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 07/10/17
 * Time: 22.55
 * To change this template use File | Settings | File Templates.
 */
public class JwtUtil {
    private JwtUtil() {
    }

    private static SecretKey key(String signingKey) {
        // HS256 requires a key of at least 256 bits (32 bytes)
        return Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));
    }

    public static String generateToken(String signingKey, String subject) {
        return Jwts.builder()
                .subject(subject)
                .issuedAt(new Date())
                .signWith(key(signingKey), Jwts.SIG.HS256)
                .compact();
    }

    public static String getSubject(HttpServletRequest httpServletRequest, String jwtTokenCookieName, String signingKey) {
        String token = CookieUtil.getValue(httpServletRequest, jwtTokenCookieName);
        if (token == null) return null;
        return Jwts.parser()
                .verifyWith(key(signingKey))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
