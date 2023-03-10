package centauri.academy.cerepro.backend;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import centauri.academy.cerepro.persistence.entity.Candidate;
import centauri.academy.cerepro.service.CandidateService;

/**
 * 
 * @author giacomo
 * @author m.franco@proximainformatica.com
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/candidate")
public class CandidateController {
	public static final Logger logger = LoggerFactory.getLogger(CandidateController.class);

	@Autowired
	private CandidateService candidateService;

	/**
	 * listAllCandidate method gets all candidates
	 * 
	 * @return a new ResponseEntity with the given status code
	 */
//	* COMMENTED BECAUSE FOR NOW, NOT CONSUMED ---> maurizio
//  * UNCOMMENTED FOR THE USE AND CREATION OF THE COURSE CODE TABLE ---> giammarco
	@GetMapping("/")
	public ResponseEntity<List<Candidate>> listAllCandidate() {
		List<Candidate> candidates = candidateService.getAll();
		if (candidates.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else
			return new ResponseEntity<>(candidates, HttpStatus.OK);
	}

	/**
	 * createCandidate method creates a candidate
	 * 
	 * @param candidate to be created
	 * @return a new ResponseEntity with the given status code
	 */
//	COMMENTED BECAUSE FOR NOW, NOT CONSUMED ---> maurizio
//	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Candidate> createCandidate(@Valid @RequestBody final Candidate candidate) {
//		logger.info("Creating Candidate : {}", candidate);
//		candidateService.insert(candidate);
//		return new ResponseEntity<>(candidate, HttpStatus.CREATED);
//	}

	/**
	 * getUserById method gets a candidate by id
	 * 
	 * @param id of the candidate to be selected
	 * @return a new ResponseEntity with the given status code
	 */
//	COMMENTED BECAUSE FOR NOW, NOT CONSUMED ---> maurizio
//	@GetMapping("/{id}")
//	public ResponseEntity<CeReProAbstractEntity> getCandidateById(@PathVariable("id") final Long id) {
//		Candidate candidate = null;
//		Optional<Candidate> c = candidateService.getById(id);
//		if (!c.isPresent()) {
//			return new ResponseEntity<>(new CustomErrorType("Candidate with id " + id + " not found"),
//					HttpStatus.NO_CONTENT);
//		} else
//			candidate = c.get();
//		return new ResponseEntity<>(candidate, HttpStatus.OK);
//	}

	/**
	 * updateCandidate method updates a candidate
	 * 
	 * @param id        of the candidate to be updated
	 * @param candidate with the fields updated
	 * @return a new ResponseEntity with the given status code
	 */
//	COMMENTED BECAUSE FOR NOW, NOT CONSUMED ---> maurizio
//	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<CeReProAbstractEntity> updateCandidate(@PathVariable("id") final Long id,
//			@RequestBody Candidate candidate) {
//		// fetch user based on id and set it to currentUser object of type UserDTO
//		Candidate currentCandidate = null;
//		Optional<Candidate> c = candidateService.getById(id);
//		if (c.isPresent()) {
//			currentCandidate = c.get();
//			String value = candidate.getUserId() + "";
////			if (value.equals("null"))
////				currentCandidate.setUserId(currentCandidate.getUserId());
////			else
//			currentCandidate.setUserId(candidate.getUserId());
//			// update currentUser object data with user object data
//			currentCandidate.setDomicileCity(candidate.getDomicileCity());
////			currentCandidate.setDomicileHouseNumber(candidate.getDomicileHouseNumber());
////			currentCandidate.setDomicileStreetName(candidate.getDomicileStreetName());
//			currentCandidate.setStudyQualification(candidate.getStudyQualification());
//			currentCandidate.setGraduate(candidate.getGraduate());
//			currentCandidate.setHighGraduate(candidate.getHighGraduate());
//			currentCandidate.setStillHighStudy(candidate.getStillHighStudy());
//			currentCandidate.setMobile(candidate.getMobile());
//			currentCandidate.setCvExternalPath(candidate.getCvExternalPath());
//			// save currentUser object
//			candidateService.insert(currentCandidate);
//			// return ResponseEntity object
//			return new ResponseEntity<>(currentCandidate, HttpStatus.OK);
//		} else {
//			return new ResponseEntity<>(
//					new CustomErrorType("Unable to update. Candidate with id " + id + " not found."),
//					HttpStatus.NOT_FOUND);
//		}
//	}

	/**
	 * deleteCandidate method deletes a candidate
	 * 
	 * @param id of the candidate to be canceled
	 * @return a new ResponseEntity with the given status code
	 */
//	COMMENTED BECAUSE FOR NOW, NOT CONSUMED ---> maurizio
//	@DeleteMapping("/{id}")
//	public ResponseEntity<CeReProAbstractEntity> deleteCandidate(@PathVariable("id") final Long id) {
//		// logger.info("DELETE CANDIDATE - START");
//		Optional<Candidate> candidate = candidateService.getById(id);
//		if (!candidate.isPresent()) {
//			return new ResponseEntity<>(
//					new CustomErrorType("Unable to delete. Candidate with id " + id + " not found."),
//					HttpStatus.NOT_FOUND);
//		}
//		candidateService.deleteById(id);
//		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//	}

	/**
	 * Provides number of registered candidates on today
	 * @return
	 */
	@GetMapping("/todayRegistrated")
	public ResponseEntity<Long> getTodayRegistratedCandidates() {
		logger.info("getTodayRegistratedCandidates() - START");
		LocalDate today = LocalDate.now();
		long count = candidateService.getRegisteredCandidatesInDate(today);
		logger.info("getTodayRegistratedCandidates() - END - with count: " + count);
		return new ResponseEntity<Long>(count, HttpStatus.OK);
	}

	/**
	 * Provides number of registered candidates on yesterday
	 * @return
	 */
	@GetMapping("/yesterdayRegistrated")
	public ResponseEntity<Long> getYesterdayRegistratedCandidates() {
		logger.info("getYesterdayRegistratedCandidates() - START");
		LocalDate yesterday = LocalDate.now();
		yesterday = yesterday.minusDays(1);
		long count = candidateService.getRegisteredCandidatesInDate(yesterday);
		logger.info("getYesterdayRegistratedCandidates() - END - with count: " + count);
		return new ResponseEntity<Long>(count, HttpStatus.OK);
	}

	/**
	 * Provides number of registered candidates on last 7 days
	 * @return
	 */
	@GetMapping("/lastSevenDaysRegistrated")
	public ResponseEntity<Long> getLastSevenDaysRegistratedCandidates() {
		logger.info("getLastSevenDaysRegistratedCandidates - START");
		long count = candidateService.getRegisteredCandidatesFromDaysAgo(7);
		logger.info("getLastSevenDaysRegistratedCandidates() - END - with count: " + count);
		return new ResponseEntity<Long>(count, HttpStatus.OK);
	}


	/**
	 * Provides number of registered candidates on last 14 days
	 * @return
	 */
	@GetMapping("/lastWeekRegistrated")
	public ResponseEntity<Long> getRegistratedCandidatesOnLastTwoWeeks() {
		logger.info("getRegistratedCandidatesOnLastTwoWeeks() - START");
		long count = candidateService.getRegisteredCandidatesFromDaysAgo(14);
		logger.info("getRegistratedCandidatesOnLastTwoWeeks() - END - with count: " + count);
		return new ResponseEntity<Long>(count, HttpStatus.OK);
	}	

}
