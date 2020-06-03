/**
 * 
 */
package centauri.academy.cerepro.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import centauri.academy.cerepro.persistence.repository.usersurveytoken.UserSurveyTokenRepository;

/**
 * @author m.franco
 */
@Service
public class UserSurveyTokenService {

	public static final Logger logger = LoggerFactory.getLogger(UserSurveyTokenService.class);

	@Autowired
	private UserSurveyTokenRepository userSurveyTokenRepository;
	
	/**
	 * Provides to delete all entities from UserSurveyToken table
	 */
	public void deleteAll() {
		logger.debug("deleteAll - START");
		userSurveyTokenRepository.deleteAll();
	}

}
