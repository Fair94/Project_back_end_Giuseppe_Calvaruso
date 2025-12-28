package com.giuseppecalvaruso.library365.security;

import com.giuseppecalvaruso.library365.entities.User;
import com.giuseppecalvaruso.library365.exceptions.UnauthorizedException;
import com.giuseppecalvaruso.library365.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component

public class JWTAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JWTTools   jwtTools;
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                    throws IOException, ServletException {

        try {
//                                Recuperiamo Header chiamato Authorization
            String authorizationHeader = request.getHeader("Authorization");

//                                Controlliamo esistenza, se non esiste, lanciamo una eccezione personalizzata
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer "))
                throw new UnauthorizedException("Token mancante o non corretto");

            String accessToken = authorizationHeader.replace("Bearer ", "");

//                                Verifichiamo la corretteza del token

            jwtTools.verifyToken(accessToken);

            UUID user_id = jwtTools.getIDfromToken(accessToken);

            User foundUser = this.userService.getUserByIdWithRoles(user_id);

            var authorities = foundUser.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .toList();

            Authentication authentication = new UsernamePasswordAuthenticationToken(foundUser, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);


        } catch (UnauthorizedException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token problems!");
        }
    }

                            @Override
                            protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException{
                                String path = request.getServletPath();
                                return path.equals("/auth/register")|| path.equals("/auth/login");
        }




    }

