package centauri.academy.cerepro;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import centauri.academy.cerepro.service.LoginService;

@Configuration
@EnableWebSecurity
public class SecurityConfig3 extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoginService loginService;
	
	private static final Logger logger = LoggerFactory.getLogger(SecurityConfig3.class);

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.httpBasic().and().authorizeRequests()
		
//		.antMatchers(HttpMethod.GET, "/api/v1/user/").permitAll()
//		.antMatchers("/api/v1/**").authenticated()
		.antMatchers(HttpMethod.POST, "/api/v1/user/").permitAll()//to allow regitration????
		.antMatchers(HttpMethod.GET, "/api/v1/**/**").authenticated()
//		.antMatchers(HttpMethod.GET, "/api/v1/**/**").permitAll()
		.antMatchers(HttpMethod.POST, "/api/v1/**/**").authenticated()
//		.antMatchers(HttpMethod.POST, "/api/v1/**/**").permitAll()
		.antMatchers(HttpMethod.PUT, "/api/v1/**/**").authenticated()
//		.antMatchers(HttpMethod.PUT, "/api/v1/**/**").permitAll()
		.antMatchers(HttpMethod.DELETE, "/api/v1/**/**").authenticated()
//		.antMatchers(HttpMethod.DELETE, "/api/v1/**/**").permitAll()
//		.antMatchers(HttpMethod.DELETE, "/api/v1/**/**/**/**/**").hasAuthority("ADMIN")
//		.antMatchers("/api/v1/**", "/api/v1/**/**", "/a/index.html").authenticated()
//		.antMatchers("/a/index.html").authenticated()
		.and().csrf().disable().cors();
	}
	
	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		logger.info("SpringSecurityConfiguration_Database.configureGlobal - START");
//		auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance()).withUser("1@2.3")
//				.password("a").roles("USER", "ADMIN");
//		auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance()).withUser("francesca")
//		.password("password").roles("USER");
		auth.userDetailsService(loginService).passwordEncoder(new BCryptPasswordEncoder());
	
	}
	
	
	@Bean
	  CorsConfigurationSource corsConfigurationSource() {
	      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	      CorsConfiguration config = new CorsConfiguration();
			config.setAllowedOrigins(Arrays.asList("*"));
			config.setAllowedMethods(Arrays.asList("*"));
			config.setAllowedHeaders(Arrays.asList("*"));
			config.setAllowCredentials(true);
	      config.applyPermitDefaultValues();
	      
	      source.registerCorsConfiguration("/api/v1/**", config);
	      source.registerCorsConfiguration("/**", config);
	      source.registerCorsConfiguration("/user", config);
	      return source;
	}	
	
}






















