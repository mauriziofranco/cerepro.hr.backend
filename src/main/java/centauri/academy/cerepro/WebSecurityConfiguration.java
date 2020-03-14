/**
 * 
 */
package centauri.academy.cerepro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import centauri.academy.cerepro.service.LoginService;

/**
 * @author maurizio
 *
 */
//@Configuration
//@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	public static final Logger logger = LoggerFactory.getLogger(WebSecurityConfiguration.class);
	@Autowired
	private LoginService loginService;
	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		logger.info("SpringSecurityConfiguration_Database.configureGlobal - START");
//		auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance()).withUser("user")
//				.password("password").roles("USER");
//		auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance()).withUser("admin")
//				.password("password").roles("USER", "ADMIN");
		auth.userDetailsService(loginService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		logger.info("SpringSecurityConfiguration_Database.configure - START");
		http
        .httpBasic()
//        http.httpBasic().and().authorizeRequests()
//        .antMatchers(HttpMethod.GET, "/api/user/").hasRole("USER")
//		.antMatchers(HttpMethod.GET, "/api/user/**").hasRole("USER")
////		.antMatchers(HttpMethod.POST, "/api/user/").hasRole("USER")
//		.antMatchers(HttpMethod.PUT, "/api/user/**").hasRole("USER")
//		.antMatchers(HttpMethod.DELETE, "/api/user/**").hasRole("ADMIN")
//		.antMatchers(HttpMethod.GET, "/template/home.html").permitAll()
//		.antMatchers(HttpMethod.GET, "/template/login.html").permitAll()
//		.antMatchers(HttpMethod.GET, "/").permitAll()
//		.antMatchers(HttpMethod.POST, "/api/user/").permitAll()
                .realmName("Proxima Skillview")
        .and().authorizeRequests()
//        .antMatchers("/template/login.html","/").permitAll()
//        .antMatchers("/template/listuser.html").hasAuthority("ADMIN")
//        .antMatchers("/template/listuser.html").hasAuthority("USER")
//        .antMatchers("/api/v1/**").authenticated()
        .antMatchers(HttpMethod.GET, "/api/v1/user/").authenticated()
		.antMatchers(HttpMethod.POST, "/api/v1/user/").authenticated()
//                .anyRequest().authenticated()
                .and().csrf().disable();
//                .and().csrf().disable();
//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());


	}

}
















