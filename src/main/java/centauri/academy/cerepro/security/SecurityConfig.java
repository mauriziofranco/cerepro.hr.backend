package centauri.academy.cerepro.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import centauri.academy.cerepro.security.jwt.JWTFilter;
import centauri.academy.cerepro.security.jwt.TokenProvider;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class SecurityConfig {

	private final TokenProvider tokenProvider;

	public SecurityConfig(TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	@Bean
	AuthenticationManager authenticationManager() {
		return authentication -> {
			throw new AuthenticationServiceException("Cannot authenticate " + authentication);
		};
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(CsrfConfigurer::disable).cors(Customizer.withDefaults())
				.sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(customizer -> {
					customizer.requestMatchers(HttpMethod.POST, "/api/v1/user/").permitAll()
					.requestMatchers(HttpMethod.GET, "/api/v1/survey/getSurveyForCandidate/**").permitAll()
					.requestMatchers(HttpMethod.POST, "/api/v1/surveyreplyrequest/start/").permitAll()
					.requestMatchers(HttpMethod.PUT, "/api/v1/surveyreplyrequest/end/**").permitAll()
					.requestMatchers(HttpMethod.GET, "/api/v1/application/info/**").permitAll()
					.requestMatchers(HttpMethod.GET, "/api/v1/**/**").authenticated()
					.requestMatchers(HttpMethod.POST, "/api/v1/**/**").authenticated()
					.requestMatchers(HttpMethod.PUT, "/api/v1/**/**").authenticated()
					.requestMatchers(HttpMethod.DELETE, "/api/v1/**/**").authenticated()
					.requestMatchers(HttpMethod.DELETE, "/api/v1/**/**/**/**/**").hasAuthority("ADMIN");
					customizer.anyRequest().authenticated();
				});

		http.addFilterBefore(new JWTFilter(this.tokenProvider), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	//This method is needed to encrypt the password, it's called in UserService to encrypt the
	//password before saving it in the DataBase
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*").allowCredentials(false).maxAge(3600)
						.allowedHeaders("Accept", "Content-Type", "Origin", "Authorization", "X-Auth-Token","request-id","username","userId")
						.exposedHeaders("X-Auth-Token", "Authorization")
						.allowedMethods("POST", "GET", "DELETE", "PUT", "OPTIONS");
			}
		};
	}

}
