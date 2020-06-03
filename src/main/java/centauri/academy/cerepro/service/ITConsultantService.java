/**
 * 
 */
package centauri.academy.cerepro.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import centauri.academy.cerepro.persistence.repository.itconsultant.ItConsultantRepository;

/**
 * @author m.franco
 */
@Service
public class ITConsultantService {

	public static final Logger logger = LoggerFactory.getLogger(ITConsultantService.class);

	@Autowired
	private ItConsultantRepository iTConsultantRepository;
	
	/**
	 * Provides to delete all entities from iTConsultant table
	 */
	public void deleteAll() {
		logger.debug("deleteAll - START");
		iTConsultantRepository.deleteAll();
	}

}
