package centauri.academy.cerepro.service;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import centauri.academy.cerepro.persistence.entity.User;
import centauri.academy.cerepro.persistence.repository.UserRepository;

/**
 * 
 * @author maurizio.franco@ymail.com
 *
 */
@Service
public class UserService {
	
	public static final Logger logger = LoggerFactory.getLogger(UserService.class);	
	
	@Autowired
	private UserRepository userRepository;
//	@Autowired
//	private UserService userService;
	
//	@Autowired
//	private UserRepository userRepository;
//
//	public User loadUserByEmail(String email)
//			throws Exception {
//		logger.debug("UserService.loadUserByEmail - START");
//		Optional<User> optionalObj = userRepository.findByEmail(email);
//		if (!optionalObj.isPresent()) {
//			throw new Exception("Warning! User not found with email: " + email);
//		} else {
//			return optionalObj.get();
//		}
//	}
//	
//	public User loadUserById(Long userId)
//			throws Exception {
//		logger.debug("UserService.loadUserById - START");
//		Optional<User> optionalObj = userRepository.findById(userId);
//		if (!optionalObj.isPresent()) {
//			throw new Exception("Opps! user not found with user id: " + userId);
//		} else {
//			return optionalObj.get();
//		}
//	}
//	
//	public List<User> getAllUsersOrderByLastname () {
//		Sort sort = new Sort(Sort.Direction.ASC, "lastname");
//		return (List<User>)userRepository.findAll(sort);
//	}
//	
	/**
	 * Provides list of user entities from repository
	 * @return List<User>, list of User entity objects
	 */
	public List<User> getAll () {
		logger.debug("getAll - START");
		return (List<User>)userRepository.findAll();
	}
	
	/**
	 * Try to delete all entities from user table
	 */
	public void deleteAll () {
		logger.debug("deleteAll - START");
		userRepository.deleteAll();
	}
	
	public Optional<User> getById(Long id) {
		logger.debug("getById -START");
		return userRepository.findById(id);
	}
	
	public Boolean updateEnabledById(Long id, Boolean b) {
		logger.debug("updateEnabledById - START");
		return (userRepository.updateEnabledById(id, b)==1?true:false);
	}
}
