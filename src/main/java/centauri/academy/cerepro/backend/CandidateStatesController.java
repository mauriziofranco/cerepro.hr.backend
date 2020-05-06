package centauri.academy.cerepro.backend;



import java.util.List;
import java.util.Optional;

import javax.validation.Valid;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import centauri.academy.cerepro.persistence.entity.CandidateStates;
import centauri.academy.cerepro.persistence.entity.CeReProAbstractEntity;
import centauri.academy.cerepro.persistence.entity.custom.CustomErrorType;
import centauri.academy.cerepro.persistence.repository.CandidateStatesRepository;

/**
 * 
 * @author Sebastiano Varchetta
 *
 */

@RestController
@RequestMapping("/api/v1/candidateStates")
public class CandidateStatesController {

	public static final Logger logger = LoggerFactory.getLogger(CandidateStatesController.class);

	@Autowired
	private CandidateStatesRepository candidateStatesRepository;

	@Autowired
	public void setCoursePageJpaRepository(CandidateStatesRepository candidatesStaesRepository) {
		this.candidateStatesRepository = candidatesStaesRepository;
	}

	@GetMapping("/")
	public ResponseEntity<List<CandidateStates>> listAllCandidatesStates() {
		List<CandidateStates> candidateStates = candidateStatesRepository.findAll();

		if (candidateStates.isEmpty()) {

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(candidateStates, HttpStatus.OK);
	}

	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CeReProAbstractEntity> createCandidateStates(
			@Valid @RequestBody final CandidateStates candidateStates) {
		logger.info("Creating candidatestates : {}", candidateStates);
		candidateStatesRepository.save(candidateStates);
		return new ResponseEntity<>(candidateStates, HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CeReProAbstractEntity> updateCandidateStates(@PathVariable("id") final Long id,
			@RequestBody CandidateStates candidateStates) {

		Optional<CandidateStates> currentCandidateStates = candidateStatesRepository.findById(id);

		if (!currentCandidateStates.isPresent()) {
			return new ResponseEntity<>(
					new CustomErrorType("Unable to update. CandidateStates with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentCandidateStates.get().setRoleId(candidateStates.getRoleId());
		currentCandidateStates.get().setStatusCode(candidateStates.getStatusCode());
		currentCandidateStates.get().setStatusLabel(candidateStates.getStatusLabel());
		currentCandidateStates.get().setStatusDescription(candidateStates.getStatusDescription());
		currentCandidateStates.get().setStatusColor(candidateStates.getStatusColor());
		
		candidateStatesRepository.saveAndFlush(currentCandidateStates.get());

		return new ResponseEntity<>(currentCandidateStates.get(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CeReProAbstractEntity> getCandidateStatesById(@PathVariable("id") final Long id) {
		Optional<CandidateStates> candO = candidateStatesRepository.findById(id);
		if (!candO.isPresent()) {
			return new ResponseEntity<>(new CustomErrorType("CandidateStates with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(candO.get(), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<CeReProAbstractEntity> deleteCandidateStates(@PathVariable("id") final Long id) {
		Optional<CandidateStates> candiStat = candidateStatesRepository.findById(id);
		if (!candiStat.isPresent()) {
			return new ResponseEntity<>(
					new CustomErrorType("Unable to delete. CandidateStates with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		candidateStatesRepository.delete(candiStat.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}


}
