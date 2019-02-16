package co.jlv.livrokaz.security;

import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import co.jlv.livrokaz.model.Users;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static co.jlv.livrokaz.security.SecurityConstants.TOKEN_EXPIRATION_TIME;
import static co.jlv.livrokaz.security.SecurityConstants.SECRET_KEY;

@Component
public class JwtTokenProvider {

    // Ici nous allons faire 3 choses
    //1. Generer le le token

    public String generateToken(Authentication authentication){
        Users users = (Users)authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());
        Date expireDate = new Date(now.getTime() + TOKEN_EXPIRATION_TIME);
        Map<String, Object>claims = new HashMap<>();
        claims.put("id", (Long.toString(users.getUsersId())));
        claims.put("username", users.getUsername());
        claims.put("role", users.getAuthorities());
        return Jwts.builder()
                .setSubject(users.getUsername())
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }


    //2. Valider le token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex){
            System.out.println("Signature JWT invalide !!!");
        } catch (MalformedJwtException ex) {
            System.out.println("token JWT invalide !!!");
        } catch (ExpiredJwtException ex) {
            System.out.println("Désolé, le token a expiré !!!");
        } catch (UnsupportedJwtException ex){
            System.out.println("Token JWT non supporté !!!");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty !!!");
        }
        return false;
    }

    //3. Recuperer l'id du user depuis apartir du token

    public Long getUserIdFromJWT(String token){
        Claims claims =Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        String id = (String)claims.get("id");
        return Long.parseLong(id);
    }
    
    public String getUsernameFromJWT(String token){
        Claims claims =Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return (String)claims.get("username");
        
    }
    
    public List<?> getAuthoritariesFroJWT(String token) {
    	Claims claims =Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        System.out.println("getUsernameFromJWT" + (String)claims.get("username"));
        return (List<?>)claims.get("role");
    }
    
    
}
