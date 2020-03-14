package centauri.academy.cerepro.rest;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class SpringSessionUser extends User{
	
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SpringSessionUser(String username, String password, Long id, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		setId(id);
	}

}
