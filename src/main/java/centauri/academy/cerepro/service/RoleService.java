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
 * @author maurizio.franco@ymail.com
 *
 */
@Service
public class RoleService {

	public static final Logger logger = LoggerFactory.getLogger(RoleService.class);

	@Autowired
	RoleRepository roleRepository;

	/**
	 * Provides list of role entities from repository
	 * 
	 * @return List<Role>, list of Role entity objects
	 */
	public List<Role> getAll() {
		logger.info("getAll - START");
		List<Role> roles = roleRepository.findAll();
		logger.info("getAll - END - returning " + roles.size() + " roles.");
		return roles;
	}

	/**
	 * Try to delete all entities from role table
	 */
	public void deleteAll() {
		logger.debug("deleteAll - START");
		roleRepository.deleteAll();
	}
	
	/**
	 * Provides to insert given role entity 
	 * 
	 * @return entity, Role entity just inserted
	 */
	public Role insert(Role entity) {
		logger.info("insert - START - entity: {}", entity);
		entity = roleRepository.save(entity);
		logger.info("insert - END - returning inserted entity: {}" + entity);
		return entity;
	}
}
