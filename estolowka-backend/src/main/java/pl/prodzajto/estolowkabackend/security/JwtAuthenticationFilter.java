package pl.prodzajto.estolowkabackend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

import static pl.prodzajto.estolowkabackend.security.Constants.EXPIRATION_TIME;
import static pl.prodzajto.estolowkabackend.security.Constants.HEADER_STRING;
import static pl.prodzajto.estolowkabackend.security.Constants.SECRET;
import static pl.prodzajto.estolowkabackend.security.Constants.TOKEN_PREFIX;

@AllArgsConstructor
class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
    
    private AuthenticationManager authenticationManager;
    private ObjectMapper mapper;
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
    {
        
        try
        {
            Credential credential = mapper.readValue(request.getInputStream(), Credential.class);
            
            return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    credential.getEmail(),
                    credential.getPassword()
                ));
        }
        catch(IOException e)
        {
            return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    null,
                    null
                ));
        }
    }
    
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
    {
        String token = Jwts.builder()
                           .setIssuer(authResult.getName())
                           .setSubject(mapAuthorities(authResult))
                           .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                           .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                           .compact();
        
        response.addHeader("access-control-expose-headers", HEADER_STRING);
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }
    
    private String mapAuthorities(Authentication auth)
    {
        Gson gson = new Gson();
        return gson.toJson(auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
    }
}
