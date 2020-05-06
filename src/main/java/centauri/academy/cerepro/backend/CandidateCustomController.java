package centauri.academy.cerepro.backend;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import centauri.academy.cerepro.persistence.entity.Candidate;
import centauri.academy.cerepro.persistence.entity.CeReProAbstractEntity;
import centauri.academy.cerepro.persistence.entity.Role;
import centauri.academy.cerepro.persistence.entity.SurveyReply;
import centauri.academy.cerepro.persistence.entity.User;
import centauri.academy.cerepro.persistence.entity.UserTokenSurvey;
import centauri.academy.cerepro.persistence.entity.custom.CandidateCustom;
import centauri.academy.cerepro.persistence.entity.custom.CustomErrorType;
import centauri.academy.cerepro.persistence.entity.custom.ListedCandidateCustom;
import centauri.academy.cerepro.persistence.repository.RoleRepository;
import centauri.academy.cerepro.persistence.repository.UserRepository;
import centauri.academy.cerepro.persistence.repository.surveyreply.SurveyReplyRepository;
import centauri.academy.cerepro.persistence.repository.usersurveytoken.UserSurveyTokenRepository;
import centauri.academy.cerepro.rest.request.RequestCandidateCustom;
import centauri.academy.cerepro.rest.request.RequestUpdateCandidateCustom;
import centauri.academy.cerepro.service.CandidateService;
import centauri.academy.cerepro.service.CoursePageService;

/**
 * 
 * @author dario
 * @author joffre
 * @author daniele piccinni
 * @author m.franco@proximainformatica.com
 * 
 */
@RestController
@RequestMapping("/api/v1/candidatecustom")
public class CandidateCustomController {
	
	@Autowired
	private CandidateService candidateService;

	@Value("${app.folder.candidate.profile.img}")
	private String IMG_DIR;
	@Value("${app.folder.candidate.cv}")
	private String CV_DIR;	


	public static final Logger logger = LoggerFactory.getLogger(CandidateCustomController.class);
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserSurveyTokenRepository userSurveyTokenRepository;
	@Autowired
	private SurveyReplyRepository surveyReplyRepository;
	@Autowired
	private CoursePageService coursePageService ;

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
	 * 
	 * @author maurizio.franco
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
	 * @return
	 * 
	 * @author maurizio.franco
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

	/******** PAGEABLE *******/

