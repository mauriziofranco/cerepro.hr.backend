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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import centauri.academy.cerepro.persistence.entity.CeReProAbstractEntity;
import centauri.academy.cerepro.persistence.entity.CustomErrorType;
import centauri.academy.cerepro.persistence.entity.ItConsultant;
import centauri.academy.cerepro.persistence.entity.ItConsultantCustom;
import centauri.academy.cerepro.persistence.entity.Role;
import centauri.academy.cerepro.persistence.entity.SurveyReply;
import centauri.academy.cerepro.persistence.entity.User;
import centauri.academy.cerepro.persistence.entity.UserTokenSurvey;
import centauri.academy.cerepro.persistence.repository.RoleRepository;
import centauri.academy.cerepro.persistence.repository.SurveyInterviewRepository;
import centauri.academy.cerepro.persistence.repository.UserRepository;
import centauri.academy.cerepro.persistence.repository.itconsultant.ItConsultantRepository;
import centauri.academy.cerepro.persistence.repository.surveyreply.SurveyReplyRepository;
import centauri.academy.cerepro.persistence.repository.usersurveytoken.UserSurveyTokenRepository;
import centauri.academy.cerepro.rest.request.RequestItConsultantCustom;
import centauri.academy.cerepro.rest.request.RequestUpdateItConsultantCustom;
import centauri.academy.cerepro.rest.response.statistics.interviews.SurveyPieChartDataset;

/**
 * 
 * @author dario
 * @author joffre
 * @author daniele piccinni
 * @author m.franco@proximainformatica.com
 * @author Adela Batalan
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/itconsultantcustom")
public class ItConsultantCustomController {

	@Value("${app.folder.itconsultants.profile.img}")
	private String IMG_DIR;
	@Value("${app.folder.itconsultants.cv}")
	private String CV_DIR;

	public static final Logger logger = LoggerFactory.getLogger(ItConsultantCustomController.class);
	@Autowired
	private ItConsultantRepository itConsultantRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserSurveyTokenRepository userSurveyTokenRepository;
	@Autowired
	private SurveyReplyRepository surveyReplyRepository;
	@Autowired
	private SurveyInterviewRepository surveyInterviewRepository;
	/**
	 * getAllItConsultantCustom method gets all candidates custom
	 * 
	 * @return a new ResponseEntity with the given status code
	 */
	@GetMapping("/")
	public ResponseEntity<List<ItConsultantCustom>> getAllItConsultantCustom() {
		List<ItConsultantCustom> candidates = itConsultantRepository.getAllCustomItConsultants();
		if (candidates.isEmpty()) {
			return new ResponseEntity<List<ItConsultantCustom>>(HttpStatus.NO_CONTENT);
		} else
			return new ResponseEntity<List<ItConsultantCustom>>(candidates, HttpStatus.OK);
	}
	
	
//	@GetMapping("/statistics-itconsultant")
//	public ResponseEntity<List<SurveyInterview>> listAllSurveyInterviewForChart() {
//		List<SurveyInterview> si = surveyInterviewRepository.findAll();
//	
//		if (si.isEmpty()) {
//			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//		}
//		return new ResponseEntity<>(si, HttpStatus.OK);
	
