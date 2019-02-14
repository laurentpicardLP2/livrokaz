package co.jlv.livrokaz.controller;

import static co.jlv.livrokaz.security.SecurityConstants.SECRET_KEY;
import static co.jlv.livrokaz.security.SecurityConstants.TOKEN_EXPIRATION_TIME;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.jlv.livrokaz.model.AuthToken;
import co.jlv.livrokaz.model.Authorities;
import co.jlv.livrokaz.model.UserFront;
import co.jlv.livrokaz.model.Users;
import co.jlv.livrokaz.payload.JWTLoginSuccessResponse;
import co.jlv.livrokaz.payload.LoginRequest;
import co.jlv.livrokaz.repository.AuthoritiesRepository;
import co.jlv.livrokaz.repository.UsersRepository;
import co.jlv.livrokaz.security.JwtTokenProvider;
import co.jlv.livrokaz.services.IAuthenticationFacade;
import co.jlv.livrokaz.services.MapValidationErrorService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/userctrl")
@CrossOrigin("http://localhost:4200")
public class UserController {
	
	@Autowired
	AuthoritiesRepository authoritiesRepo;
	
	@Autowired
	UsersRepository usersRepo;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
    private MapValidationErrorService mapValidationErrorService;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
    private IAuthenticationFacade authenticationFacade;
	
	public static HttpSession sessionUser;
	
	public static String username;
	
	public static final String TOKEN_PREFIX = "Bearer ";
	
	
	@PostMapping("/adduser")
	public ResponseEntity<?> addUsers(@Valid String numVoieDomicile, @Valid String nomVoieDomicile, @Valid int cpDomicile,
		@Valid String cityDomicile, @Valid String countryDomicile, @Valid String numVoieLivraison,
		@Valid String nomVoieLivraison, @Valid int cpLivraison, @Valid String cityLivraison, @Valid String countryLivraison,
		@Valid int yyyy, @Valid int mm, @Valid int dd, @Valid String userName, @Valid String pwd, @Valid String typeRole,
		@Valid String civility, @Valid String firstName, @Valid String lastName, @Valid String tel) {
		Users users;
		Authorities authorities;
		
		Long id;

		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		
		
		
			
		id =  (LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())/1000;
		users = new Users(id, userName,"{bcrypt}" + bcrypt.encode(pwd) , true, 
				civility, firstName, lastName, tel, yyyy,mm, dd, 
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
	    public ResponseEntity<?> authenticateUser(@Valid @RequestBody Users users, BindingResult result){
	        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
	        
	        System.out.println("@PostMapping        : ");
	        if(errorMap != null)
	            return  errorMap;
	        Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                		users.getUsername(),
	                		users.getPassword()
	                )
	        );
	        
	        users = usersRepo.findByUsername(users.getUsername()); // pour récupérer l'id 
	        
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        
	        System.out.println("*********authentication.getAuthorities()" + authentication.getAuthorities());
	        
	        System.out.println("ùùùùùùùùùùùùùù" + authentication.getPrincipal());
	        System.out.println("lllllllllllllll" + authentication.getAuthorities());
	        
	        Date now = new Date(System.currentTimeMillis());
	        Date expireDate = new Date(now.getTime() + TOKEN_EXPIRATION_TIME);
	        Map<String, Object>claims = new HashMap<>();
	        claims.put("id", (Long.toString(users.getId())));
	        claims.put("username", users.getUsername());
	        claims.put("role",  authentication.getAuthorities());
	        String jwt =  TOKEN_PREFIX + Jwts.builder()
	                .setSubject(users.getUsername())
	                .setClaims(claims)
	                .setIssuedAt(now)
	                .setExpiration(expireDate)
	                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
	                .compact();
	    	
	        
	        
	        
	        //String token = TOKEN_PREFIX + tokenProvider.generateToken(authentication);
	        
	        System.out.println("jwt        : " + jwt);
	        return ResponseEntity.ok(new AuthToken(jwt));
	        
	        
	        
	    }
	
	
	
	
	
	
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
