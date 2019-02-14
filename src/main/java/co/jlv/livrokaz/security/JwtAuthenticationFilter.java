package co.jlv.livrokaz.security;

import static co.jlv.livrokaz.security.SecurityConstants.HEADER_STRING;
import static co.jlv.livrokaz.security.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import co.jlv.livrokaz.model.Users;
import co.jlv.livrokaz.services.CustomUserDetailsService;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    
    @Autowired
	AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

    	System.out.println("Avt ------------ JwtAuthenticationFilter --------- authenticationToken " );
        try{
        	String auth = httpServletRequest.getHeader("Authorization");
        	System.out.println("Avt auth------------ JwtAuthenticationFilter --------- authenticationToken " + auth );
        	
            String jwt = getJWTFromRequest(httpServletRequest);
            System.out.println("Avt jwt------------ JwtAuthenticationFilter --------- authenticationToken " + jwt );
            if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                long userId = tokenProvider.getUserIdFromJWT(jwt);
                
                
                Users users = customUserDetailsService.loadUserById(userId);
                System.out.println("JwtAuthenticationFilter ---------Long userId " + userId);
                
                String username = tokenProvider.getUsernameFromJWT(jwt);
                System.out.println("JwtAuthenticationFilter ---------username " + username);
                
                Object typeRole = new Object();
                
                List<?> role = tokenProvider.getAuthoritariesFroJWT(jwt);
                for (int i=0; i< role.size();i++) {
                	typeRole = role.get(i).toString();
                }
                
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                		users.getUsername(),
                		users.getPassword()
                );
                
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                
                typeRole = typeRole.toString().split("=")[1];
                typeRole = typeRole.toString().substring(0, typeRole.toString().length() - 1);
                		
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(typeRole.toString());
                List<SimpleGrantedAuthority> updatedAuthorities = new ArrayList<SimpleGrantedAuthority>();
                updatedAuthorities.add(authority);

                

                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                                SecurityContextHolder.getContext().getAuthentication().getCredentials(),
                                updatedAuthorities)
                );
    	        
                System.out.println("JwtAuthenticationFilter --------- authenticationToken " + authenticationToken.getName());
                System.out.println("JwtAuthenticationFilter --------- getCredentials " + authenticationToken.getCredentials());
                System.out.println("JwtAuthenticationFilter --------- getAuthorities " + authenticationToken.getAuthorities());
                System.out.println("JwtAuthenticationFilter --------- getPrincipal " + authenticationToken.getPrincipal());
                System.out.println("JwtAuthenticationFilter --------- getDetails " + authenticationToken.getDetails());
                System.out.println("JwtAuthenticationFilter --------- getName " + authenticationToken.getName());
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HEADER_STRING);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}