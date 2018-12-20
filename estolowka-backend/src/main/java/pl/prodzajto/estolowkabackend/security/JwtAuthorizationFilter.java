package pl.prodzajto.estolowkabackend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Stream;

import static pl.prodzajto.estolowkabackend.security.Constants.HEADER_STRING;
import static pl.prodzajto.estolowkabackend.security.Constants.SECRET;
import static pl.prodzajto.estolowkabackend.security.Constants.TOKEN_PREFIX;


class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String authorizationString = Stream.of((request.getQueryString() == null ? "" : request.getQueryString()).split("&"))
                .filter(query -> query.startsWith("authorization="))
                .map(query -> query.replace("authorization=", ""))
                .map(query -> query.replace("%20", " "))
                .findFirst().orElseGet(() -> request.getHeader(HEADER_STRING));

        if (authorizationString == null || !authorizationString.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(authorizationString);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String authorizationString) {
        if (authorizationString == null) {
            return null;
        }
        Claims body = Jwts.parser()
                .setSigningKey(SECRET.getBytes())
                .parseClaimsJws(authorizationString.replace(TOKEN_PREFIX, ""))
                .getBody();

        String username = body.getIssuer();

        return new UsernamePasswordAuthenticationToken(username, null, null);

    }
}
