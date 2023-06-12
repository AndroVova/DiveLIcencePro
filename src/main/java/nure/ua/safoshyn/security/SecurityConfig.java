package nure.ua.safoshyn.security;

import lombok.AllArgsConstructor;
import nure.ua.safoshyn.repository.ProfileRepository;
import nure.ua.safoshyn.security.filter.ExceptionHandlerFilter;
import nure.ua.safoshyn.security.filter.AuthenticationFilter;
import nure.ua.safoshyn.security.filter.JWTAuthorizationFilter;
import nure.ua.safoshyn.security.manager.CustomAuthenticationManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@AllArgsConstructor
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    private final CustomAuthenticationManager customAuthenticationManager;
    private ProfileRepository profileRepository;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(customAuthenticationManager, profileRepository);
        authenticationFilter.setFilterProcessesUrl("/authenticate");
        http
                .cors().and()
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, SecurityConstants.REGISTER_PATH).permitAll()
                .requestMatchers("/profile/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/lesson/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/sensor/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/lesson_testing/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/custom_user/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/dive_club/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/certificate/admin/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class)
                .addFilter(authenticationFilter)
                .addFilterAfter(new JWTAuthorizationFilter(), AuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

//TODO: векторы атаки на jwt token
}
