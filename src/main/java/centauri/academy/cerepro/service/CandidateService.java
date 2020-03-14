/**
 * 
 */
package centauri.academy.cerepro.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import centauri.academy.cerepro.persistence.entity.Candidate;
import centauri.academy.cerepro.persistence.entity.CandidateCustom;
import centauri.academy.cerepro.persistence.entity.CandidateStates;
import centauri.academy.cerepro.persistence.repository.candidate.CandidateRepository;

/**
 * 
 * 
 * @author m.franco@proximainformatica.com
 *
 */
@Service 
public class CandidateService {
	public static final Logger log = LoggerFactory.getLogger(CandidateService.class);
	
	@Autowired
	private CandidateRepository candidateRepository;
	
	/**
	 * Gets all candidates entries from table
	 * 
	 * @return List<Candidate>
	 */
	public List<Candidate> getAll () {
		log.info("CandidateService.getAll - START");
		return candidateRepository.findAll();
	}
	
//	/**
//	 * Gets all candidates custom(Candidates + Users)
//	 * COMMENTED ON 24/01/20 because no more used!!!!!!!! by maurizio
//	 * @return List<CandidateCustom>
//	 */
//	public List<CandidateCustom> getAllCustom () {
//		log.info("CandidateService.getAllCustom - START");
//		return candidateRepository.getAllCustomCandidates();
//	}
	
//	/**
//	 * Gets all candidates custom(Candidates + Users) in paginated version list
//	 * 
//	 * @param Pageable p, page info
//	 * @return Page<CandidateCustom>
//	 */
//	public Page<CandidateCustom> getAllCustomPaginated (Pageable p) {
//		log.info("CandidateService.getAllCustomPaginated - START - with pageable info {}", p);
//		return candidateRepository.getAllCustomCandidatesPaginated(p);
//	}
	
	/**
	 * Gets all candidates custom(Candidates + Users) in paginated version list, by course code field
	 * 
	 * @param Pageable p, page info
	 * @param String courseCode, string value for course code field
	 * @return Page<CandidateCustom>
	 */
	public Page<CandidateCustom> getAllCustomPaginatedByCourseCode (Pageable p, String courseCode) {
		log.info("CandidateService.getAllCustomPaginatedByCourseCode - START - with pageable info {0} and course code: {1}", p, courseCode);
		return candidateRepository.getAllCustomCandidatesPaginatedByCourseCode(p, courseCode);
	}
	
	/**
	 * Gets all candidates custom(Candidates + Users) by course code field
	 * 
	 * @return List<CandidateCustom>
	 */
	public List<CandidateCustom> getAllByCourseCode (String courseCode) {
		log.info("CandidateService.getAllByCourseCode - START with given course code {}", courseCode);
		List<CandidateCustom> items = candidateRepository.findByCourseCode(courseCode);
		return items;
	}
	
	/**
	 * Insert new candidate entity 
	 * 
	 */
	public Candidate insert (Candidate c) {
		log.info("CandidateService.insert START with given candidate {}", c);
		c.setCandidacyDateTime(LocalDateTime.now());
		c.setCandidateStatesId(CandidateStates.DEFAULT_INSERTING_STATUS_CODE);
		log.info("CandidateService.insert DEBUG with given candidate {}", c);
		return candidateRepository.save(c);
	}
	
	/**
	 * Update candidate entity 
	 * 
	 */
	public Candidate update (Candidate c) {
		log.info("CandidateService.update START with given candidate {}", c);
		//TO DO: check all form files mandatory and legacy....
		return candidateRepository.save(c);
	}
	
	/**
	 * Retrieve candidate by id
	 * 
	 * @param id of the candidate to retrieve
	 * @return candidate entity
	 */
	public Optional<Candidate> getById(long id) {
		log.info("CandidateService.getById START with given id {}", id);
		return candidateRepository.findById(id);
		
	}
	
	/**
	 * Retrieve candidate by id
	 * 
	 * @param id of the candidate to retrieve
	 * @return candidate entity
	 */
	public CandidateCustom getCustomById(long id) {
		log.info("CandidateService.getCustomById START with given id {}", id);
		return candidateRepository.getSingleCustomCandidate(id);
		
	}
	
	/**
	 * Delete candidate by id
	 * 
	 * @param id of the candidate to delete
	 */
	public void deleteById(long id) {
		log.info("CandidateService.deleteById START with given id {}", id);
		candidateRepository.deleteById(id);
		
	}
	
//	/**
//	 * Insert new candidate custom by inserting into candidates and users tables.
//	 * 
//	 * @param CandidateCustom cc, candidate custom info to insert
//	 */
//	public void insertCustom (CandidateCustom cc) {
//		log.info("CandidateService.insert START with given candidate {}", c);
//		c.setCandidacyDateTime(LocalDateTime.now());
//		c.setCandidateStatesId(CandidateStates.DEFAULT_INSERTING_STATUS_CODE);
//		log.info("CandidateService.insert DEBUG with given candidate {}", c);
//		candidateRepository.save(c);
//	}
	
	
}
