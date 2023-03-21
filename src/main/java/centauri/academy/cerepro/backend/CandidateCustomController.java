package centauri.academy.cerepro.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import centauri.academy.cerepro.persistence.entity.CeReProAbstractEntity;
import centauri.academy.cerepro.persistence.entity.custom.CandidateCustom;
import centauri.academy.cerepro.persistence.entity.custom.CustomErrorType;
import centauri.academy.cerepro.persistence.entity.custom.ListedCandidateCustom;
import centauri.academy.cerepro.rest.request.candidate.RequestCandidateCustom;
import centauri.academy.cerepro.rest.request.candidate.RequestUpdateCandidateCustom;
import centauri.academy.cerepro.service.CandidateService;
import centauri.academy.cerepro.service.exception.CandidateNotFoundException;

/**
 * 
 * @author maurizio.franco@ymail.com
 * 
 */
@RestController
@RequestMapping("/api/v1/candidatecustom")
public class CandidateCustomController {
	
	@Autowired
	private CandidateService candidateService;

	public static final Logger logger = LoggerFactory.getLogger(CandidateCustomController.class);
//	@Autowired
//	private UserRepository userRepository;
//	@Autowired
//	private RoleRepository roleRepository;
//	@Autowired
//	private UserSurveyTokenRepository userSurveyTokenRepository;
//	@Autowired
//	private SurveyReplyRepository surveyReplyRepository;
	

//	/**
//	 * getAllCandidateCustom method gets all candidates custom
//	 * 
//	 * COMMENTED ON 24/01/20 because no more used!!!!!!!! by maurizio
//	 * 
//	 * @return a new ResponseEntity with the given status code
//	 */
//	@GetMapping("/")
//	public ResponseEntity<List<CandidateCustom>> getAllCandidateCustom() {
//		List<CandidateCustom> candidates = candidateService.getAllCustom();
//		if (candidates.isEmpty()) {
//			return new ResponseEntity<List<CandidateCustom>>(HttpStatus.NO_CONTENT);
//		} else
//			return new ResponseEntity<List<CandidateCustom>>(candidates, HttpStatus.OK);
//	}

