package centauri.academy.cerepro;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import centauri.academy.cerepro.persistence.entity.User;
import centauri.academy.cerepro.persistence.repository.UserRepository;

/**
 * @author maurizio
 *
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

	public static final Logger logger = LoggerFactory.getLogger(UserDetailServiceImpl.class);

	@Autowired
	private UserRepository repository;

	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 
    	logger.info("UserDetailServiceImpl.loadUserByUsername - START - with given username: " + username);
      Optional<User> currentOptUser = repository.findByEmail(username);
      if (currentOptUser.isPresent()) {
    	  User currentUser = currentOptUser.get(); 
    	  List<GrantedAuthority> authorities = new ArrayList<>();
    	  int role = currentUser.getRole();

  		if (role == 0) {
  			authorities = AuthorityUtils.createAuthorityList("ADMIN");
  		} else {
  			authorities = AuthorityUtils.createAuthorityList("USER");
  		}
  		
    	  UserDetails user = new org.springframework.security.core
    	            .userdetails.User(username, currentUser.getPassword()
    	            , true, true, true, true, 
    	            authorities);
    	        return user;  
      } else {
    	  throw new UsernameNotFoundException ("utente non trovato!!");
      }
        
    }

}
