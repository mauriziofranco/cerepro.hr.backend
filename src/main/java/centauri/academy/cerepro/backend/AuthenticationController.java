/**
 * 
 */
package centauri.academy.cerepro.backend;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author maurizio
 *
 */
@CrossOrigin("*")
@RestController
public class AuthenticationController {
    
	public static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
	
	@RequestMapping("/user")
	public Principal gettingUserForAuthentication(Principal user) {
		logger.info("gettingUserForAuthentication - START");
		return user;
	}
}