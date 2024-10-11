package com.example.blogApi.config;

import com.example.blogApi.service.user.jwt.UserServiceImpl;
import com.example.blogApi.utils.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    private final UserServiceImpl userService;

    @Override
    protected void doFilterInternal( // This method is called for every request
            @NonNull HttpServletRequest request, // Request object
            @NonNull HttpServletResponse response, // Response object
            @NonNull FilterChain filterChain // FilterChain object
    ) throws ServletException, // Exception thrown by the servlet, A servlet is a Java class that is used to extend the capabilities of servers that host applications accessed by means of a request-response programming model.
            IOException  // Exception thrown by the input/output system
    {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer ")) { // checks validity of token but checking the header
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7); // Extracting the token from the header
        userEmail = jwtUtil.extractUsername(jwt); // Extracting the username from the token

        if (StringUtils.isNoneEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null) { // Checking if the user is not already authenticated
            UserDetails userDetails = userService.loadUserByUsername(userEmail); // Loading the user details which are stored in the database
            if(jwtUtil.isTokenValid(jwt)) { // Checking if the token is valid
                SecurityContext context = SecurityContextHolder.createEmptyContext(); // Creating an empty context
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); // Creating an authentication token used to authenticate the user but is not yet authenticated
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // Setting the details of the authentication token
                context.setAuthentication(usernamePasswordAuthenticationToken); // Setting the authentication token in the context
                SecurityContextHolder.setContext(context); // Setting the context in the SecurityContextHolder
            }
        }

        filterChain.doFilter(request, response); // Passing the request and response to the next filter in the chain so that the request can be processed further
    }
}
