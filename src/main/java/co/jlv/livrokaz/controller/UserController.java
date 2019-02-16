package co.jlv.livrokaz.controller;

import static co.jlv.livrokaz.security.SecurityConstants.SECRET_KEY;
import static co.jlv.livrokaz.security.SecurityConstants.TOKEN_EXPIRATION_TIME;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import co.jlv.livrokaz.model.Author;
import co.jlv.livrokaz.model.Authorities;
import co.jlv.livrokaz.model.Gendle;
import co.jlv.livrokaz.model.UserFront;
import co.jlv.livrokaz.model.Users;
import co.jlv.livrokaz.payload.JWTLoginSuccessResponse;
import co.jlv.livrokaz.payload.LoginRequest;
import co.jlv.livrokaz.repository.AuthoritiesRepository;
import co.jlv.livrokaz.repository.UsersRepository;
import co.jlv.livrokaz.security.JwtTokenProvider;
import co.jlv.livrokaz.services.AuthorService;
import co.jlv.livrokaz.services.AuthoritiesService;
import co.jlv.livrokaz.services.GestionCategories;
import co.jlv.livrokaz.services.IAuthenticationFacade;
import co.jlv.livrokaz.services.MapValidationErrorService;
import co.jlv.livrokaz.services.UsersService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/userctrl")
@CrossOrigin("http://localhost:4200")
public class UserController {
	
	@Autowired
	AuthoritiesRepository authoritiesRepo;
	
	private AuthoritiesService authoritiesService;
	
	@Autowired
	UsersRepository usersRepo;
	
	private UsersService usersService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
    private MapValidationErrorService mapValidationErrorService;
	
	
	public static HttpSession sessionUser;
	
	public static String username;
	
	public static final String TOKEN_PREFIX = "Bearer ";
	
	public UserController(AuthoritiesService authoritiesService, UsersService usersService) {
		this.authoritiesService = authoritiesService;
		this.usersService = usersService;
	}
	
	//via Postman
	@PostMapping("/adduser")
	public ResponseEntity<?> addUsers(@Valid String numVoieDomicile, @Valid String nomVoieDomicile, @Valid int cpDomicile,
		@Valid String cityDomicile, @Valid String countryDomicile, @Valid String numVoieLivraison,
		@Valid String nomVoieLivraison, @Valid int cpLivraison, @Valid String cityLivraison, @Valid String countryLivraison,
		@Valid int yyyy, @Valid int mm, @Valid int dd, @Valid String userName, @Valid String pwd, @Valid String typeRole,
		@Valid String civility, @Valid String firstName, @Valid String lastName, @Valid String tel, @Valid String mail) {
		Users users;
		Authorities authorities;
		
		Long userId;

		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		
		userId =  (LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())/1000;
		users = new Users(userId, userName,"{bcrypt}" + bcrypt.encode(pwd) , true, 
				civility, firstName, lastName, tel, yyyy,mm, dd, 
				numVoieDomicile, nomVoieDomicile, cpDomicile, cityDomicile, countryDomicile,
				numVoieLivraison, nomVoieLivraison, cpLivraison, cityLivraison, 
				countryLivraison, mail
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
	
	//via l'api node.js
		@PostMapping("/newauthority/{authority}")
		public ResponseEntity<?> createAuthority(@RequestBody Authorities newAuthority, @PathVariable String authority) {
			System.out.println("authority : " + authority);
				try {
					return ResponseEntity.ok(null) ;
				} catch (Exception e) {
					return ResponseEntity.badRequest().build();
				}
		}
	
		//via l'api node.js
		@PostMapping("/newuser/{authority}")
		public ResponseEntity<?> createUser(@RequestBody Users newUsers, @PathVariable String authority) {
			System.out.println("@PostMapping : " + authority);
			Users users;
			Authorities authorities;
			
			Long userId;

			BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
			
			userId =  (LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())/1000;
			users = new Users(userId, newUsers.getUsername(),"{bcrypt}" + bcrypt.encode(newUsers.getPassword()) , true, 
					newUsers.getCivility(), newUsers.getFirstName(), newUsers.getLastName(), newUsers.getTel(), newUsers.getYyyy(),newUsers.getMm(), newUsers.getDd(), 
					newUsers.getNomVoieDomicile(), newUsers.getNomVoieDomicile(), newUsers.getCpDomicile(), newUsers.getCityDomicile(), newUsers.getCountryDomicile(),
					newUsers.getNumVoieLivraison(), newUsers.getNomVoieLivraison(), newUsers.getCpLivraison(), newUsers.getCityLivraison(), 
					newUsers.getCountryLivraison(), newUsers.getMail()
					);
			authorities = new Authorities(authority);
			authorities.setUsers(users);
			authoritiesRepo.save(authorities);
				try {
					return ResponseEntity.status(HttpStatus.OK).body(newUsers);
				} catch (Exception e) {
					return ResponseEntity.badRequest().build();
				}
		}
	
	@PostMapping("/login")
	    public ResponseEntity<?> authenticateUser(@Valid @RequestBody Users users, BindingResult result){
	        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
	        
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
	        
	        Date now = new Date(System.currentTimeMillis());
	        Date expireDate = new Date(now.getTime() + TOKEN_EXPIRATION_TIME);
	        Map<String, Object>claims = new HashMap<>();
	        claims.put("id", (Long.toString(users.getUsersId())));
	        claims.put("username", users.getUsername());
	        claims.put("role",  authentication.getAuthorities());
	        String jwt =  TOKEN_PREFIX + Jwts.builder()
	                .setSubject(users.getUsername())
	                .setClaims(claims)
	                .setIssuedAt(now)
	                .setExpiration(expireDate)
	                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
	                .compact();

	        return ResponseEntity.ok(new AuthToken(jwt));
	}
	
	@GetMapping("/users")
	public List<Users> getAllCustomers() {
		return this.usersService.getAllUsers();
	}
	
	@GetMapping("/authorities")
	public List<Authorities> getAllAuthorities() {
		return this.authoritiesService.getAllAuthorities();
	}

	
}
