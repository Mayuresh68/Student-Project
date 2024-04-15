package com.mayuresh.student.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private HandlerExceptionResolver exceptionResolver;  /*For_Token_Exception*/

    // Constuctor
    public JwtAuthFilter(HandlerExceptionResolver exceptionResolver) {  /*For_Token_Exception*/
        this.exceptionResolver = exceptionResolver;
    }

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserInfoUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("doFilterInternal.................");
        String authHeader = request.getHeader("Authorization");

        String token = null;
        String username = null;
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                System.out.println("TOKEN : "+ token);
                username = jwtService.extractUsername(token);
                System.out.println("USERNAME : "+ username);
//				try {
//					username = jwtService.extractUsername(token);
//				} catch (ExpiredJwtException e) {
//					System.out.println("Jwt token has expired : " + e.getMessage());
//					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//					response.getWriter().write("Token expired: " + e.getMessage());
//					return;
//				}
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                System.out.println("USERDETAILS : "+ userDetails);
                if (jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                            null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException | SignatureException ex) { /*For_Token_Exception*/
            exceptionResolver.resolveException(request, response, null, ex);
        }catch (Exception ex) {
            System.out.println("<><><><><><><><><><"+ex.getMessage());
//            exceptionResolver.resolveException(request, response, null, ex);
        }
    }
}
