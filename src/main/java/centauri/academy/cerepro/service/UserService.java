package centauri.academy.cerepro.service;
/**
 * 
 * @author anna
 *
 */
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import centauri.academy.cerepro.persistence.entity.User;
import centauri.academy.cerepro.persistence.repository.UserRepository;

@Service
public class UserService {
	
	public static final Logger logger = LoggerFactory.getLogger(UserService.class);	
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserService userService;
	
	public long getPeriod(LocalDate date) {
		logger.info("UserService getPeriod start");
		LocalDateTime start = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 0, 0, 0);
		LocalDateTime end = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 23, 59, 59);
		long count = userRepository.getUserRegdateInPeriod(start, end);
	
		logger.info("UserService getPeriod end " + count);
		return count;
	}
	
	public long getUserRegistratedInPeriod(long period) {
		logger.info("UserService getUserRegistratedInPeriod start");
		long count = 0;
		LocalDate date = LocalDate.now();
		//User currentUser;
		for(int i = 0; i < period; i++) {
			LocalDate yesterday = date.minusDays(i);
		//	currentUser = new User();
			count += userService.getPeriod(yesterday);		
		}
		logger.info("UserService getUserRegistratedInPeriod end" + count);
		return count;
	}
	public long getUserRegistratedInLastWeek(long period) {
		logger.info("UserService getUserRegistratedInPeriod start");
		long count = 0;
		LocalDate date = LocalDate.now();
		//User currentUser;
		for(int i = 6; i < period; i++) {
			LocalDate yesterday = date.minusDays(i);
		//	currentUser = new User();
			count += userService.getPeriod(yesterday);		
		}
		logger.info("UserService getUserRegistratedInPeriod end" + count);
		return count;
	}
	
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
//	public List<User> getAllUsers () {
//		return (List<User>)userRepository.findAll();
//	}
}
