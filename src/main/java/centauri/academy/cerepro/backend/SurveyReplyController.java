package centauri.academy.cerepro.backend;

import java.time.LocalDate;
import java.util.ArrayList;
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
import centauri.academy.cerepro.persistence.entity.SurveyReply;
import centauri.academy.cerepro.persistence.entity.custom.CustomErrorType;
import centauri.academy.cerepro.persistence.entity.custom.SurveyReplyCustom;
import centauri.academy.cerepro.persistence.repository.SurveyRepository;
import centauri.academy.cerepro.persistence.repository.UserRepository;
import centauri.academy.cerepro.persistence.repository.surveyreply.SurveyReplyRepository;
import centauri.academy.cerepro.service.SurveyReplyService;

@RestController
@RequestMapping("/api/v1/surveyreply")
public class SurveyReplyController {

	public static final Logger logger = LoggerFactory.getLogger(SurveyReplyController.class);

	@Autowired
	private SurveyReplyRepository surveyReplyRepository;
	@Autowired
	private SurveyReplyService surveyReplyService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SurveyRepository surveyRepository;

	@Autowired
	public void setSurveyReplyJpaRepository(SurveyReplyRepository surveyReplyRepository) {
		this.surveyReplyRepository = surveyReplyRepository;
	}

	/** SELECT ALL SURVEY REPLIES */
	@GetMapping("/")
	public ResponseEntity<List<SurveyReply>> listAllSurveyReplies() {
		List<SurveyReply> surveyReplies = surveyReplyRepository.findAll();
		if (surveyReplies.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(surveyReplies, HttpStatus.OK);
	}

	/** CREATE A NEW SURVEY REPLY */
	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CeReProAbstractEntity> createSurveyReply(@Valid @RequestBody final SurveyReply surveyReply) {
		logger.info("Creating Survey Reply : {}", surveyReply);

		if (surveyRepository.findById(surveyReply.getSurveyId()) == null)
			return new ResponseEntity<>(new CustomErrorType("Unable to create new Survey Reply. Survey Id: "
					+ surveyReply.getSurveyId() + " is not present in database."), HttpStatus.CONFLICT);

		if (userRepository.findById(surveyReply.getUserId()) == null)
			return new ResponseEntity<>(new CustomErrorType("Unable to create new Survey Reply. User id: "
					+ surveyReply.getUserId() + " is not present in database."), HttpStatus.CONFLICT);

		surveyReplyRepository.save(surveyReply);
		return new ResponseEntity<>(surveyReply, HttpStatus.CREATED);
	}

	/** SELECT SURVEY REPLY BY ID */
	@GetMapping("/{id}")
	public ResponseEntity<CeReProAbstractEntity> getSurveyReplyById(@PathVariable("id") final Long id) {
		Optional<SurveyReply> surveyReply = surveyReplyRepository.findById(id);
		if (!surveyReply.isPresent()) {
			return new ResponseEntity<>(new CustomErrorType("SurveyReply with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(surveyReply.get(), HttpStatus.OK);
	}

	/** UPDATE SURVEY REPLY */
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CeReProAbstractEntity> updateSurveyReply(@PathVariable("id") final Long id,
			@RequestBody SurveyReply surveyReply) {

		Optional<SurveyReply> currentSurveyReply = surveyReplyRepository.findById(id);

		if (!currentSurveyReply.isPresent()) {
			return new ResponseEntity<>(
					new CustomErrorType("Unable to upate. SurveyReply with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		// update currentSurveyReply object data with SurveyReply object data

		currentSurveyReply.get().setUserId(surveyReply.getUserId());
		currentSurveyReply.get().setSurveyId(surveyReply.getSurveyId());
		currentSurveyReply.get().setStarttime(surveyReply.getStarttime());
		currentSurveyReply.get().setEndtime(surveyReply.getEndtime());
		currentSurveyReply.get().setAnswers(surveyReply.getAnswers());
		currentSurveyReply.get().setPdffilename(surveyReply.getPdffilename());
		currentSurveyReply.get().setPoints(surveyReply.getPoints());

		surveyReplyRepository.saveAndFlush(currentSurveyReply.get());

		return new ResponseEntity<>(currentSurveyReply.get(), HttpStatus.OK);
	}

	/** DELETE SURVEY REPLY BY ID */
	@DeleteMapping("/{id}")
	public ResponseEntity<CeReProAbstractEntity> deleteSurveyReply(@PathVariable("id") final Long id) {
		Optional<SurveyReply> surveyReply = surveyReplyRepository.findById(id);
		if (!surveyReply.isPresent()) {
			return new ResponseEntity<>(
					new CustomErrorType("Unable to delete. SurveyReply with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		surveyReplyRepository.delete(surveyReply.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/** SELECT SURVEY REPLY LAST 7 DAYS */
	@GetMapping("/lastweek")
	public ResponseEntity<List<SurveyReplyCustom>> getSurveyReplyEndTimeLastWeek() {
		// calculating the number of insertions in the last 7 days
		List<SurveyReplyCustom> toReturn = surveyReplyService.throwbackPeriods(7);
		return new ResponseEntity<List<SurveyReplyCustom>>(toReturn, HttpStatus.OK);
	}

	/** SELECT SURVEY REPLY LAST 30 DAYS */
	@GetMapping("/lastmonth")
	public ResponseEntity<List<SurveyReplyCustom>> getSurveyReplyEndTimeLastMonth() {
		// calculating the number of insertions in the last 30 days
		List<SurveyReplyCustom> toReturn = surveyReplyService.throwbackPeriods(30);
		return new ResponseEntity<List<SurveyReplyCustom>>(toReturn, HttpStatus.OK);
	}

	/** SELECT SURVEY REPLY LAST 365 DAYS */
	@GetMapping("/lastyear")
	public ResponseEntity<List<SurveyReplyCustom>> getSurveyReplyEndTimeLastYear() {
		// calculating the number of insertions in the last 365 days
		List<SurveyReplyCustom> toReturn = surveyReplyService.throwbackPeriods(365);
		return new ResponseEntity<List<SurveyReplyCustom>>(toReturn, HttpStatus.OK);
	}

	// questionari compilati oggi
	@GetMapping("/todayFilled")
	public ResponseEntity<Long> getSurveyFilledToday() {
		logger.info("getSurveyFilledToday() started");
		LocalDate date = LocalDate.now();
		Long count = surveyReplyService.getPeriod(date);
		logger.info("getSurveyFilledToday() end " + count);
		return new ResponseEntity<Long>(count, HttpStatus.OK);
	}

	// questionari compilati oggi
	@GetMapping("/lastSevenDaysFilled")
	public ResponseEntity<Long> getSurveyFilledLastSevenDays() {
		logger.info("getSurveyFilledLastSevenDays() started");
		Long count = surveyReplyService.surveyFilledInPeriod(7);
		logger.info("getSurveyFilledLastSevenDays() end" + count);
		return new ResponseEntity<Long>(count, HttpStatus.OK);
	}

	// questionari compilati ieri
	@GetMapping("/yesterdayFilled")
	public ResponseEntity<Long> getSurveyFilledYesterday() {
		logger.info("getSurveyFilledToday() started");
		LocalDate date = LocalDate.now();
		date = date.minusDays(1);
		Long count = surveyReplyService.getPeriod(date);
		logger.info("getSurveyFilledToday() end " + count);
		return new ResponseEntity<Long>(count, HttpStatus.OK);
	}

	// questionari compilati settimana scorsa
	@GetMapping("/lastWeekFilled")
	public ResponseEntity<Long> getSurveyFilledLastWeek() {
		logger.info("getSurveyFilledLastSevenDays() started");
		Long count = surveyReplyService.surveyFilledInLastWeek(14);
		logger.info("getSurveyFilledLastSevenDays() end" + count);
		return new ResponseEntity<Long>(count, HttpStatus.OK);
	}
}