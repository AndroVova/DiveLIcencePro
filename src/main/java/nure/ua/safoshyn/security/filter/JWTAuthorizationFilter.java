package nure.ua.safoshyn.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nure.ua.safoshyn.entity.Profile;
import nure.ua.safoshyn.entity.Role;
import nure.ua.safoshyn.security.SecurityConstants;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class JWTAuthorizationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith(SecurityConstants.BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.replace(SecurityConstants.BEARER, "");
        UserDetails user = getUserDetails(token);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null,  user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private UserDetails getUserDetails(String token) {
        Profile userDetails = new Profile();

        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET_KEY))
                .build()
                .verify(token);
        String subject = decodedJWT.getSubject();
        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);


        for (String aRoleName : roles) {
            userDetails.addRole(new Role(aRoleName));
        }

        userDetails.setEmail(subject);

        return userDetails;
    }
}
