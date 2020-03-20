package centauri.academy.cerepro.service;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import centauri.academy.cerepro.persistence.entity.Role;
import centauri.academy.cerepro.persistence.repository.RoleRepository;
/**
 * 
 * @author maurizio - m.franco@proximanetwork.it
 *
 */
@Service
public class RoleService {
	
	public static final Logger logger = LoggerFactory.getLogger(RoleService.class);	
	
	@Autowired
	RoleRepository roleRepository;
	
	public List<Role> getAll () {
		logger.info("getAll - START");
		List<Role> roles = roleRepository.findAll();
		logger.info("getAll - END - returning " + roles.size() + " roles.");
		return roles ;
	}
}
