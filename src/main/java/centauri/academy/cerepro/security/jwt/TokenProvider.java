package centauri.academy.cerepro.security.jwt;

import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import centauri.academy.cerepro.security.service.UserDetailsServiceImpl;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenProvider {

	@Value("${cerepro.jwtExpirationMs}")
	private String jwtExpirationMs;

	private final SecretKey key;

	private final JwtParser jwtParser;

	@Autowired
	private final UserDetailsServiceImpl userService;

	public TokenProvider(UserDetailsServiceImpl userService) {
		this.key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
		this.jwtParser = Jwts.parserBuilder().setSigningKey(this.key).build();
		this.userService = userService;
	}

	public String createToken(String username) {
		Date now = new Date();
		Date validity = new Date(now.getTime() + Long.parseLong(jwtExpirationMs));
		String token = Jwts.builder().setId(UUID.randomUUID().toString()).setSubject(username).setIssuedAt(now)
				.signWith(this.key).setExpiration(validity).compact();
		return token;
	}

	public Authentication getAuthentication(String token) {
		System.out.println(token);
		String username = this.jwtParser.parseClaimsJws(token).getBody().getSubject();

		UserDetails userDetails = userService.loadUserByUsername(username);
		if (userDetails == null) {
			throw new UsernameNotFoundException("User '" + username + "' not found");
		}

		userDetails = org.springframework.security.core.userdetails.User.withUsername(username).password("")
				.authorities(userDetails.getAuthorities()).accountExpired(false).accountLocked(false).credentialsExpired(false)
				.disabled(false).build();

		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}

}
