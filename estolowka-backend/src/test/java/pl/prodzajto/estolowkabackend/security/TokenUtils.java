package pl.prodzajto.estolowkabackend.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Collections;
import java.util.Date;

import static pl.prodzajto.estolowkabackend.security.Constants.EXPIRATION_TIME;
import static pl.prodzajto.estolowkabackend.security.Constants.SECRET;

class TokenUtils
{
    static String generateToken()
    {
        return Jwts.builder()
                   .setIssuer("testUser@test.com")
                   .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                   .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                   .setSubject(Collections.emptyList().toString())
                   .compact();
    }
}
