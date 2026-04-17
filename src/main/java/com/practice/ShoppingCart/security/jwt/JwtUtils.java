package com.practice.ShoppingCart.security.jwt;

import com.practice.ShoppingCart.model.Role;
import com.practice.ShoppingCart.security.User.ShopUserDetails;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {

    @Value("${auth.token.jwtSecret}")
    private String jwtSecret;

    @Value("${${auth.token.expiration}}")
    private int expirationTime;

    public String generateToken(Authentication authentication){

        ShopUserDetails userPrincipal = (ShopUserDetails) authentication.getPrincipal();

        List<String> roles = userPrincipal.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).toList();



        return Jwts.builder()
                .subject(userPrincipal.getEmail())
                .claim("id",userPrincipal.getId())
                .claim("role",roles)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime()+expirationTime))
                .signWith(key()).compact();
    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUserNameFromToken(String token){

        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload().getSubject();

    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) key())
                    .build()
                    .parseSignedClaims(token);
            return true;
        }catch (Exception e){
            throw  new JwtException("Invalid token "+ e.getMessage());
        }
    }
}
