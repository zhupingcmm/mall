package com.mf.mall.common.util;


import com.mf.mall.common.base.ResponseEnum;
import com.mf.mall.common.config.JwtConfig;
import com.mf.mall.common.exception.BusinessException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.val;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

public class JwtUtil {
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    public static String createToken (JwtConfig jwtConfig, Key key) {

       return Jwts.builder()
                .setId(jwtConfig.getJwtId())
                .setSubject(jwtConfig.getUserDto().getName())
                .claim(jwtConfig.getAuthorities(), jwtConfig.getUserDto().getPassword())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getAccessTokenExpireTime()))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public static boolean validateToken(String token, Key signKey){

        try {
            Jwts.parserBuilder().setSigningKey(signKey).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | MalformedJwtException | SignatureException e) {
            if (e instanceof ExpiredJwtException){
                throw new BusinessException(ResponseEnum.TOKEN_EXPIRED);
            }
        }
        return false;
    }

    public static String createToken(JwtConfig jwtConfig) {
        return createToken(jwtConfig, key);
    }

    public static boolean validateToken(String token) {
        return validateToken(token, key);
    }
    public static Optional<Claims> parseClaims(String token) {
        return parseClaims(token, key);
    }

    public static Optional<Claims> parseClaims(String token, Key key){
        try {
            val claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            return Optional.of(claims);
        } catch (Exception e) {
            return Optional.empty();
        }
    }


}
