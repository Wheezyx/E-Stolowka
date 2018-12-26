package pl.prodzajto.estolowkabackend.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import static pl.prodzajto.estolowkabackend.security.Constants.EXPIRATION_TIME;
import static pl.prodzajto.estolowkabackend.security.Constants.SECRET;
import static pl.prodzajto.estolowkabackend.security.Constants.TOKEN_PREFIX;

/**
 * Controller created just for testing purpose to generate access token.
 */

@RestController
class TokenController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/token")
    public String generateToken(@RequestBody Credential credential) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credential.getUsername(),
                        credential.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return TOKEN_PREFIX + " " + Jwts.builder()
                .setIssuer(authentication.getName())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                .compact();

    }
}
