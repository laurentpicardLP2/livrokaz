package co.jlv.livrokaz;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

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
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	//antMatchers("/", "/home").permitAll()
        http
        	.csrf().disable()
            .authorizeRequests()
                .antMatchers("/home").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .permitAll();
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
	
	
	
//
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
//		@Override
//		protected void configure(HttpSecurity http) throws Exception {
//	        http.authorizeRequests()
//	    	.antMatchers("/").permitAll()
//	    	.antMatchers("/public").permitAll()
//	    	.antMatchers("/deny").denyAll()
//	    	.antMatchers("/developper").hasAnyAuthority("DEVELOPPER", "ADMIN")
//	    	.antMatchers("/manager").hasAnyAuthority("MANAGER", "ADMIN")
//	    	.antMatchers("/admin").hasAnyAuthority("ADMIN")
//	    	.antMatchers("/error").hasAnyAuthority("ADMIN")
//	    	.anyRequest().authenticated()
//	    	.and()
//	    	.formLogin().permitAll()
//	    	.and()
//		    .exceptionHandling().accessDeniedPage("/error");
//		}
