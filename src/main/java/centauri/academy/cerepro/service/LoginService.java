/**
 * 
 */
package centauri.academy.cerepro.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import centauri.academy.cerepro.persistence.entity.User;
import centauri.academy.cerepro.persistence.repository.UserRepository;
import centauri.academy.cerepro.rest.SpringSessionUser;

/**
 * @author maurizio
 *
 */
@Service
public class LoginService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		logger.info("LoginService.loadUserByUsername - START - email: " + email);
		Optional<User> optUser = userRepository.findByEmail(email);
		if (optUser.isEmpty()) {
			logger.info("LoginService.loadUserByUsername - WARNING - USER NOT FOUND, email: " + email);
			throw new UsernameNotFoundException("Opps! user not found with user-name: " + email);
		}
		// System.out.println(optUser.get().getId());
		logger.info("LoginService.loadUserByUsername - DEBUG - USER FOUND!!!!");
		return new SpringSessionUser(optUser.get().getEmail(), optUser.get().getPassword(), optUser.get().getId(),
				getAuthorities(optUser.get()));
	}

	public Collection<GrantedAuthority> getAuthorities(User user) {
		List<GrantedAuthority> authorities = new ArrayList<>();

		int role = user.getRole();

		if (role == 0) {
			authorities = AuthorityUtils.createAuthorityList("ADMIN");
		} else {
			authorities = AuthorityUtils.createAuthorityList("USER");

		}
		return authorities;
	}
}