//	}
	@GetMapping("/statistics-itconsultant")
	//@Query("SELECT DISTINCT e FROM surveyreplies e WHERE e.survey_id=5")
	public ResponseEntity<List<SurveyReply>> listAllSurveyReplyForChart() {		
		List<SurveyReply> sr = surveyReplyRepository.findAll();
		if (sr.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}		
		return new ResponseEntity<>(sr, HttpStatus.OK);
	
	}
	
	@GetMapping("/statistics-itconsultant2")
	public ResponseEntity<List<SurveyPieChartDataset>> listAllSurveyReplyForChart2() {		
//		List<SurveyReply> sr = surveyReplyRepository.findAll();
//		if (sr.isEmpty()) {
//			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//		}		
		ArrayList<SurveyPieChartDataset> ret = new ArrayList<SurveyPieChartDataset>();
		ret.add(new SurveyPieChartDataset());
		return new ResponseEntity<>(ret, HttpStatus.OK);
	
	}
	
	@GetMapping("/statistics-itconsultants/{surveyId}")
	public ResponseEntity<SurveyPieChartDataset> getSurveyRepliesForPieChart(
			@PathVariable("surveyId") final int surveyId) {	
		//TODO..... 
		return new ResponseEntity<>(new SurveyPieChartDataset(), HttpStatus.OK);
	
	}
	
	
	
	


	/******* PAGEABLE ********/

	@GetMapping("/paginated/{size}/{number}/")
	public ResponseEntity<Page<ItConsultantCustom>> getPaginatedItConsultant(@PathVariable("size") final int size,
			@PathVariable("number") final int number) {
		Page<ItConsultantCustom> cC = itConsultantRepository
				.getAllCustomItConsultantsPaginated(PageRequest.of(number, size, Sort.Direction.ASC, "id"));
		if (cC.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(cC, HttpStatus.OK);
	}

	/******** PAGEABLE *******/

	/**
	 * getItConsultantCustomById method gets a itConsultantCustom by id
	 * 
	 * @param id of the itConsultantCustom to be selected
	 * @return a new ResponseEntity with the given status code
	 */
	@GetMapping("/{id}")
	public ResponseEntity<CeReProAbstractEntity> getItConsultantCustomById(@PathVariable("id") final Long id) {
		ItConsultantCustom itConsultantCustom = itConsultantRepository.getSingleCustomItConsultant(id);
		if (itConsultantCustom == null) {
			return new ResponseEntity<>(new CustomErrorType("itConsultantCustom with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(itConsultantCustom, HttpStatus.OK);
	}

	/**
	 * createItConsultantCustom method creates a candidate
	 * 
	 * @param candidate to be created
	 * @return a new ResponseEntity with the given status code
	 */

	@Transactional
	@PostMapping(value = "/")
	public ResponseEntity<?> createItConsultantCustom(@Valid @RequestBody  @ModelAttribute final RequestItConsultantCustom itConsultantCustom) {
		logger.info("Creating itConsultantCustom : {}", itConsultantCustom);
//		boolean imgIsPresent = false;
//		boolean cvIsPresent = false;

		if (userRepository.findByEmail(itConsultantCustom.getEmail()).isPresent()) {
	
			
			return new ResponseEntity<>(
					new CustomErrorType("Unable to create new user. A ItConsultant with email "
					+ itConsultantCustom.getEmail() + " already exist."), HttpStatus.CONFLICT);
		}
		

		if (roleRepository.findByLevel(Role.ITCONSULTANT_LEVEL) == null) {

			return new ResponseEntity<>(
					new CustomErrorType("Unable to create new ItConsultant. Level " + 90 + " is not present in database."),
					HttpStatus.CONFLICT);
		}

		User user = new User();

		user.setEmail(itConsultantCustom.getEmail());
		user.setFirstname(itConsultantCustom.getFirstname());
		user.setLastname(itConsultantCustom.getLastname());
		System.out.println("Local date time: " + itConsultantCustom.getDateOfBirth());
		Date inputDate = itConsultantCustom.getDateOfBirth();
//		 new Date("Sat Nov 11 00:11:00 CET 1111");

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
		user.setRole(Role.ITCONSULTANT_LEVEL);

		User userforItConsultant = userRepository.save(user);

		if (itConsultantCustom.getImgpath() != null) {

			try {
				String[] nameIdData = saveUploadedFiles(itConsultantCustom.getFiles(), user.getId().toString());
				logger.info("nameIdData:" + nameIdData[0]);
				user.setImgpath(nameIdData[0]);
				userforItConsultant = userRepository.save(user);
			} catch (IOException e) {
				logger.error("Error", e);
			}

		}

		ItConsultant candidate = new ItConsultant();

		candidate.setUserId(userforItConsultant.getId());
		candidate.setDomicileCity(itConsultantCustom.getDomicileCity());
//		candidate.setDomicileHouseNumber(itConsultantCustom.getDomicileHouseNumber());
//		candidate.setDomicileStreetName(itConsultantCustom.getDomicileStreetName());
		candidate.setStudyQualification(itConsultantCustom.getStudyQualification());
		candidate.setGraduate(itConsultantCustom.getGraduate());
		candidate.setHighGraduate(itConsultantCustom.getHighGraduate());
		candidate.setStillHighStudy(itConsultantCustom.getStillHighStudy());
		candidate.setMobile(itConsultantCustom.getMobile());

		if (itConsultantCustom.getCvExternalPath() != null) {

			try {

				String[] nameIdData = saveUploadedFiles(itConsultantCustom.getFiles(), user.getId().toString());
				logger.info("nameIdData:" + nameIdData[1]);
				candidate.setCvExternalPath(nameIdData[1]);
			} catch (IOException e) {
				logger.error("Error", e);
			}
		}

		itConsultantRepository.save(candidate);

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
	 * updateItConsultantCustom method updates a candidate custom
	 * 
	 * @param id   of the user to be updated
	 * @param user with the fields updated
	 * @return a new ResponseEntity with the given status code
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<CeReProAbstractEntity> updateItConsultantCustom(@PathVariable("id") final Long id,
			@ModelAttribute final RequestUpdateItConsultantCustom itConsultantCustom) {
		System.out.println("####### Updating itConsultantCustom : {}" + itConsultantCustom);

		ObjectMapper objectMapper = new ObjectMapper();

		objectMapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
		objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

//		boolean imgIsPresent = false;
//		boolean cvIsPresent = false;

		if (itConsultantCustom == null) {
			return new ResponseEntity<>(
					new CustomErrorType("Unable to update. itConsultantCustom with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		System.out.println("############ PUT ENTRATO: ");
		System.out.println(itConsultantCustom.getUserId());

//		ItConsultantCustom candidateCurrentCustom = candidateRepository.getSingleCustomItConsultant(id);

		Optional<User> optUser = userRepository.findById(itConsultantCustom.getUserId());

		User currentUser = optUser.get();

		currentUser.setEmail(itConsultantCustom.getEmail());
		currentUser.setFirstname(itConsultantCustom.getFirstname());
		logger.info("itConsultantCustom.getFirstname() : {}", itConsultantCustom.getFirstname());
		currentUser.setLastname(itConsultantCustom.getLastname());
		Date inputDate = itConsultantCustom.getDateOfBirth();
		if (inputDate != null) {
			LocalDate dateToDB = inputDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			currentUser.setDateOfBirth(dateToDB);
		}

		System.out.println("OLDIMG: " + itConsultantCustom.getOldImg());
		System.out.println("NewIMG: " + itConsultantCustom.getImgpath());
		String oldImg = itConsultantCustom.getOldImg();
		String newImg = itConsultantCustom.getImgpath();

		// se si oldImg e si newImg
		if (newImg != null && !oldImg.equals("null") && !newImg.equals("null")) {
//			imgIsPresent = true;
//			String imgPath = "img/" +itConsultantCustom.getUserId()+itConsultantCustom.getImgpath();
			try {

				// save newImg
				String[] nameIdData = saveUploadedFiles(itConsultantCustom.getFiles(),
						itConsultantCustom.getUserId().toString());
				logger.info("nameIdData:" + nameIdData[0]);
				currentUser.setImgpath(nameIdData[0]);

				// delete oldImg
				String sPath = IMG_DIR + File.separatorChar + itConsultantCustom.getOldImg();
				Path path = Paths.get(sPath);
				Files.delete(path);

			} catch (IOException e) {
				logger.error("Error", e);
			}

		}

		// se no oldImg si newImg
		if (newImg != null && oldImg.equals("null") && !newImg.equals("null")) {
//			imgIsPresent = true;
//			String imgPath = "img/" +itConsultantCustom.getUserId()+ itConsultantCustom.getImgpath();

			try {

				// save firstNewImg
				String[] nameIdData = saveUploadedFiles(itConsultantCustom.getFiles(),
						itConsultantCustom.getUserId().toString());
				logger.info("nameIdData:" + nameIdData[0]);
				currentUser.setImgpath(nameIdData[0]);

			} catch (IOException e) {
				logger.error("Error", e);
			}

		}

		logger.info("Update currentUser : {}", currentUser.toString());
		userRepository.save(currentUser);

		Optional<ItConsultant> candidate = itConsultantRepository.findById(id);

		ItConsultant currentItConsultant = candidate.get();
		currentItConsultant.setDomicileCity(itConsultantCustom.getDomicileCity());
		currentItConsultant.setDomicileHouseNumber(itConsultantCustom.getDomicileHouseNumber());
		currentItConsultant.setDomicileStreetName(itConsultantCustom.getDomicileStreetName());
		currentItConsultant.setStudyQualification(itConsultantCustom.getStudyQualification());
		currentItConsultant.setGraduate(itConsultantCustom.getGraduate());
		currentItConsultant.setHighGraduate(itConsultantCustom.getHighGraduate());
		currentItConsultant.setStillHighStudy(itConsultantCustom.getStillHighStudy());
		currentItConsultant.setMobile(itConsultantCustom.getMobile());

		System.out.println("OLDCV: " + itConsultantCustom.getOldCV());
		System.out.println("NewCV: " + itConsultantCustom.getCvExternalPath());
		String oldCV = itConsultantCustom.getOldCV();
		String newCV = itConsultantCustom.getCvExternalPath();

		// se si oldCV si newCV
		if (newCV != null && !oldCV.equals("null") && !newCV.equals("null")) {
//			cvIsPresent = true;
//			String cvPath = "curriculumvitae/" +itConsultantCustom.getUserId()+ itConsultantCustom.getCvExternalPath();

			try {

				// save newCv
				String[] nameIdData = saveUploadedFiles(itConsultantCustom.getFiles(),
						itConsultantCustom.getUserId().toString());
				logger.info("nameIdData:" + nameIdData[1]);
				currentItConsultant.setCvExternalPath(nameIdData[1]);

				// delete oldCV
				String sPath = CV_DIR + File.separatorChar + itConsultantCustom.getOldCV();
				Path path = Paths.get(sPath);
				Files.delete(path);

			} catch (IOException e) {
				logger.error("Error", e);
			}

		}

		// se no oldCV si newCV
		if (newCV != null && oldCV.equals("null") && !newCV.equals("null")) {
//			cvIsPresent = true;
//			String cvPath = "curriculumvitae/" +itConsultantCustom.getUserId()+ itConsultantCustom.getCvExternalPath();

			try {

				// save firstCV
				String[] nameIdData = saveUploadedFiles(itConsultantCustom.getFiles(),
						itConsultantCustom.getUserId().toString());
				logger.info("nameIdData:" + nameIdData[1]);
				currentItConsultant.setCvExternalPath(nameIdData[1]);

			} catch (IOException e) {
				logger.error("Error", e);
			}

		}

		itConsultantRepository.save(currentItConsultant);

		logger.info("END PUT");
		return new ResponseEntity<>(itConsultantCustom, HttpStatus.OK);
	}

	/**
	 * deleteItConsultant method deletes a candidate and relative user
	 * 
	 * @param id of the candidate to be canceled
	 * 
	 * 
	 * @return a new ResponseEntity with the given status code
	 */
	@Transactional
	@DeleteMapping("/{id}")
	public ResponseEntity<CeReProAbstractEntity> deleteItConsultant(@PathVariable("id") final Long id) {
		logger.info("DELETE CANDIDATE CUSTOM - START");
		Optional<ItConsultant> candidateOpt = itConsultantRepository.findById(id);
		logger.info("candidateOpt " + candidateOpt);
		if (!candidateOpt.isPresent()) {
			logger.info("candidate not present " + candidateOpt);
			return new ResponseEntity<>(
					new CustomErrorType("Unable to delete. ItConsultant with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		ItConsultant currentItConsultant = candidateOpt.get();
		logger.info("Deleting user with user id: " + currentItConsultant.getUserId());
		logger.info("candidate id " + currentItConsultant.getUserId());
		List<UserTokenSurvey> userTokenSurvey = userSurveyTokenRepository.findByUserId(currentItConsultant.getUserId());
		logger.info("####################### LIST #########" + userTokenSurvey.toString());

//		Optional<SurveyReply> surveyReplyOpt = surveyReplyRepository.findById(id); 
//		logger.info("####### surveyReplyOpt  ########"+surveyReplyOpt);
		List<SurveyReply> surveyReply = surveyReplyRepository.findByUserId(currentItConsultant.getUserId());
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
			itConsultantRepository.deleteById(id);

			Optional<User> userOpt = userRepository.findById(currentItConsultant.getUserId());
			userRepository.deleteById(currentItConsultant.getUserId());

			if (userOpt.get().getImgpath() != null) {

				String sPath = IMG_DIR + File.separatorChar + userOpt.get().getImgpath();
				Path path = Paths.get(sPath);
				try {
					Files.delete(path);
				} catch (IOException e) {
					logger.error("Error", e);
				}
			}

			if (currentItConsultant.getCvExternalPath() != null) {

				String sPath = CV_DIR + File.separatorChar + currentItConsultant.getCvExternalPath();
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
	 *         createItConsultantCustom method creates a candidate
	 * 
	 * @param candidate to be created
	 * @return a new ResponseEntity with the given status code
	 */
	@Transactional
	@PostMapping(value = "/field/")
	public ResponseEntity<?> createFieldItConsultantCustom(@RequestBody final ItConsultantCustom itConsultantCustom) {

		if (roleRepository.findByLevel(Role.ITCONSULTANT_LEVEL) == null) {
			return new ResponseEntity<>(
					new CustomErrorType("Unable to create new ItConsultant. Level " + Role.ITCONSULTANT_LEVEL + " is not present in database."),
					HttpStatus.CONFLICT);
		}

		if (userRepository.findByEmail(itConsultantCustom.getEmail()).isPresent()) {
			return new ResponseEntity<>(new CustomErrorType("Unable to create new candidate. A user with email "
					+ itConsultantCustom.getEmail() + " already exist."), HttpStatus.CONFLICT);
		} else {
			User user = new User();

			user.setEmail(itConsultantCustom.getEmail());
			user.setFirstname(itConsultantCustom.getFirstname());
			user.setLastname(itConsultantCustom.getLastname());
			user.setDateOfBirth(itConsultantCustom.getDateOfBirth());
			user.setRegdate(LocalDateTime.now());
			user.setRole(Role.ITCONSULTANT_LEVEL);

			User userforItConsultant = userRepository.save(user);
			ItConsultant candidate = new ItConsultant();

			candidate.setUserId(userforItConsultant.getId());
			candidate.setDomicileCity(itConsultantCustom.getDomicileCity());
			candidate.setDomicileHouseNumber(itConsultantCustom.getDomicileHouseNumber());
			candidate.setDomicileStreetName(itConsultantCustom.getDomicileStreetName());
			candidate.setStudyQualification(itConsultantCustom.getStudyQualification());
			candidate.setGraduate(itConsultantCustom.getGraduate());
			candidate.setHighGraduate(itConsultantCustom.getHighGraduate());
			candidate.setStillHighStudy(itConsultantCustom.getStillHighStudy());
			candidate.setMobile(itConsultantCustom.getMobile());

			itConsultantRepository.save(candidate);

			return new ResponseEntity<>(candidate, HttpStatus.CREATED);
		}
	}

	/**
	 * @author daniele
	 * 
	 *         updateItConsultantCustom method updates a candidate custom
	 * 
	 * @param id   of the user to be updated
	 * @param user with the fields updated
	 * @return a new ResponseEntity with the given status code
	 */
	@PutMapping(value = "/field/{id}")
	public ResponseEntity<CeReProAbstractEntity> updateFieldItConsultantCustom(@PathVariable("id") final Long id,
			@RequestBody final ItConsultantCustom itConsultantCustom) {
		if (itConsultantCustom == null) {
			return new ResponseEntity<>(
					new CustomErrorType("Unable to update. itConsultantCustom with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		} else {

			Optional<User> optUser = userRepository.findById(itConsultantCustom.getUserId());

			if (!optUser.isPresent()) {
				return new ResponseEntity<>(
						new CustomErrorType(
								"Unable to update. user with id " + itConsultantCustom.getUserId() + " not found."),
						HttpStatus.NOT_FOUND);
			} else {
				User currentUser = optUser.get();

				currentUser.setEmail(itConsultantCustom.getEmail());
				currentUser.setFirstname(itConsultantCustom.getFirstname());
				currentUser.setLastname(itConsultantCustom.getLastname());
				currentUser.setDateOfBirth(itConsultantCustom.getDateOfBirth());

				userRepository.save(currentUser);

				Optional<ItConsultant> optItConsultant = itConsultantRepository.findById(id);

				if (!optItConsultant.isPresent()) {
					return new ResponseEntity<>(
							new CustomErrorType("Unable to update. candidate with id " + id + " not found."),
							HttpStatus.NOT_FOUND);
				} else {
					ItConsultant currentItConsultant = optItConsultant.get();
					currentItConsultant.setDomicileCity(itConsultantCustom.getDomicileCity());
					currentItConsultant.setDomicileHouseNumber(itConsultantCustom.getDomicileHouseNumber());
					currentItConsultant.setDomicileStreetName(itConsultantCustom.getDomicileStreetName());
					currentItConsultant.setStudyQualification(itConsultantCustom.getStudyQualification());
					currentItConsultant.setGraduate(itConsultantCustom.getGraduate());
					currentItConsultant.setHighGraduate(itConsultantCustom.getHighGraduate());
					currentItConsultant.setStillHighStudy(itConsultantCustom.getStillHighStudy());
					currentItConsultant.setMobile(itConsultantCustom.getMobile());

					itConsultantRepository.save(currentItConsultant);
					return new ResponseEntity<>(itConsultantCustom, HttpStatus.OK);
				}
			}
		}
	}

}