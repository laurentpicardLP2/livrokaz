package co.jlv.livrokaz.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import co.jlv.livrokaz.model.Author;
import co.jlv.livrokaz.model.Authorities;
import co.jlv.livrokaz.model.Gendle;
import co.jlv.livrokaz.model.UserFront;
import co.jlv.livrokaz.model.Users;
import co.jlv.livrokaz.repository.AuthoritiesRepository;
import co.jlv.livrokaz.repository.UsersRepository;
import co.jlv.livrokaz.services.IAuthenticationFacade;

import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@RestController
@RequestMapping("/userctrl")
@CrossOrigin("http://localhost:4200")
public class UserController {
	
	public static final String SIGN_UP_URLS = "/api/user/**";
    public static final String SECRET_KEY = "CleSecretePourGenererJWTs";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final long TOKEN_EXPIRATION_TIME = 30_000; //30 secondes
	
	@Autowired
	AuthoritiesRepository authoritiesRepo;
	
	@Autowired
	UsersRepository usersRepo;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	public static HttpSession sessionUser;
	
	public static String username;
	
	@Autowired
    private IAuthenticationFacade authenticationFacade;
	
	@PostMapping("/adduser")
	public ResponseEntity<?> addUsers(@Valid String numVoieDomicile, @Valid String nomVoieDomicile, @Valid int cpDomicile,
		@Valid String cityDomicile, @Valid String countryDomicile, @Valid String numVoieLivraison,
		@Valid String nomVoieLivraison, @Valid int cpLivraison, @Valid String cityLivraison, @Valid String countryLivraison,
		@Valid int yyyy, @Valid int mm, @Valid int dd, @Valid String userName, @Valid String pwd, @Valid String typeRole,
		@Valid String civility, @Valid String firstName, @Valid String lastName, @Valid String tel) {
		Users users;
		Authorities authorities;
		Date dateBirthday;

		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		
		
		
		dateBirthday = Date.from((LocalDate.of(yyyy, mm, dd).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		users = new Users(userName,"{bcrypt}" + bcrypt.encode(pwd) , true, 
				civility, firstName, lastName, tel, dateBirthday, 
				numVoieDomicile, nomVoieDomicile, cpDomicile, cityDomicile, countryDomicile,
				numVoieLivraison, nomVoieLivraison, cpLivraison, cityLivraison, countryLivraison
				);
		
	
		authorities = new Authorities(typeRole);
		authorities.setUsers(users);
		authoritiesRepo.save(authorities);
		try {
			//authoritiesRepo.save(authorities);
			return ResponseEntity.status(HttpStatus.OK).body(null);
			
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			
		}	
				
	}
	
	
	@GetMapping("/test/{name}")
    public void uid(@PathVariable String name) {
		
		System.out.println(name);
		
    }
	
	
	@PostMapping("/login")
	public ResponseEntity<?> loginFront(@RequestBody UserFront userFront) {
		
        //does the authentication
		/*if(userFront.getUsername().contains(",")) {
			userFront.setUsername(userFront.getUsername().split(",")[0]);
		}
		if(userFront.getPassword().contains(",")) {
			userFront.setPassword(userFront.getPassword().split(",")[0]);
		}*/
		
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                		userFront.getUsername(),
                		userFront.getPassword()
                )
        );
		
		
  
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
       String token = generateToken(authentication, userFront, authentication.getAuthorities().toString());
       String test = loginSecure(userFront.getUsername(),
       		userFront.getPassword());
				
		try {
			System.out.println("ANGULAR         authentication.getAuthorities() " + authentication.getAuthorities() + " token : " + token);
			return ResponseEntity.ok(userFront) ;
		} catch (Exception e) {
			return null;
		}			
	}
	
	
	public String generateToken(Authentication authentication,UserFront userFront, String role){
       
        Date now = new Date(System.currentTimeMillis());
        Date expireDate = new Date(now.getTime() + TOKEN_EXPIRATION_TIME);
        Map<String, Object>claims = new HashMap<>();
        claims.put("username", userFront.getUsername());
        claims.put("role", role);
        return Jwts.builder()
                .setSubject( userFront.getUsername())
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
	
	
	
	
	
	
	/*@Override
	public String createTokenForUser(User user) {
	    final ZonedDateTime afterOneWeek = ZonedDateTime.now().plusWeeks(1);
	    return Jwts.builder()
	                .setSubject(user.getId().toString()) // expiration + session
	                .signWith(SignatureAlgorithm.HS512, secret)
	                .setExpiration(Date.from(afterOneWeek.toInstant()))
	                .compact();
	}*/

	
	
	
	
	
	@PostMapping("/loginsecure")
    public String loginSecure(@Valid String username, @Valid  String password) {
		System.out.println("username " + username + " password " + password);
        //does the authentication
		if(username.contains(",")) {
			username = username.split(",")[0];
		}
		if(password.contains(",")) {
			password = password.split(",")[0];
		}
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );
  
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        System.out.println("*********authentication.getAuthorities()" + authentication.getAuthorities());
        
        System.out.println(authentication.isAuthenticated());
        
        System.out.println(authentication.toString());
        
       
        /*if (sessionUser == null) {
			System.out.println("sessionUser == null");
			sessionUser = session;
			Authentication authenticationGetUsername = authenticationFacade.getAuthentication();
			
			username = authenticationGetUsername.getName();
		} 
        System.out.println("username - autentication : " + username);
		System.out.println("+++++++++++++++++ session.getId() " + session.getId());*/
        return "index";
    }
	

	
	
	@GetMapping("/login")
	public ResponseEntity<?> login(HttpSession session,  HttpServletResponse httpResponse) {
		System.out.println("sessionUser : " + sessionUser);
		if (sessionUser == null) {
			System.out.println("sessionUser == null");
			sessionUser = session;
			Authentication authentication = authenticationFacade.getAuthentication();
			
			username = authentication.getName();
		} 
		
		System.out.println("Integer.MAX_VALUE : " + Integer.MAX_VALUE);
		
		 
		    
		        
		        System.out.println("username - autentication : " + username);
		
		System.out.println("+++++++++++++++++ session.getId() " + session.getId());
		
		sessionUser = session;
		sessionUser.setAttribute("livre",new Integer(1));
		
		Integer i = (Integer)sessionUser.getAttribute("livre");
		System.out.println(" --------- i : " + i.intValue());
		//sessionUser.setMaxInactiveInterval(1);

		//sessionUser.invalidate();
		
        
		try {
			//httpResponse.sendRedirect("/livrokaz/username");
			//httpResponse.sendRedirect("/");
			return ResponseEntity.status(HttpStatus.OK).body(null);
			
			//return ResponseEntity.status(HttpStatus.OK).body("<html> Session</html>");
			
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			
		}
		
				
	}
	
	

	
}