	/**
	 * getCandidateCustomById method gets a candidateCustom by id
	 * 
	 * @param id of the candidateCustom to be selected
	 * @return a new ResponseEntity with the given status code
	 */
	@GetMapping("/{id}")
	public ResponseEntity<CeReProAbstractEntity> getCandidateCustomById(@PathVariable("id") final Long id) {
		CandidateCustom candidateCustom = candidateService.getCustomById(id);
		if (candidateCustom == null) {
			return new ResponseEntity<>(new CustomErrorType("candidateCustom with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(candidateCustom, HttpStatus.OK);
	}
	
	
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
	
	
	

	/**
	 * createCandidateCustom method creates a candidate
	 * 
	 * @param candidate to be created
	 * @return a new ResponseEntity with the given status code
	 */
	@Transactional
	@PostMapping(value = "/")
	public ResponseEntity<CeReProAbstractEntity> createCandidateCustom(@ModelAttribute final RequestCandidateCustom candidateCustom) {
		logger.info("Creating candidateCustom : {}", candidateCustom);
		
		if (roleRepository.findByLevel(Role.JAVA_COURSE_CANDIDATE_LEVEL) == null) {

			return new ResponseEntity<>(
					new CustomErrorType("Unable to create new Candidate. Level " + Role.JAVA_COURSE_CANDIDATE_LEVEL + " is not present in database."),
					HttpStatus.CONFLICT);
		}
		User user = null ;
		Optional<User> optUser = userRepository.findByEmail(candidateCustom.getEmail()) ;
		if (optUser.isPresent()) {
			user = optUser.get();
			return new ResponseEntity<>(new CustomErrorType("Unable to create new user. A Candidate with email "
					+ candidateCustom.getEmail() + " already exist."), HttpStatus.CONFLICT);
		} else { 


			user = new User();
	
			user.setEmail(candidateCustom.getEmail());
			user.setFirstname(candidateCustom.getFirstname());
			user.setLastname(candidateCustom.getLastname());
			user.setNote(candidateCustom.getNote());
			System.out.println("Local date time: " + candidateCustom.getDateOfBirth());
			Date inputDate = candidateCustom.getDateOfBirth();
	
			if (inputDate != null) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
				String inputStringDate = formatter.format(inputDate);
				System.out.println("inputStringDate " + inputStringDate);
	
				if (inputStringDate.equals("11-nov-1111")) {
					user.setDateOfBirth(null);
				} else {
					LocalDate dateToDB = inputDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					user.setDateOfBirth(dateToDB);
				}
			}
			user.setRegdate(LocalDateTime.now());
			user.setRole(Role.JAVA_COURSE_CANDIDATE_LEVEL);
	
			//User userforCandidate = userRepository.save(user);
			userRepository.save(user);
		}

		if (candidateCustom.getImgpath() != null) {

			try {
				String[] nameIdData = saveUploadedFiles(candidateCustom.getFiles(), user.getId().toString());
				logger.info("nameIdData:" + nameIdData[0]);
				user.setImgpath(nameIdData[0]);
				//userforCandidate = userRepository.save(user);
				userRepository.save(user);
			} catch (IOException e) {
				logger.error("Error", e);
			}

		}

		Candidate candidate = new Candidate();

//		candidate.setUserId(userforCandidate.getId());
		candidate.setUserId(user.getId());
		candidate.setDomicileCity(candidateCustom.getDomicileCity());
//		candidate.setDomicileHouseNumber(candidateCustom.getDomicileHouseNumber());
//		candidate.setDomicileStreetName(candidateCustom.getDomicileStreetName());
		candidate.setStudyQualification(candidateCustom.getStudyQualification());
		candidate.setGraduate(candidateCustom.getGraduate());
		candidate.setHighGraduate(candidateCustom.getHighGraduate());
		candidate.setStillHighStudy(candidateCustom.getStillHighStudy());
		candidate.setMobile(candidateCustom.getMobile());
		candidate.setCourseCode(coursePageService.checkCoursePageCode(candidateCustom.getCourseCode()));
		//TODO: AGGIUNGERE UN CONTROLLO SUL COURSE CODE SE NON è PRESENTE NEL DATABASE METTERNE UNO DI DEFAULT --> candidatura generica!!!!
//		candidate.setNote(candidateCustom.getNote());
		candidate.setCandidacyDateTime(LocalDateTime.now());
		if (candidateCustom.getCvExternalPath() != null) {

			try {

				String[] nameIdData = saveUploadedFiles(candidateCustom.getFiles(), user.getId().toString());
				logger.info("nameIdData:" + nameIdData[1]);
				candidate.setCvExternalPath(nameIdData[1]);
			} catch (IOException e) {
				logger.error("Error", e);
			}
		}

		candidateService.insert(candidate);

		logger.info("END POST");
		return new ResponseEntity<>(candidate, HttpStatus.CREATED);

	}	

	// Save Files
	private String[] saveUploadedFiles(MultipartFile[] files, String userId) throws IOException {
		logger.info("saveUploadedFiles - START");
		// Make sure directory exists!
		File uploadImgDir = new File(IMG_DIR);
		uploadImgDir.mkdirs();
		File uploadCvDir = new File(CV_DIR);
		uploadCvDir.mkdirs();

		StringBuilder sb = new StringBuilder();
		logger.info("saveUploadedFiles - DEBUG 1");
		String[] nameIdData = new String[2];
		for (MultipartFile file : files) {

			if (file.isEmpty()) {
				continue;
			}
			String uploadFilePath = null;
			logger.info("saveUploadedFiles - DEBUG 2");
			StringTokenizer st = new StringTokenizer(file.getOriginalFilename(), ".");
			String name = st.nextToken();
			String extension = st.nextToken();
			String fileName = file.getOriginalFilename();

			if (fileName.endsWith("jpg")) {
				extension = ".jpg";
				uploadFilePath = IMG_DIR + File.pathSeparator + userId + file.getOriginalFilename();
				nameIdData[0] = userId + extension;
				logger.info("saveUploadedFiles - DEBUG 2.1 - nameIdData[0]: " + nameIdData[0]);
				uploadFilePath = IMG_DIR + File.separatorChar + nameIdData[0];
			}
			if (fileName.endsWith("jpeg")) {
				extension = ".jpeg";
				uploadFilePath = IMG_DIR + File.pathSeparator + userId + file.getOriginalFilename();
				nameIdData[0] = userId + extension;
				logger.info("saveUploadedFiles - DEBUG 2.2 - nameIdData[0]: " + nameIdData[0]);
				uploadFilePath = IMG_DIR + File.separatorChar + nameIdData[0];
			}
			if (fileName.endsWith("png")) {
				extension = ".png";
				uploadFilePath = IMG_DIR + File.pathSeparator + userId + file.getOriginalFilename();
				nameIdData[0] = userId + extension;
				logger.info("saveUploadedFiles - DEBUG 2.3 - nameIdData[0]: " + nameIdData[0]);
				uploadFilePath = IMG_DIR + File.separatorChar + nameIdData[0];
			}
			if (fileName.endsWith("docx")) {
				extension = ".docx";
				nameIdData[1] = userId + extension;
				logger.info("saveUploadedFiles - DEBUG 2.4 - nameIdData[1]: " + nameIdData[1]);
				uploadFilePath = CV_DIR + File.separatorChar + nameIdData[1];
			}
			if (fileName.endsWith("doc")) {
				extension = ".doc";
				nameIdData[1] = userId + extension;
				logger.info("saveUploadedFiles - DEBUG 2.5 - nameIdData[1]: " + nameIdData[1]);
				uploadFilePath = CV_DIR + File.separatorChar + nameIdData[1];
			}
			if (fileName.endsWith("pdf")) {
				extension = ".pdf";
				nameIdData[1] = userId + extension;
				logger.info("saveUploadedFiles - DEBUG 2.6 - nameIdData[1]: " + nameIdData[1]);
				uploadFilePath = CV_DIR + File.separatorChar + nameIdData[1];
			}

			logger.info("saveUploadedFiles - DEBUG 3 - uploadFilePath: " + uploadFilePath);
			byte[] bytes = file.getBytes();
			logger.info("saveUploadedFiles - DEBUG 3.5 - bytes.length: " + bytes.length);
			FileOutputStream fos = new FileOutputStream(uploadFilePath);
			fos.write(bytes);

			logger.info("saveUploadedFiles - DEBUG 5");
		}
		logger.info("saveUploadedFiles - DEBUG 6");
		return nameIdData;
	}

	/**
	 * updateCandidateCustom method updates a candidate custom
	 * 
	 * @param id   of the user to be updated
	 * @param user with the fields updated
	 * @return a new ResponseEntity with the given status code
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<CeReProAbstractEntity> updateCandidateCustom(@PathVariable("id") final Long id,
			@ModelAttribute final RequestUpdateCandidateCustom candidateCustom) {
		System.out.println("####### Updating candidateCustom : {}" + candidateCustom);

		ObjectMapper objectMapper = new ObjectMapper();

		objectMapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
		objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

//		boolean imgIsPresent = false;
//		boolean cvIsPresent = false;

		if (candidateCustom == null) {
			return new ResponseEntity<>(
					new CustomErrorType("Unable to update. candidateCustom with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		System.out.println("############ PUT ENTRATO: ");
		System.out.println(candidateCustom.getUserId());

//		CandidateCustom candidateCurrentCustom = candidateRepository.getSingleCustomCandidate(id);

		Optional<User> optUser = userRepository.findById(candidateCustom.getUserId());

		User currentUser = optUser.get();

		currentUser.setEmail(candidateCustom.getEmail());
		currentUser.setFirstname(candidateCustom.getFirstname());
		logger.info("candidateCustom.getFirstname() : {}", candidateCustom.getFirstname());
		currentUser.setLastname(candidateCustom.getLastname());
		currentUser.setNote(candidateCustom.getNote());
		Date inputDate = candidateCustom.getDateOfBirth();
		if (inputDate != null) {
			LocalDate dateToDB = inputDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			currentUser.setDateOfBirth(dateToDB);
		}

		System.out.println("OLDIMG: " + candidateCustom.getOldImg());
		System.out.println("NewIMG: " + candidateCustom.getImgpath());
		String oldImg = candidateCustom.getOldImg();
		String newImg = candidateCustom.getImgpath();

		// se si oldImg e si newImg
		if (newImg != null && !oldImg.equals("null") && !newImg.equals("null")) {
//			imgIsPresent = true;
//			String imgPath = "img/" +candidateCustom.getUserId()+candidateCustom.getImgpath();
			try {

				// save newImg
				String[] nameIdData = saveUploadedFiles(candidateCustom.getFiles(),
						candidateCustom.getUserId().toString());
				logger.info("nameIdData:" + nameIdData[0]);
				currentUser.setImgpath(nameIdData[0]);

				// delete oldImg
				String sPath = IMG_DIR + File.separatorChar + candidateCustom.getOldImg();
				Path path = Paths.get(sPath);
				Files.delete(path);

			} catch (IOException e) {
				logger.error("Error", e);
			}

		}

		// se no oldImg si newImg
		if (newImg != null && oldImg.equals("null") && !newImg.equals("null")) {
//			imgIsPresent = true;
//			String imgPath = "img/" +candidateCustom.getUserId()+ candidateCustom.getImgpath();

			try {

				// save firstNewImg
				String[] nameIdData = saveUploadedFiles(candidateCustom.getFiles(),
						candidateCustom.getUserId().toString());
				logger.info("nameIdData:" + nameIdData[0]);
				currentUser.setImgpath(nameIdData[0]);

			} catch (IOException e) {
				logger.error("Error", e);
			}

		}

		logger.info("Update currentUser : {}", currentUser.toString());
		userRepository.save(currentUser);

		Optional<Candidate> candidate = candidateService.getById(id);

		Candidate currentCandidate = candidate.get();
		currentCandidate.setDomicileCity(candidateCustom.getDomicileCity());
//		currentCandidate.setDomicileHouseNumber(candidateCustom.getDomicileHouseNumber());
//		currentCandidate.setDomicileStreetName(candidateCustom.getDomicileStreetName());
		currentCandidate.setStudyQualification(candidateCustom.getStudyQualification());
		currentCandidate.setGraduate(candidateCustom.getGraduate());
		currentCandidate.setHighGraduate(candidateCustom.getHighGraduate());
		currentCandidate.setStillHighStudy(candidateCustom.getStillHighStudy());
		currentCandidate.setMobile(candidateCustom.getMobile());
		currentCandidate.setCandidateStatesId(candidateCustom.getCandidateStatesId());
		
		//System.out.println("candidateStatesId: "+currentCandidate.getCandidateStatesId());
		//System.out.println("OLDCV: " + candidateCustom.getOldCV());
		//System.out.println("NewCV: " + candidateCustom.getCvExternalPath());
		String oldCV = candidateCustom.getOldCV();
		String newCV = candidateCustom.getCvExternalPath();

		// se si oldCV si newCV
		if (newCV != null && !oldCV.equals("null") && !newCV.equals("null")) {
//			cvIsPresent = true;
//			String cvPath = "curriculumvitae/" +candidateCustom.getUserId()+ candidateCustom.getCvExternalPath();

			try {

				// save newCv
				String[] nameIdData = saveUploadedFiles(candidateCustom.getFiles(),
						candidateCustom.getUserId().toString());
				logger.info("nameIdData:" + nameIdData[1]);
				currentCandidate.setCvExternalPath(nameIdData[1]);

				// delete oldCV
				String sPath = CV_DIR + File.separatorChar + candidateCustom.getOldCV();
				Path path = Paths.get(sPath);
				Files.delete(path);

			} catch (IOException e) {
				logger.error("Error", e);
			}

		}

		// se no oldCV si newCV
		if (newCV != null && oldCV.equals("null") && !newCV.equals("null")) {
//			cvIsPresent = true;
//			String cvPath = "curriculumvitae/" +candidateCustom.getUserId()+ candidateCustom.getCvExternalPath();

			try {

				// save firstCV
				String[] nameIdData = saveUploadedFiles(candidateCustom.getFiles(),
						candidateCustom.getUserId().toString());
				logger.info("nameIdData:" + nameIdData[1]);
				currentCandidate.setCvExternalPath(nameIdData[1]);

			} catch (IOException e) {
				logger.error("Error", e);
			}

		}
		
		//System.out.println("Prima di essere salvato candidateStatesId: "+currentCandidate.getCandidateStatesId());
		
		candidateService.update(currentCandidate);
        
		//System.out.println("Dopo essere salvato candidateStatesId: "+currentCandidate.getCandidateStatesId());
		//logger.info("Prima della fine della PUT.........................................................................................");
		logger.info("END PUT");
		return new ResponseEntity<>(candidateCustom, HttpStatus.OK);
	}

	/**
	 * deleteCandidate method deletes a candidate and relative user
	 * 
	 * @param id of the candidate to be canceled
	 * 
	 * 
	 * @return a new ResponseEntity with the given status code
	 */
	@Transactional
	@DeleteMapping("/{id}")
	public ResponseEntity<CeReProAbstractEntity> deleteCandidate(@PathVariable("id") final Long id) {
		logger.info("DELETE CANDIDATE CUSTOM - START");
		Optional<Candidate> candidateOpt = candidateService.getById(id);
		logger.info("candidateOpt " + candidateOpt);
		if (!candidateOpt.isPresent()) {
			logger.info("candidate not present " + candidateOpt);
			return new ResponseEntity<>(
					new CustomErrorType("Unable to delete. Candidate with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		Candidate currentCandidate = candidateOpt.get();
		logger.info("Deleting user with user id: " + currentCandidate.getUserId());
		logger.info("candidate id " + currentCandidate.getUserId());
		List<UserTokenSurvey> userTokenSurvey = userSurveyTokenRepository.findByUserId(currentCandidate.getUserId());
		logger.info("####################### LIST #########" + userTokenSurvey.toString());

//		Optional<SurveyReply> surveyReplyOpt = surveyReplyRepository.findById(id); 
//		logger.info("####### surveyReplyOpt  ########"+surveyReplyOpt);
		List<SurveyReply> surveyReply = surveyReplyRepository.findByUserId(currentCandidate.getUserId());
		logger.info("####################### LIST <SurveyReply> surveyReply #########" + surveyReply.toString());
		if (!userTokenSurvey.isEmpty()) {
			return new ResponseEntity<>(
					new CustomErrorType("Unable to delete. User with id " + id + " is userTokenSurvey referenced."),
					HttpStatus.CONFLICT); // code 409

		} else if (!surveyReply.isEmpty()) {
			return new ResponseEntity<>(new CustomErrorType("Non è possibile cancellare l'utente con id: " + id
					+ " in quanto esiste almeno un questionario associato ad esso."), HttpStatus.CONFLICT); // code 409

		} else {
			logger.info("####################### ");
			logger.info("Deleting candidate with id: " + id);
			candidateService.deleteById(id);

			Optional<User> userOpt = userRepository.findById(currentCandidate.getUserId());
			userRepository.deleteById(currentCandidate.getUserId());

			if (userOpt.get().getImgpath() != null) {

				String sPath = IMG_DIR + File.separatorChar + userOpt.get().getImgpath();
				Path path = Paths.get(sPath);
				try {
					Files.delete(path);
				} catch (IOException e) {
					logger.error("Error", e);
				}
			}

			if (currentCandidate.getCvExternalPath() != null) {

				String sPath = CV_DIR + File.separatorChar + currentCandidate.getCvExternalPath();
				Path path = Paths.get(sPath);
				try {
					Files.delete(path);
				} catch (IOException e) {
					logger.error("Error", e);
				}
			}

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

	}

	/**
	 * @author daniele
	 * 
	 *         createCandidateCustom method creates a candidate
	 *         
	 *         
	 *         USED BY REACT FRONTEND!!!!!!!!!!!!!!!!!
	 * 
	 * @param candidate to be created
	 * @return a new ResponseEntity with the given status code
	 */
	@Transactional
	@PostMapping(value = "/field/")
	public ResponseEntity<?> createFieldCandidateCustom(@RequestBody final CandidateCustom candidateCustom) {

		if (roleRepository.findByLevel(90) == null) {
			return new ResponseEntity<>(
					new CustomErrorType("Unable to create new Candidate. Level " + 90 + " is not present in database."),
					HttpStatus.CONFLICT);
		}

		if (userRepository.findByEmail(candidateCustom.getEmail()).isPresent()) {
			return new ResponseEntity<>(new CustomErrorType("Unable to create new candidate. A user with email "
					+ candidateCustom.getEmail() + " already exist."), HttpStatus.CONFLICT);
		} else {
			User user = new User();

			user.setEmail(candidateCustom.getEmail());
			user.setFirstname(candidateCustom.getFirstname());
			user.setLastname(candidateCustom.getLastname());
			user.setDateOfBirth(candidateCustom.getDateOfBirth());
			user.setRegdate(LocalDateTime.now());
			user.setRole(90);

			User userforCandidate = userRepository.save(user);
			Candidate candidate = new Candidate();

			candidate.setUserId(userforCandidate.getId());
			candidate.setDomicileCity(candidateCustom.getDomicileCity());
//			candidate.setDomicileHouseNumber(candidateCustom.getDomicileHouseNumber());
//			candidate.setDomicileStreetName(candidateCustom.getDomicileStreetName());
			candidate.setStudyQualification(candidateCustom.getStudyQualification());
			candidate.setGraduate(candidateCustom.getGraduate());
			candidate.setHighGraduate(candidateCustom.getHighGraduate());
			candidate.setStillHighStudy(candidateCustom.getStillHighStudy());
			candidate.setMobile(candidateCustom.getMobile());
			candidate.setCandidacyDateTime(LocalDateTime.now());
			candidateService.insert(candidate);

			return new ResponseEntity<>(candidate, HttpStatus.CREATED);
		}
	}

	/**
	 * @author daniele
	 * 
	 *         updateCandidateCustom method updates a candidate custom
	 *         
	 *         USED BY REACT FRONTEND!!!!!!!!!!!!!!!!!
	 * 
	 * @param id   of the user to be updated
	 * @param user with the fields updated
	 * @return a new ResponseEntity with the given status code
	 */
	@PutMapping(value = "/field/{id}")
	public ResponseEntity<CeReProAbstractEntity> updateFieldCandidateCustom(@PathVariable("id") final Long id,
			@RequestBody final CandidateCustom candidateCustom) {
		if (candidateCustom == null) {
			return new ResponseEntity<>(
					new CustomErrorType("Unable to update. candidateCustom with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		} else {

			Optional<User> optUser = userRepository.findById(candidateCustom.getUserId());

			if (!optUser.isPresent()) {
				return new ResponseEntity<>(
						new CustomErrorType(
								"Unable to update. user with id " + candidateCustom.getUserId() + " not found."),
						HttpStatus.NOT_FOUND);
			} else {
				User currentUser = optUser.get();

				currentUser.setEmail(candidateCustom.getEmail());
				currentUser.setFirstname(candidateCustom.getFirstname());
				currentUser.setLastname(candidateCustom.getLastname());
				currentUser.setDateOfBirth(candidateCustom.getDateOfBirth());

				userRepository.save(currentUser);

				Optional<Candidate> optCandidate = candidateService.getById(id);

				if (!optCandidate.isPresent()) {
					return new ResponseEntity<>(
							new CustomErrorType("Unable to update. candidate with id " + id + " not found."),
							HttpStatus.NOT_FOUND);
				} else {
					Candidate currentCandidate = optCandidate.get();
					currentCandidate.setDomicileCity(candidateCustom.getDomicileCity());
//					currentCandidate.setDomicileHouseNumber(candidateCustom.getDomicileHouseNumber());
//					currentCandidate.setDomicileStreetName(candidateCustom.getDomicileStreetName());
					currentCandidate.setStudyQualification(candidateCustom.getStudyQualification());
					currentCandidate.setGraduate(candidateCustom.getGraduate());
					currentCandidate.setHighGraduate(candidateCustom.getHighGraduate());
					currentCandidate.setStillHighStudy(candidateCustom.getStillHighStudy());
					currentCandidate.setMobile(candidateCustom.getMobile());
					//currentCandidate.setCandidateStatesId(candidateCustom.getCandidateStatesId());
					
					
					//System.out.println("Sei anche qua");
					candidateService.insert(currentCandidate);
					return new ResponseEntity<>(candidateCustom, HttpStatus.OK);
				}
			}
		}
	}

}