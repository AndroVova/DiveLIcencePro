package nure.ua.safoshyn.security.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nure.ua.safoshyn.exception.EntityNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (EntityNotFoundException e) {
            getResponse(response, HttpServletResponse.SC_NOT_FOUND, "Username doesn't exist");
        } catch (JWTVerificationException e) {
            getResponse(response, HttpServletResponse.SC_FORBIDDEN, "JWT NOT VALID");
        } catch (RuntimeException e) {
            getResponse(response, HttpServletResponse.SC_BAD_REQUEST, "BAD REQUEST");
        }
    }

    private static void getResponse(HttpServletResponse response, int code, String message) throws IOException {
        response.setStatus(code);
        response.getWriter().write(message);
        response.getWriter().flush();
    }
}