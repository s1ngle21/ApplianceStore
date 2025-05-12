package org.example.appliancestore.token;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtSecurityFilter extends OncePerRequestFilter {

    private JwtUtils jwtTokenUtils;
    private UserDetailsService userDetailsService;
    private static final Logger LOGGER = Logger.getLogger(JwtSecurityFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = null;
        String username = null;

        String requestURI = request.getRequestURI();
        boolean isLoginRequest = requestURI.startsWith("/login") || requestURI.startsWith("/register");

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("authToken".equals(cookie.getName())) {
                    jwt = cookie.getValue();
                    break;
                }
            }
        }

        try {
            if (jwt != null) {
                username = jwtTokenUtils.getUserName(jwt);
            }
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(token);
            }
        } catch (ExpiredJwtException e) {
            if (!isLoginRequest) {
                LOGGER.warn("Expired token for user [" + e.getClaims().getSubject() + "]. Redirecting to login page.");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.sendRedirect("/login?error=Your token has expired");
                return;
            }
        } catch (SignatureException e) {
            if (!isLoginRequest) {
                LOGGER.warn("Invalid token for request to [" + request.getRequestURI() + "]. Redirecting to login page.");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.sendRedirect("/login?error=Your token is invalid");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

}