	/******* PAGEABLE ********/

	
	/**
	 * Method called from frontend, when user DOESN'T select a particular course code or a job code.
	 * 
	 * Provides a paginated list of candidate filtered by pagination info.
	 *  
	 * @param size
	 * @param number
	 * @return
	 */
	@GetMapping("/paginated/{size}/{number}/")
	public ResponseEntity<Page<ListedCandidateCustom>> getPaginatedCandidate(@PathVariable("size") final int size,
			@PathVariable("number") final int number) {
		//Page<CandidateCustom> cC = candidateService.getAllCustomPaginated(PageRequest.of(number, size, Sort.Direction.ASC, "id"));
		Page<ListedCandidateCustom> cC = candidateService.getAllCustomPaginatedByCourseCode(PageRequest.of(number, size), null);
		if (cC.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Page<ListedCandidateCustom>>(cC, HttpStatus.OK);
	}
	
	/**
	 * Method called from frontend, when user select a particular course code or a job code.
	 * 
	 * Provides a paginated list of candidate filtered by course code, and pagination info.
	 *  
	 * @param size
	 * @param number
	 * @param code
	 * @return paginated list of candidate filtered by course code, and pagination info
	 * 
	 */
	@GetMapping("/paginated/{size}/{number}/{code}")
	public ResponseEntity<Page<ListedCandidateCustom>> getAllCustomCandidatesPaginatedByCode(@PathVariable("size") final int size,
			@PathVariable("number") final int number,@PathVariable("code") final String code) {
		//commented to allow ordering by candidacy_date_time
		//Page<CandidateCustom> cC = candidateService.getAllCustomPaginatedByCourseCode(PageRequest.of(number, size, Sort.Direction.ASC, "id"),courseCode);
		Page<ListedCandidateCustom> cC = candidateService.getAllCustomPaginatedByCourseCode(PageRequest.of(number, size),code);
		if (cC.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Page<ListedCandidateCustom>>(cC, HttpStatus.OK);
	}

	/**
	 * getCandidateCustomById method gets a candidateCustom by id
	 * 
	 * @param id of the candidateCustom to be selected
	 * @return a new ResponseEntity with the given status code
	 */
	@GetMapping("/{id}")
	public ResponseEntity<CeReProAbstractEntity> getCandidateCustomById(@PathVariable("id") final Long id) {
		logger.info("getCandidateCustomById - START - with id: {}", id);
		CandidateCustom candidateCustom = candidateService.getCustomById(id);
		if (candidateCustom == null) {
//			return new ResponseEntity<>(new CustomErrorType("candidateCustom with id " + id + " not found"),
//					HttpStatus.NOT_FOUND);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(candidateCustom, HttpStatus.OK);
	}
	
	/*
	 * COMMENTED BECAUSE FOR NOW, NOT USED ---> maurizio
	@GetMapping("/code/{code}")
	public ResponseEntity<List<ListedCandidateCustom>> listAllCandidateByCode(@PathVariable("code") final String code) {
		logger.info("started");
		List<ListedCandidateCustom> candidates = candidateService.getAllByCourseCode(code);
//		logger.info(candidates.get(0).getCourseCode());
		if (candidates.isEmpty()) {     			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}else
			return new ResponseEntity<List<ListedCandidateCustom>>(candidates, HttpStatus.OK);
	}
	*/
	
	

	/**
	 * Provides to create a new candidate from a given RequestCandidateCustom
	 * 
	 * @param requestCandidateCustom, RequestCandidateCustom instance to keep field to insert for a new Candidate entity to insert.
	 * @return ResponseEntity instance with the status code
	 */
	//COMENTED----> OLD VERSION
//	@Transactional
//	@PostMapping(value = "/")
//	public ResponseEntity<CeReProAbstractEntity> createCandidateCustom(@ModelAttribute final RequestCandidateCustom candidateCustom) {
//		logger.info("Creating candidateCustom : {}", candidateCustom);
//		
//		if (roleRepository.findByLevel(Role.JAVA_COURSE_CANDIDATE_LEVEL) == null) {
//
//			return new ResponseEntity<>(
//					new CustomErrorType("Unable to create new Candidate. Level " + Role.JAVA_COURSE_CANDIDATE_LEVEL + " is not present in database."),
//					HttpStatus.CONFLICT);
//		}
//		User user = null ;
//		Optional<User> optUser = userRepository.findByEmail(candidateCustom.getEmail()) ;
//		if (optUser.isPresent()) {
//			user = optUser.get();
//			return new ResponseEntity<>(new CustomErrorType("Unable to create new user. A Candidate with email "
//					+ candidateCustom.getEmail() + " already exist."), HttpStatus.CONFLICT);
//		} else { 
//
//
//			user = new User();
//	
//			user.setEmail(candidateCustom.getEmail());
//			user.setFirstname(candidateCustom.getFirstname());
//			user.setLastname(candidateCustom.getLastname());
//			user.setNote(candidateCustom.getNote());
//			System.out.println("Local date time: " + candidateCustom.getDateOfBirth());
//			Date inputDate = candidateCustom.getDateOfBirth();
//	
//			if (inputDate != null) {
//				SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
//				String inputStringDate = formatter.format(inputDate);
//				System.out.println("inputStringDate " + inputStringDate);
//	
//				if (inputStringDate.equals("11-nov-1111")) {
//					user.setDateOfBirth(null);
//				} else {
//					LocalDate dateToDB = inputDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//					user.setDateOfBirth(dateToDB);
//				}
//			}
//			user.setRegdate(LocalDateTime.now());
//			user.setRole(Role.JAVA_COURSE_CANDIDATE_LEVEL);
//	
//			//User userforCandidate = userRepository.save(user);
//			userRepository.save(user);
//		}
//
//		if (candidateCustom.getImgpath() != null) {
//
//			try {
//				String[] nameIdData = saveUploadedFiles(candidateCustom.getFiles(), user.getId().toString());
//				logger.info("nameIdData:" + nameIdData[0]);
//				user.setImgpath(nameIdData[0]);
//				//userforCandidate = userRepository.save(user);
//				userRepository.save(user);
//			} catch (IOException e) {
//				logger.error("Error", e);
//			}
//
//		}
//
//		Candidate candidate = new Candidate();
//
////		candidate.setUserId(userforCandidate.getId());
//		candidate.setUserId(user.getId());
//		candidate.setDomicileCity(candidateCustom.getDomicileCity());
////		candidate.setDomicileHouseNumber(candidateCustom.getDomicileHouseNumber());
////		candidate.setDomicileStreetName(candidateCustom.getDomicileStreetName());
//		candidate.setStudyQualification(candidateCustom.getStudyQualification());
//		candidate.setGraduate(candidateCustom.getGraduate());
//		candidate.setHighGraduate(candidateCustom.getHighGraduate());
//		candidate.setStillHighStudy(candidateCustom.getStillHighStudy());
//		candidate.setMobile(candidateCustom.getMobile());
//		candidate.setCourseCode(coursePageService.checkCoursePageCode(candidateCustom.getCourseCode()));
//		//TODO: AGGIUNGERE UN CONTROLLO SUL COURSE CODE SE NON è PRESENTE NEL DATABASE METTERNE UNO DI DEFAULT --> candidatura generica!!!!
////		candidate.setNote(candidateCustom.getNote());
//		candidate.setCandidacyDateTime(LocalDateTime.now());
//		if (candidateCustom.getCvExternalPath() != null) {
//
//			try {
//
//				String[] nameIdData = saveUploadedFiles(candidateCustom.getFiles(), user.getId().toString());
//				logger.info("nameIdData:" + nameIdData[1]);
//				candidate.setCvExternalPath(nameIdData[1]);
//			} catch (IOException e) {
//				logger.error("Error", e);
//			}
//		}
//
//		candidateService.insert(candidate);
//
//		logger.info("END POST");
//		return new ResponseEntity<>(candidate, HttpStatus.CREATED);
//
//	}	
	@Transactional
	@PostMapping(value = "/")
	//public ResponseEntity<CeReProAbstractEntity> insert(@ModelAttribute final RequestCandidateCustom requestCandidateCustom) {
	public ResponseEntity<CeReProAbstractEntity> insert(@ModelAttribute RequestCandidateCustom requestCandidateCustom) {
		logger.info("insert : {}", requestCandidateCustom);
		CeReProAbstractEntity objToReturn = null ;
		try {
			objToReturn = candidateService.createNewCandidate(requestCandidateCustom);
			return new ResponseEntity<CeReProAbstractEntity>(objToReturn, HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error("ERROR in inserting new candidate: ", e);
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			return new ResponseEntity<CeReProAbstractEntity>(new CustomErrorType("Unable to insert new candidate: " + requestCandidateCustom)  , HttpStatus.BAD_REQUEST);
		}

//		logger.info("END POST");
		// return new ResponseEntity<>(candidate, HttpStatus.CREATED);

	}	


	/**
	 * updateCandidateCustom method updates a candidate custom
	 * 
	 * @param id   of the user to be updated
	 * @param user with the fields updated
	 * @return a new ResponseEntity with the given status code
	 */
	@Transactional
	@PutMapping(value = "/{id}")
	public ResponseEntity<CeReProAbstractEntity> update(@PathVariable("id") Long id,
			@ModelAttribute RequestUpdateCandidateCustom candidateCustom) {
//		System.out.println("####### Updating candidateCustom : {}" + candidateCustom);
		logger.info("update: {} for id: {}", candidateCustom, id);
		CeReProAbstractEntity objToReturn = null ;
		try {
			objToReturn = candidateService.updateCandidate(id, candidateCustom);
			return new ResponseEntity<CeReProAbstractEntity>(objToReturn, HttpStatus.OK);
		} catch (CandidateNotFoundException e) {
			logger.error(e.getMessage());
			logger.error("ERROR in updating candidate, Candidate not found: ", e);
			return new ResponseEntity<CeReProAbstractEntity>(new CustomErrorType("Candidate not found. Unable to update candidate: " + candidateCustom)  , HttpStatus.NO_CONTENT);		
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error("ERROR in updating candidate: ", e);
			return new ResponseEntity<CeReProAbstractEntity>(new CustomErrorType("Unable to update candidate: " + candidateCustom)  , HttpStatus.BAD_REQUEST);
		}
//		ObjectMapper objectMapper = new ObjectMapper();
//
//		objectMapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
//		objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

//		boolean imgIsPresent = false;
//		boolean cvIsPresent = false;

//		if (candidateCustom == null) {
//			return new ResponseEntity<>(
//					new CustomErrorType("Unable to update. candidateCustom with id " + id + " not found."),
//					HttpStatus.NOT_FOUND);
//		}
//
//		System.out.println("############ PUT ENTRATO: ");
//		System.out.println(candidateCustom.getUserId());

//		CandidateCustom candidateCurrentCustom = candidateRepository.getSingleCustomCandidate(id);

//		Optional<User> optUser = userRepository.findById(candidateCustom.getUserId());

//		User currentUser = optUser.get();
//
//		currentUser.setEmail(candidateCustom.getEmail());
//		currentUser.setFirstname(candidateCustom.getFirstname());
//		logger.info("candidateCustom.getFirstname() : {}", candidateCustom.getFirstname());
//		currentUser.setLastname(candidateCustom.getLastname());
//		currentUser.setNote(candidateCustom.getNote());
//		Date inputDate = candidateCustom.getDateOfBirth();
//		if (inputDate != null) {
//			LocalDate dateToDB = inputDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//			currentUser.setDateOfBirth(dateToDB);
//		}
//
//		System.out.println("OLDIMG: " + candidateCustom.getOldImg());
//		System.out.println("NewIMG: " + candidateCustom.getImgpath());
//		String oldImg = candidateCustom.getOldImg();
//		String newImg = candidateCustom.getImgpath();
//
//		// se si oldImg e si newImg
//		if (newImg != null && !oldImg.equals("null") && !newImg.equals("null")) {
////			imgIsPresent = true;
////			String imgPath = "img/" +candidateCustom.getUserId()+candidateCustom.getImgpath();
//			try {
//
//				// save newImg
//				String[] nameIdData = candidateService.uploadFile(candidateCustom.getFiles(),
//						candidateCustom.getUserId().toString());
//				logger.info("nameIdData:" + nameIdData[0]);
//				currentUser.setImgpath(nameIdData[0]);
//
//				// delete oldImg
//				String sPath = candidateService.IMG_DIR + File.separatorChar + candidateCustom.getOldImg();
//				Path path = Paths.get(sPath);
//				Files.delete(path);
//
//			} catch (IOException e) {
//				logger.error("Error", e);
//			}
//
//		}
//
//		// se no oldImg si newImg
//		if (newImg != null && oldImg.equals("null") && !newImg.equals("null")) {
////			imgIsPresent = true;
////			String imgPath = "img/" +candidateCustom.getUserId()+ candidateCustom.getImgpath();
//
//			try {
//
//				// save firstNewImg
//				String[] nameIdData = candidateService.uploadFile(candidateCustom.getFiles(),
//						candidateCustom.getUserId().toString());
//				logger.info("nameIdData:" + nameIdData[0]);
//				currentUser.setImgpath(nameIdData[0]);
//
//			} catch (IOException e) {
//				logger.error("Error", e);
//			}
//
//		}

//		logger.info("Update currentUser : {}", currentUser.toString());
//		userRepository.save(currentUser);
//
//		Optional<Candidate> candidate = candidateService.getById(id);
//
//		Candidate currentCandidate = candidate.get();
//		currentCandidate.setDomicileCity(candidateCustom.getDomicileCity());
////		currentCandidate.setDomicileHouseNumber(candidateCustom.getDomicileHouseNumber());
////		currentCandidate.setDomicileStreetName(candidateCustom.getDomicileStreetName());
//		currentCandidate.setStudyQualification(candidateCustom.getStudyQualification());
//		currentCandidate.setGraduate(candidateCustom.getGraduate());
//		currentCandidate.setHighGraduate(candidateCustom.getHighGraduate());
//		currentCandidate.setStillHighStudy(candidateCustom.getStillHighStudy());
//		currentCandidate.setMobile(candidateCustom.getMobile());
//		currentCandidate.setCandidateStatusCode(candidateCustom.getCandidateStatesId());
//		
//		//System.out.println("candidateStatesId: "+currentCandidate.getCandidateStatesId());
//		//System.out.println("OLDCV: " + candidateCustom.getOldCV());
//		//System.out.println("NewCV: " + candidateCustom.getCvExternalPath());
//		String oldCV = candidateCustom.getOldCV();
//		String newCV = candidateCustom.getCvExternalPath();
//
//		// se si oldCV si newCV
//		if (newCV != null && !oldCV.equals("null") && !newCV.equals("null")) {
////			cvIsPresent = true;
////			String cvPath = "curriculumvitae/" +candidateCustom.getUserId()+ candidateCustom.getCvExternalPath();
//
//			try {
//
//				// save newCv
//				String[] nameIdData = candidateService.uploadFile(candidateCustom.getFiles(),
//						candidateCustom.getUserId().toString());
//				logger.info("nameIdData:" + nameIdData[1]);
//				currentCandidate.setCvExternalPath(nameIdData[1]);
//
//				// delete oldCV
//				String sPath = candidateService.CV_DIR + File.separatorChar + candidateCustom.getOldCV();
//				Path path = Paths.get(sPath);
//				Files.delete(path);
//
//			} catch (IOException e) {
//				logger.error("Error", e);
//			}
//
//		}
//
//		// se no oldCV si newCV
//		if (newCV != null && oldCV.equals("null") && !newCV.equals("null")) {
////			cvIsPresent = true;
////			String cvPath = "curriculumvitae/" +candidateCustom.getUserId()+ candidateCustom.getCvExternalPath();
//
//			try {
//
//				// save firstCV
//				String[] nameIdData = candidateService.uploadFile(candidateCustom.getFiles(),
//						candidateCustom.getUserId().toString());
//				logger.info("nameIdData:" + nameIdData[1]);
//				currentCandidate.setCvExternalPath(nameIdData[1]);
//
//			} catch (IOException e) {
//				logger.error("Error", e);
//			}
//
//		}
		
//		//System.out.println("Prima di essere salvato candidateStatesId: "+currentCandidate.getCandidateStatesId());
//		
//		candidateService.update(currentCandidate);
//        
//		//System.out.println("Dopo essere salvato candidateStatesId: "+currentCandidate.getCandidateStatesId());
//		//logger.info("Prima della fine della PUT.........................................................................................");
//		logger.info("END PUT");
//		return new ResponseEntity<>(candidateCustom, HttpStatus.OK);
		
		
		
		
		
	}

	/**
	 * Provides to delete candidate from database and cv and profile img files from file system.
	 * 
	 * @param id of the candidate to delete
	 * 
	 * @return a new ResponseEntity with the given status code
	 */
	@Transactional
	@DeleteMapping("/{id}")
	public ResponseEntity<CeReProAbstractEntity> delete(@PathVariable("id") final Long id) {
		logger.info("delete - START - for id: {}", id);
		try {
			candidateService.deleteCandidate(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (CandidateNotFoundException e) {
			logger.error(e.getMessage());
			logger.error("ERROR in updating candidate, Candidate not found: ", e);
			return new ResponseEntity<CeReProAbstractEntity>(new CustomErrorType("Candidate not found. Unable to delete candidate with id: " + id)  , HttpStatus.BAD_REQUEST);		
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error("ERROR in updating candidate: ", e);
			return new ResponseEntity<CeReProAbstractEntity>(new CustomErrorType("Unable to delete candidate with id: " + id)  , HttpStatus.CONFLICT);
		}
		
//		logger.info("DELETE CANDIDATE CUSTOM - START");
//		Optional<Candidate> candidateOpt = candidateService.getById(id);
//		logger.info("candidateOpt " + candidateOpt);
//		if (!candidateOpt.isPresent()) {
//			logger.info("candidate not present " + candidateOpt);
//			return new ResponseEntity<>(
//					new CustomErrorType("Unable to delete. Candidate with id " + id + " not found."),
//					HttpStatus.NOT_FOUND);
//		}
//
//		Candidate currentCandidate = candidateOpt.get();
//		logger.info("Deleting user with user id: " + currentCandidate.getUserId());
//		logger.info("candidate id " + currentCandidate.getUserId());
//		List<UserTokenSurvey> userTokenSurvey = userSurveyTokenRepository.findByUserId(currentCandidate.getUserId());
//		logger.info("####################### LIST #########" + userTokenSurvey.toString());
//
////		Optional<SurveyReply> surveyReplyOpt = surveyReplyRepository.findById(id); 
////		logger.info("####### surveyReplyOpt  ########"+surveyReplyOpt);
//		List<SurveyReply> surveyReply = surveyReplyRepository.findByUserId(currentCandidate.getUserId());
//		logger.info("####################### LIST <SurveyReply> surveyReply #########" + surveyReply.toString());
//		if (!userTokenSurvey.isEmpty()) {
//			return new ResponseEntity<>(
//					new CustomErrorType("Unable to delete. User with id " + id + " is userTokenSurvey referenced."),
//					HttpStatus.CONFLICT); // code 409
//
//		} else if (!surveyReply.isEmpty()) {
//			return new ResponseEntity<>(new CustomErrorType("Non è possibile cancellare l'utente con id: " + id
//					+ " in quanto esiste almeno un questionario associato ad esso."), HttpStatus.CONFLICT); // code 409
//
//		} else {
//			logger.info("####################### ");
//			logger.info("Deleting candidate with id: " + id);
//			candidateService.deleteById(id);
//
//			Optional<User> userOpt = userRepository.findById(currentCandidate.getUserId());
//			userRepository.deleteById(currentCandidate.getUserId());
//
//			if (userOpt.get().getImgpath() != null) {
//
//				String sPath = candidateService.IMG_DIR + File.separatorChar + userOpt.get().getImgpath();
//				Path path = Paths.get(sPath);
//				try {
//					Files.delete(path);
//				} catch (IOException e) {
//					logger.error("Error", e);
//				}
//			}
//
//			if (currentCandidate.getCvExternalPath() != null) {
//
//				String sPath = candidateService.CV_DIR + File.separatorChar + currentCandidate.getCvExternalPath();
//				Path path = Paths.get(sPath);
//				try {
//					Files.delete(path);
//				} catch (IOException e) {
//					logger.error("Error", e);
//				}
//			}
//
//			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//		}

	}

//	//COMMENTED BECAUSE NO MORE USED --> MAurizio --> 20200506
//	/**
//	 * @author daniele
//	 * 
//	 *         createCandidateCustom method creates a candidate
//	 *         
//	 *         
//	 *         USED BY REACT FRONTEND!!!!!!!!!!!!!!!!!
//	 * 
//	 * @param candidate to be created
//	 * @return a new ResponseEntity with the given status code
//	 */
//	@Transactional
//	@PostMapping(value = "/field/")
//	public ResponseEntity<?> createFieldCandidateCustom(@RequestBody final CandidateCustom candidateCustom) {
//
//		if (roleRepository.findByLevel(90) == null) {
//			return new ResponseEntity<>(
//					new CustomErrorType("Unable to create new Candidate. Level " + 90 + " is not present in database."),
//					HttpStatus.CONFLICT);
//		}
//
//		if (userRepository.findByEmail(candidateCustom.getEmail()).isPresent()) {
//			return new ResponseEntity<>(new CustomErrorType("Unable to create new candidate. A user with email "
//					+ candidateCustom.getEmail() + " already exist."), HttpStatus.CONFLICT);
//		} else {
//			User user = new User();
//
//			user.setEmail(candidateCustom.getEmail());
//			user.setFirstname(candidateCustom.getFirstname());
//			user.setLastname(candidateCustom.getLastname());
//			user.setDateOfBirth(candidateCustom.getDateOfBirth());
//			user.setRegdate(LocalDateTime.now());
//			user.setRole(90);
//
//			User userforCandidate = userRepository.save(user);
//			Candidate candidate = new Candidate();
//
//			candidate.setUserId(userforCandidate.getId());
//			candidate.setDomicileCity(candidateCustom.getDomicileCity());
////			candidate.setDomicileHouseNumber(candidateCustom.getDomicileHouseNumber());
////			candidate.setDomicileStreetName(candidateCustom.getDomicileStreetName());
//			candidate.setStudyQualification(candidateCustom.getStudyQualification());
//			candidate.setGraduate(candidateCustom.getGraduate());
//			candidate.setHighGraduate(candidateCustom.getHighGraduate());
//			candidate.setStillHighStudy(candidateCustom.getStillHighStudy());
//			candidate.setMobile(candidateCustom.getMobile());
//			candidate.setCandidacyDateTime(LocalDateTime.now());
//			candidateService.insert(candidate);
//
//			return new ResponseEntity<>(candidate, HttpStatus.CREATED);
//		}
//	}

	
//	//COMMENTED BECAUSE NO MORE USED --> MAurizio --> 20200506
//	/**
//	 * @author daniele
//	 * 
//	 *         updateCandidateCustom method updates a candidate custom
//	 *         
//	 *         USED BY REACT FRONTEND!!!!!!!!!!!!!!!!!
//	 * 
//	 * @param id   of the user to be updated
//	 * @param user with the fields updated
//	 * @return a new ResponseEntity with the given status code
//	 */
//	@PutMapping(value = "/field/{id}")
//	public ResponseEntity<CeReProAbstractEntity> updateFieldCandidateCustom(@PathVariable("id") final Long id,
//			@RequestBody final CandidateCustom candidateCustom) {
//		if (candidateCustom == null) {
//			return new ResponseEntity<>(
//					new CustomErrorType("Unable to update. candidateCustom with id " + id + " not found."),
//					HttpStatus.NOT_FOUND);
//		} else {
//
//			Optional<User> optUser = userRepository.findById(candidateCustom.getUserId());
//
//			if (!optUser.isPresent()) {
//				return new ResponseEntity<>(
//						new CustomErrorType(
//								"Unable to update. user with id " + candidateCustom.getUserId() + " not found."),
//						HttpStatus.NOT_FOUND);
//			} else {
//				User currentUser = optUser.get();
//
//				currentUser.setEmail(candidateCustom.getEmail());
//				currentUser.setFirstname(candidateCustom.getFirstname());
//				currentUser.setLastname(candidateCustom.getLastname());
//				currentUser.setDateOfBirth(candidateCustom.getDateOfBirth());
//
//				userRepository.save(currentUser);
//
//				Optional<Candidate> optCandidate = candidateService.getById(id);
//
//				if (!optCandidate.isPresent()) {
//					return new ResponseEntity<>(
//							new CustomErrorType("Unable to update. candidate with id " + id + " not found."),
//							HttpStatus.NOT_FOUND);
//				} else {
//					Candidate currentCandidate = optCandidate.get();
//					currentCandidate.setDomicileCity(candidateCustom.getDomicileCity());
////					currentCandidate.setDomicileHouseNumber(candidateCustom.getDomicileHouseNumber());
////					currentCandidate.setDomicileStreetName(candidateCustom.getDomicileStreetName());
//					currentCandidate.setStudyQualification(candidateCustom.getStudyQualification());
//					currentCandidate.setGraduate(candidateCustom.getGraduate());
//					currentCandidate.setHighGraduate(candidateCustom.getHighGraduate());
//					currentCandidate.setStillHighStudy(candidateCustom.getStillHighStudy());
//					currentCandidate.setMobile(candidateCustom.getMobile());
//					//currentCandidate.setCandidateStatesId(candidateCustom.getCandidateStatesId());
//					
//					
//					//System.out.println("Sei anche qua");
//					candidateService.insert(currentCandidate);
//					return new ResponseEntity<>(candidateCustom, HttpStatus.OK);
//				}
//			}
//		}
//	}

}