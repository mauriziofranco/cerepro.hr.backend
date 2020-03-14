/**
 * 
 */
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

import centauri.academy.cerepro.persistence.entity.CeReProAbstractEntity;
import centauri.academy.cerepro.persistence.entity.CustomErrorType;
import centauri.academy.cerepro.persistence.entity.Interview;
import centauri.academy.cerepro.persistence.repository.InterviewRepository;


/**
 * @author Fabio Li Greci
 *
 */

@RestController
@RequestMapping("/api/v1/interview")
public class InterviewController {
	public static final Logger logger = LoggerFactory.getLogger(InterviewController.class);
	@Autowired
	private InterviewRepository interviewRepository;

	/****** SELECT-ALL ******/
	@GetMapping("/")
	public ResponseEntity<List<Interview>> listAllInterview() {
		List<Interview> interviews = interviewRepository.findAll();

		if (interviews.isEmpty()) {

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(interviews, HttpStatus.OK);
	}

	/****** INSERT ******/
	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CeReProAbstractEntity> createInterview(@Valid @RequestBody final Interview interview) {

		logger.info("Creating interview : {}", interview);
		interviewRepository.save(interview);
		return new ResponseEntity<>(interview, HttpStatus.CREATED);
	}

	/****** SELECT BY ID ******/
	@GetMapping("/{id}")
	public ResponseEntity<CeReProAbstractEntity> getInterviewById(@PathVariable("id") final Long id) {
		Optional<Interview> interview = interviewRepository.findById(id);

		if (!interview.isPresent()) {

			return new ResponseEntity<>(new CustomErrorType("Interview with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(interview.get(), HttpStatus.OK);
	}

	/****** UPDATE ******/
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CeReProAbstractEntity> updateInterview(@PathVariable("id") final Long id,
			@RequestBody Interview interview) {

		Optional<Interview> currentInterview = interviewRepository.findById(id);

		if (!currentInterview.isPresent()) {
			return new ResponseEntity<>(new CustomErrorType("Unable to upate. Interview with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}


		

		

		
		currentInterview.get().setQuestionText(interview.getQuestionText());
		currentInterview.get().setAnsa(interview.getAnsa());
		currentInterview.get().setAnsb(interview.getAnsb());
		currentInterview.get().setAnsc(interview.getAnsc());
		currentInterview.get().setAnsd(interview.getAnsd());
		currentInterview.get().setAnse(interview.getAnse());
		currentInterview.get().setAnsf(interview.getAnsf());
		currentInterview.get().setAnsg(interview.getAnsg());
		currentInterview.get().setAnsh(interview.getAnsh());
		currentInterview.get().setAnsi(interview.getAnsi());
		currentInterview.get().setAnsj(interview.getAnsj());

		interviewRepository.saveAndFlush(currentInterview.get());

		return new ResponseEntity<>(currentInterview.get(), HttpStatus.OK);
	}
	
	/****** DELETE ******/
	@DeleteMapping("/{id}")
	public ResponseEntity<CeReProAbstractEntity> deleteInterview(@PathVariable("id") final Long id) {
		Optional<Interview> interview = interviewRepository.findById(id);
		if (!interview.isPresent()) {
			return new ResponseEntity<>(new CustomErrorType("Unable to delete. Interview with id " + id + " not found."), HttpStatus.NOT_FOUND);
		}
		interviewRepository.delete(interview.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
