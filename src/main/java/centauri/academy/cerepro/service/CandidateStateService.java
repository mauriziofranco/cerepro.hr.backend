package centauri.academy.cerepro.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import centauri.academy.cerepro.persistence.repository.CandidateStatesRepository;

/**
 * 
 * @author maurizio.franco@ymail.com
 *
 */
@Service 
public class CandidateStateService {
	
	private Logger logger = LoggerFactory.getLogger(CandidateStateService.class);
	
	@Autowired
	private CandidateStatesRepository candidateStateRepository;
	
	
	/**
	 * Try to delete all entities from candidate state table
	 */
	public void deleteAll() {
		logger.debug("deleteAll - START");
		candidateStateRepository.deleteAll();
	}
	
}
