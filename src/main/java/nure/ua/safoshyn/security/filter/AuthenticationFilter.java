package nure.ua.safoshyn.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import nure.ua.safoshyn.entity.Profile;
import nure.ua.safoshyn.entity.Role;
import nure.ua.safoshyn.repository.ProfileRepository;
import nure.ua.safoshyn.security.SecurityConstants;
import nure.ua.safoshyn.security.manager.CustomAuthenticationManager;
import nure.ua.safoshyn.service.ProfileServiceImpl;
import org.hibernate.Hibernate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private CustomAuthenticationManager authenticationManager;
    private ProfileRepository profileRepository;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            Profile profile = new ObjectMapper().readValue(request.getInputStream(), Profile.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(profile.getEmail(), profile.getPassword());
            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(failed.getMessage());
        response.getWriter().flush();
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        Profile profile = ProfileServiceImpl.unwrapProfile(profileRepository.findByEmail(authResult.getName()), 404L);
        Hibernate.initialize(profile.getRoles());
        Set<Role> roles = profile.getRoles();
        List<String> roleNames = roles.stream()
                .map(Role::getName)
                .toList();

        String token = JWT.create()
                .withSubject(authResult.getName())
                .withArrayClaim("roles", roleNames.toArray(new String[0]))
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_EXPIRATION))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET_KEY));
        response.addHeader(SecurityConstants.AUTHORIZATION, SecurityConstants.BEARER + token);
    }


}
