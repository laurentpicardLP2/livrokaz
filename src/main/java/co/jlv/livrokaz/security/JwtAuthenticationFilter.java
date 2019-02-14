package co.jlv.livrokaz.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import co.jlv.livrokaz.model.Users;
import co.jlv.livrokaz.services.CustomUserDetailsService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static co.jlv.livrokaz.security.SecurityConstants.HEADER_STRING;
import static co.jlv.livrokaz.security.SecurityConstants.TOKEN_PREFIX;

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
                	System.out.println("JwtAuthenticationFilter ---------role " + role.get(i));
                	typeRole = role.get(i);
                }
               /* Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if (principal instanceof UserDetails) {
                    ((UserDetails) principal).getAuthorities().add(New GrantedAuthorityImpl("ROLE_FOO"));
                }*/
                
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                		users.getUsername(),
                		users.getPassword()
                );
                
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                
                
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN");
                List<SimpleGrantedAuthority> updatedAuthorities = new ArrayList<SimpleGrantedAuthority>();
                updatedAuthorities.add(authority);

                

                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                                SecurityContextHolder.getContext().getAuthentication().getCredentials(),
                                updatedAuthorities)
                );
    	        
                
//                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//                		users, null, Collections.emptyList()
//                );
                
                
                
                //authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                //SecurityContextHolder.getContext().setAuthentication(authenticationToken);
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