package co.jlv.livrokaz;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private Environment env;
	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		System.out.println("in");
//        http.authorizeRequests()
//    	.antMatchers("/").permitAll()
//    	.antMatchers("/public").permitAll()
//    	.antMatchers("/deny").denyAll()
//    	.antMatchers("/developper").hasAnyAuthority("DEVELOPPER", "ADMIN")
//    	.antMatchers("/manager").hasAnyAuthority("MANAGER", "ADMIN")
//    	.antMatchers("/admin").hasAnyAuthority("ADMIN")
//    	.antMatchers("/error").hasAnyAuthority("ADMIN")
//    	.anyRequest().authenticated()
//    	.and()
//    	.formLogin().loginPage("/login").permitAll()
//    	.and()
//    	.logout()
//    	.permitAll();
//
//	}
	
//	@Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/register");
//    }
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	.csrf().disable()
            .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/web/**").hasAnyRole("ADMIN", "MANAGER")
                .antMatchers("/web/gestionbooks").hasAnyRole("ADMIN", "MANAGER")
                .antMatchers("/livrokaz/**").hasAnyRole("ADMIN", "USER")
                //.anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login") //.successHandler(successHandler)
                .permitAll()
                //.and()
                //.antMatcher("/register").anonymous()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
                .and()
                .sessionManagement().maximumSessions(1).expiredUrl("/login");
        
	       http
	        .sessionManagement()
	        .maximumSessions(1)
	        .expiredUrl("/login?expired")
	        .maxSessionsPreventsLogin(true)
	        .and()
	        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
	        .invalidSessionUrl("/logout");
    }

    
	@Bean
	public DataSource dataSource() {
		    final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		    dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
		    dataSource.setUrl(env.getProperty("spring.datasource.url"));
		    dataSource.setUsername(env.getProperty("spring.datasource.username"));
		    dataSource.setPassword(env.getProperty("spring.dasource.password"));
		    return dataSource;
		}

		@Autowired
		public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		    auth.jdbcAuthentication().dataSource(dataSource());
		}
	
}
    
//    @Autowired
//    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication()
//			.withUser("user").password("{noop}simplon").roles("USER")
//			.and()
//			.withUser("developper").password("{noop}simplon").roles("DEVELOPPER")
//			.and()
//			.withUser("manager").password("{noop}simplon").roles("MANAGER")
//			.and()
//			.withUser("admin").password("{bcrypt}$2a$10$OhwFVfhBW0Rv2TUtS4UFSOtvMFbGnPPEFkFcKnXif9bBAfWFnKm16").roles("ADMIN");
//    }
   
    
	
	
	
	
//.antMatchers("/web/admin/**").hasAnyRole(ADMIN.toString(), GUEST.toString())
//.anyRequest().permitAll()
//.and()
//.formLogin().loginPage("/web/login").permitAll()
//.and()
//.csrf().ignoringAntMatchers("/contact-email")
//.and()
//.logout().logoutUrl("/web/logout").logoutSuccessUrl("/web/").permitAll();



	

//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//             User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
//		@Autowired
//	    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
//			auth.inMemoryAuthentication()
//				.withUser("user").password("{noop}simplon").roles("USER")
//				.and()
//				.withUser("developper").password("{noop}simplon").roles("DEVELOPPER")
//				.and()
//				.withUser("manager").password("{noop}simplon").roles("MANAGER")
//				.and()
//				.withUser("admin").password("{bcrypt}$2a$10$OhwFVfhBW0Rv2TUtS4UFSOtvMFbGnPPEFkFcKnXif9bBAfWFnKm16").roles("ADMIN");
//	    }
	
//	@Bean
//	public DataSource dataSource() {
//		    final DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		    dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
//		    dataSource.setUrl(env.getProperty("spring.datasource.url"));
//		    dataSource.setUsername(env.getProperty("spring.datasource.username"));
//		    dataSource.setPassword(env.getProperty("spring.dasource.password"));
//		    return dataSource;
//		}
//
//		@Autowired
//		public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
//		    auth.jdbcAuthentication().dataSource(dataSource());
//		}
//
//		@Override
//		protected void configure(HttpSecurity http) throws Exception {
//			http
//        	.csrf().disable()
//            .authorizeRequests()
//	    	.antMatchers("/").permitAll()
//	    	.antMatchers("/web/**").hasAnyRole("ADMIN", "MANAGER")
//	    	.anyRequest().authenticated()
//	    	.and()
//          .formLogin()
//              .loginPage("/login").permitAll()
//	    	.and()
//		    .exceptionHandling().accessDeniedPage("/error");
//		}
//}