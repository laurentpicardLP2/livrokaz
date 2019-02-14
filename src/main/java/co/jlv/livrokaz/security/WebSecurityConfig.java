package co.jlv.livrokaz.security;

import static co.jlv.livrokaz.security.SecurityConstants.SIGN_UP_URLS;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import co.jlv.livrokaz.services.CustomUserDetailsService;
import javax.sql.DataSource;
import org.springframework.core.env.Environment;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private Environment env;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

//    @Autowired(required=true)
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() { return new JwtAuthenticationFilter(); }
    
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
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
		
//		 @Override
//		    @Bean(BeanIds.AUTHENTICATION_MANAGER)
//		    protected AuthenticationManager authenticationManager() throws Exception {
//		        return super.authenticationManager();
//		    }
//		    
	

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
//    }

   
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        PasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        return bCryptPasswordEncoder;
//    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers().frameOptions().sameOrigin() //Pour activer la base de donnees MySQL
                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/userctrl/**").permitAll()
                //.antMatchers("/userctrl/test").permitAll()
                .antMatchers("/orderctrl/**").hasAnyRole("ADMIN", "DEVELOPPER", "MANAGER", "ANONYMOUS")
                .antMatchers("/web/**").hasAnyRole("ADMIN")
                //.antMatchers("/web/gestionbooks").hasAnyRole("ADMIN", "MANAGER")
                .antMatchers("/livrokaz/**").hasAnyRole("ADMIN", "DEVELOPPER", "MANAGER", "ANONYMOUS")
                
                
                .antMatchers(SIGN_UP_URLS).permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}