/**
 * 
 */
package centauri.academy.cerepro.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import centauri.academy.cerepro.persistence.entity.Candidate;
import centauri.academy.cerepro.persistence.entity.CandidateStates;
import centauri.academy.cerepro.persistence.entity.custom.CandidateCustom;
import centauri.academy.cerepro.persistence.entity.custom.ListedCandidateCustom;
import centauri.academy.cerepro.persistence.repository.candidate.CandidateRepository;
import centauri.academy.cerepro.rest.request.candidate.RequestCandidateCustom;
import centauri.academy.cerepro.rest.request.candidate.RequestUpdateCandidateCustom;
import centauri.academy.cerepro.service.exception.CandidateNotFoundException;

/**
 * 
 * 
 * @author maurizio.franco@ymail.com
 *
 */
@Service 
public class CandidateService {
	
	@Value("${app.folder.candidate.profile.img}")
	public String IMG_DIR;
	@Value("${app.folder.candidate.cv}")
	public String CV_DIR;	
	
	public static final Logger logger = LoggerFactory.getLogger(CandidateService.class);
	
	@Autowired
	private CandidateRepository candidateRepository;
	@Autowired
	private CoursePageService coursePageService ;
	/**
	 * Gets all candidates entries from table
	 * 
	 * @return List<Candidate>
	 */
	public List<Candidate> getAll () {
		logger.info("CandidateService.getAll - START");
		return candidateRepository.findAll();
	}
	
	/**
	 * Try to delete all instances into candidate table
	 * 
	 * @return long
	 */
	public void deleteAll () {
		logger.info("deleteAll - START");
		candidateRepository.deleteAll();
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
	 * Gets all candidates custom(Candidates + Users) in paginated version list, filtering by course code field
	 * 
	 * @param Pageable p, page info
	 * @param String courseCode, string value for course code field
	 * @return Page<ListedCandidateCustom>
	 */
	public Page<ListedCandidateCustom> getAllCustomPaginatedByCourseCode (Pageable p, String courseCode) {
		logger.info("CandidateService.getAllCustomPaginatedByCourseCode - START - with pageable info {0} and course code: {1}", p, courseCode);
		return candidateRepository.getAllCustomCandidatesPaginatedByCourseCode(p, courseCode);
	}
	
	/**
	 * Gets all candidates custom(Candidates + Users) by course code field
	 * 
	 * @return List<CandidateCustom>
	 */
	public List<ListedCandidateCustom> getAllByCourseCode (String courseCode) {
		logger.info("CandidateService.getAllByCourseCode - START with given course code {}", courseCode);
		List<ListedCandidateCustom> items = candidateRepository.findByCourseCode(courseCode);
		return items;
	}
	
	/**
	 * Insert new candidate entity 
	 */
	public Candidate insert (Candidate c) {
		logger.info("insert() - START - with given candidate {}", c);
//		c.setCandidacyDateTime(LocalDateTime.now());
//		c.setCandidateStatesId(CandidateStates.DEFAULT_INSERTING_STATUS_CODE);
//		logger.info("CandidateService.insert DEBUG with given candidate {}", c);
		return candidateRepository.save(c);
	}
	
	/**
	 * Update candidate entity 
	 * 
	 */
	public Candidate update (Candidate c) {
		logger.info("update() - START -with given candidate {}", c);
		return candidateRepository.save(c);
	}
	
	/**
	 * Retrieve candidate by id
	 * 
	 * @param id of the candidate to retrieve
	 * @return candidate entity
	 */
	public Optional<Candidate> getById(long id) {
		logger.info("getById() - START with given id {}", id);
		return candidateRepository.findById(id);
		
	}
	
	/**
	 * Retrieve candidate by id
	 * 
	 * @param id of the candidate to retrieve
	 * @return candidate entity
	 */
	public CandidateCustom getCustomById(long id) {
		logger.info("getCustomById() - START - with given id {}", id);
		CandidateCustom candiateToReturn = null ;
        try {
        	candiateToReturn = candidateRepository.getSingleCustomCandidate(id);
        } catch (Exception e) {
        	logger.info(e.getMessage());
			logger.info("getCustomById() - INFO - No candidate found for id {}", id);
        }
		return candiateToReturn ;
		
	}
	
	/**
	 * Delete candidate by id
	 * 
	 * @param id of the candidate to delete
	 */
	public void deleteById(long id) {
		logger.info("CandidateService.deleteById START with given id {}", id);
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
	
	public long getRegisteredCandidatesInDate(LocalDate date) {
		logger.info("getRegisteredCandidatesInDate - START");
		LocalDateTime start = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 0, 0, 0);
		LocalDateTime end = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 23, 59, 59);
		long count = candidateRepository.getCandidateCountWithRegdateInPeriod(start, end);
	
		logger.info("getRegisteredCandidatesInDate - END - with registered candidates: " + count);
		return count;
	}
	
	/**
	 * 
	 * Returns number of candidates registered from days ago number received.
	 * 
	 * @param daysAgo
	 * @return
	 * @author orlando
	 * @author m.franco@proximanetwork.it
	 */
	public long getRegisteredCandidatesFromDaysAgo(long daysAgo) {
		logger.info("getRegisteredCandidatesFromDaysAgo - START - daysAgo: " + daysAgo);
		long count = 0;
		LocalDate date = LocalDate.now();
		//User currentUser;
		for(int i = 0; i < daysAgo; i++) {
			LocalDate day = date.minusDays(i);
			count += getRegisteredCandidatesInDate(day);		
		}
		logger.info("getRegisteredCandidatesFromDaysAgo - END - return: " + count);
		return count;
	}
	
	/**
	 * Invoked by CandidateCustomController.insert, provides to upload image and cv files and fill a new Candidate entity, 
	 * than call CandidateService.insert method to persist the entity. 
	 * 
	 * @return Candidate, inserted entity
	 */
	public Candidate createNewCandidate (RequestCandidateCustom requestCandidateCustom) {
		logger.info("createNewCandidate - START - with requestCandidateCustom {}", requestCandidateCustom);
		
		Candidate candidateToInsert = new Candidate () ;
		candidateToInsert.setUserId(requestCandidateCustom.getInsertedBy());
		candidateToInsert.setInsertedBy(requestCandidateCustom.getInsertedBy());
		candidateToInsert.setDomicileCity(requestCandidateCustom.getDomicileCity());
		candidateToInsert.setStudyQualification(requestCandidateCustom.getStudyQualification());
		candidateToInsert.setGraduate(requestCandidateCustom.getGraduate());
		candidateToInsert.setHighGraduate(requestCandidateCustom.getHighGraduate());
		candidateToInsert.setStillHighStudy(requestCandidateCustom.getStillHighStudy());
		candidateToInsert.setMobile(requestCandidateCustom.getMobile());
		candidateToInsert.setEmail(requestCandidateCustom.getEmail());
		candidateToInsert.setFirstname(requestCandidateCustom.getFirstname());
		candidateToInsert.setLastname(requestCandidateCustom.getLastname());
		candidateToInsert.setTechnicalNote(requestCandidateCustom.getNote());
		candidateToInsert.setRegdate(LocalDateTime.now());
		candidateToInsert.setCourseCode(coursePageService.checkCoursePageCode(requestCandidateCustom.getCourseCode()));
		candidateToInsert.setCandidacyDateTime(LocalDateTime.now());
		candidateToInsert.setCandidateStatusCode(CandidateStates.DEFAULT_INSERTING_STATUS_CODE);
		if (requestCandidateCustom.getCvExternalPath() != null) {

			try {
				logger.info("createNewCandidate - DEBUG - inserting cv document file");
				String cvExternalPathFileName = uploadFile(requestCandidateCustom.getFiles()[1], buildNewFileName(requestCandidateCustom.getEmail()));
				logger.info("createNewCandidate - DEBUG - cvExternalPathFileName:" + cvExternalPathFileName);
				candidateToInsert.setCvExternalPath(cvExternalPathFileName);
			} catch (Exception e) {
				logger.error("Error", e);
			}
		}
		if (requestCandidateCustom.getImgpath() != null) {

			try {
				logger.info("createNewCandidate - DEBUG - inserting profile image file");
				String profileImgFileName = uploadFile(requestCandidateCustom.getFiles()[0], buildNewFileName(requestCandidateCustom.getEmail()));
				logger.info("createNewCandidate - DEBUG - profileImgFileName:" + profileImgFileName);
				candidateToInsert.setImgpath(profileImgFileName);
			} catch (Exception e) {
				logger.error("Error", e);
			}

		}
		return insert(candidateToInsert);

	}
	
	/*
	 * Provide to save file
	 */
	private String uploadFile (MultipartFile file, String candidateFileName) throws IOException {
		logger.info("uploadFile - START");

		StringBuilder sb = new StringBuilder();
		String fileNameToReturn = null ;

//		if (file.isEmpty()) {
//			continue;
//		}
		String uploadFilePath = null;
		logger.info("uploadFile - DEBUG 2 - file.getOriginalFilename(): " + file.getOriginalFilename());
		StringTokenizer st = new StringTokenizer(file.getOriginalFilename(), ".");
		String name = st.nextToken();
		String extension = st.nextToken();
		String fileName = file.getOriginalFilename();
		logger.info("uploadFile - DEBUG 2.5 - extension: " + extension);
		fileNameToReturn = candidateFileName + extension;
		uploadFilePath = File.separatorChar + fileNameToReturn;
		if ((fileName.endsWith("jpg"))||(fileName.endsWith("jpeg"))||(fileName.endsWith("png"))||
				(fileName.endsWith("gif")) ){
		
//		if (fileName.endsWith("jpg")) {
//			extension = ".jpg";
//			uploadFilePath = IMG_DIR + File.pathSeparator + candidateFileName + file.getOriginalFilename();
//			nameIdData[0] = candidateFileName + extension;
//			logger.info("uploadFile - DEBUG 2.1 - nameIdData[0]: " + nameIdData[0]);
			uploadFilePath = IMG_DIR + uploadFilePath ;
//		} else if (fileName.endsWith("jpeg")) {
//			extension = ".jpeg";
//			uploadFilePath = IMG_DIR + File.pathSeparator + candidateFileName + file.getOriginalFilename();
//			nameIdData[0] = candidateFileName + extension;
//			logger.info("uploadFile - DEBUG 2.2 - nameIdData[0]: " + nameIdData[0]);
//			uploadFilePath = IMG_DIR + uploadFilePath;
//		} else if (fileName.endsWith("png")) {
//			extension = ".png";
//			uploadFilePath = IMG_DIR + File.pathSeparator + candidateFileName + file.getOriginalFilename();
//			nameIdData[0] = candidateFileName + extension;
//			logger.info("uploadFile - DEBUG 2.3 - nameIdData[0]: " + nameIdData[0]);
//			uploadFilePath = IMG_DIR + uploadFilePath;
		} else if ((fileName.endsWith("docx")) || (fileName.endsWith("doc")) || (fileName.endsWith("pdf")) || (fileName.endsWith("odt"))) {
//			extension = ".docx";
//			nameIdData[1] = candidateFileName + extension;
//			logger.info("uploadFile - DEBUG 2.4 - nameIdData[1]: " + nameIdData[1]);
//			uploadFilePath = CV_DIR + uploadFilePath;
//		} else if (fileName.endsWith("doc")) {
////			extension = ".doc";
////			nameIdData[1] = candidateFileName + extension;
////			logger.info("uploadFile - DEBUG 2.5 - nameIdData[1]: " + nameIdData[1]);
//			uploadFilePath = CV_DIR + uploadFilePath;
//		} else if (fileName.endsWith("pdf")) {
////			extension = ".pdf";
////			nameIdData[1] = candidateFileName + extension;
////			logger.info("uploadFile - DEBUG 2.6 - nameIdData[1]: " + nameIdData[1]);
			uploadFilePath = CV_DIR + uploadFilePath;
		}
		
//		logger.info("uploadFile - DEBUG 2 - nameIdData[0]: " + nameIdData[0]);
		logger.info("uploadFile - DEBUG 3 - uploadFilePath: " + uploadFilePath);
		byte[] bytes = file.getBytes();
		logger.info("uploadFile - DEBUG 3.5 - bytes.length: " + bytes.length);
		FileOutputStream fos = new FileOutputStream(uploadFilePath);
		fos.write(bytes);

		logger.info("uploadFile - END - fileNameToReturn: {}", fileNameToReturn);
		return fileNameToReturn;
	}
	/*
	 * Provide to save file
	 */
	private String[] uploadFile(MultipartFile[] files, String candidateFileName) throws IOException {
		logger.info("uploadFile - START");
		// Make sure directory exists!
		File uploadImgDir = new File(IMG_DIR);
		uploadImgDir.mkdirs();
		File uploadCvDir = new File(CV_DIR);
		uploadCvDir.mkdirs();

		StringBuilder sb = new StringBuilder();
		logger.info("uploadFile - DEBUG 1");
		String[] nameIdData = new String[2];
		for (MultipartFile file : files) {

			if (file.isEmpty()) {
				continue;
			}
			String uploadFilePath = null;
			logger.info("uploadFile - DEBUG 2 - file.getOriginalFilename(): " + file.getOriginalFilename());
			StringTokenizer st = new StringTokenizer(file.getOriginalFilename(), ".");
			String name = st.nextToken();
			String extension = st.nextToken();
			String fileName = file.getOriginalFilename();

			if (fileName.endsWith("jpg")) {
				extension = ".jpg";
				uploadFilePath = IMG_DIR + File.pathSeparator + candidateFileName + file.getOriginalFilename();
				nameIdData[0] = candidateFileName + extension;
				logger.info("uploadFile - DEBUG 2.1 - nameIdData[0]: " + nameIdData[0]);
				uploadFilePath = IMG_DIR + File.separatorChar + nameIdData[0];
			} 
			if (fileName.endsWith("jpeg")) {
				extension = ".jpeg";
				uploadFilePath = IMG_DIR + File.pathSeparator + candidateFileName + file.getOriginalFilename();
				nameIdData[0] = candidateFileName + extension;
				logger.info("uploadFile - DEBUG 2.2 - nameIdData[0]: " + nameIdData[0]);
				uploadFilePath = IMG_DIR + File.separatorChar + nameIdData[0];
			}
			if (fileName.endsWith("png")) {
				extension = ".png";
				uploadFilePath = IMG_DIR + File.pathSeparator + candidateFileName + file.getOriginalFilename();
				nameIdData[0] = candidateFileName + extension;
				logger.info("uploadFile - DEBUG 2.3 - nameIdData[0]: " + nameIdData[0]);
				uploadFilePath = IMG_DIR + File.separatorChar + nameIdData[0];
			}
			if (fileName.endsWith("docx")) {
				extension = ".docx";
				nameIdData[1] = candidateFileName + extension;
				logger.info("uploadFile - DEBUG 2.4 - nameIdData[1]: " + nameIdData[1]);
				uploadFilePath = CV_DIR + File.separatorChar + nameIdData[1];
			}
			if (fileName.endsWith("doc")) {
				extension = ".doc";
				nameIdData[1] = candidateFileName + extension;
				logger.info("uploadFile - DEBUG 2.5 - nameIdData[1]: " + nameIdData[1]);
				uploadFilePath = CV_DIR + File.separatorChar + nameIdData[1];
			}
			if (fileName.endsWith("pdf")) {
				extension = ".pdf";
				nameIdData[1] = candidateFileName + extension;
				logger.info("uploadFile - DEBUG 2.6 - nameIdData[1]: " + nameIdData[1]);
				uploadFilePath = CV_DIR + File.separatorChar + nameIdData[1];
			}

			logger.info("uploadFile - DEBUG 3 - uploadFilePath: " + uploadFilePath);
			byte[] bytes = file.getBytes();
			logger.info("uploadFile - DEBUG 3.5 - bytes.length: " + bytes.length);
			FileOutputStream fos = new FileOutputStream(uploadFilePath);
			fos.write(bytes);

			logger.info("uploadFile - DEBUG 5");
		}
		logger.info("uploadFile - END - nameIdData: {}", Arrays.toString(nameIdData));
		return nameIdData;
	}
	
	
	/**
	 * Invoked by CandidateCustomController.update, provides retrieve existent
	 * entity, uploads new image and new cv files, and fill the old entity with new
	 * properties, than call CandidateService.update method to persist the new
	 * entity.
	 * 
	 * @param id,                long number id of candidate to update
	 * @param candidateToUpdate, RequestUpdateCandidateCustom instance that contains
	 *                           new parameters to update.
	 * 
	 * @return Candidate, updated entity
	 */
	public Candidate updateCandidate(Long id, RequestUpdateCandidateCustom requestCandidateToUpdate) throws CandidateNotFoundException {
		logger.info("updateCandidate - START - with requestCandidateToUpdate {}", requestCandidateToUpdate);

		Optional<Candidate> optCandidate = getById(id);
		if (optCandidate.isEmpty()) {
			throw new CandidateNotFoundException("User not found, during candidate updating process.");
		} else {// optCandidate.isPresent

			Candidate candidateToUpdate = optCandidate.get();
//			Candidate candidateToUpdate = new Candidate();
//			candidateToUpdate.setUserId(requestCandidateToUpdate.getInsertedBy());
//			candidateToUpdate.setInsertedBy(requestCandidateToUpdate.getInsertedBy());
			candidateToUpdate.setDomicileCity(requestCandidateToUpdate.getDomicileCity());
			candidateToUpdate.setStudyQualification(requestCandidateToUpdate.getStudyQualification());
			candidateToUpdate.setGraduate(requestCandidateToUpdate.getGraduate());
			candidateToUpdate.setHighGraduate(requestCandidateToUpdate.getHighGraduate());
			candidateToUpdate.setStillHighStudy(requestCandidateToUpdate.getStillHighStudy());
			candidateToUpdate.setMobile(requestCandidateToUpdate.getMobile());
			candidateToUpdate.setEmail(requestCandidateToUpdate.getEmail());
			candidateToUpdate.setFirstname(requestCandidateToUpdate.getFirstname());
			candidateToUpdate.setLastname(requestCandidateToUpdate.getLastname());
			candidateToUpdate.setTechnicalNote(requestCandidateToUpdate.getNote());
			candidateToUpdate.setCandidateStatusCode(requestCandidateToUpdate.getCandidateStatusCode());
			candidateToUpdate.setCourseCode(requestCandidateToUpdate.getPositionCode());
//			candidateToUpdate.setRegdate(LocalDateTime.now());
			Date inputDate = requestCandidateToUpdate.getDateOfBirth();
			if (inputDate != null) {
				LocalDate dateToDB = inputDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				candidateToUpdate.setDateOfBirth(dateToDB);
			}
			//
			//UPDATE CV - START
			//
			String oldCV = requestCandidateToUpdate.getOldCV();
			String newCV = requestCandidateToUpdate.getCvExternalPath();
			/*
			// se si oldCV si newCV
			if (newCV != null && !oldCV.equals("null") && !newCV.equals("null")) {
				try {
					// save newCv
					String[] nameIdData = uploadFile(requestCandidateToUpdate.getFiles(), ""+requestCandidateToUpdate.getEmail().hashCode());
					logger.info("nameIdData:" + nameIdData[1]);
					candidateToUpdate.setCvExternalPath(nameIdData[1]);
					// delete oldCV
					String sPath = CV_DIR + File.separatorChar + requestCandidateToUpdate.getOldCV();
					Path path = Paths.get(sPath);
					Files.delete(path);
				} catch (IOException e) {
					logger.error("Error", e);
				}
			}

			// se no oldCV si newCV
			if (newCV != null && oldCV.equals("null") && !newCV.equals("null")) {
//				cvIsPresent = true;
//				String cvPath = "curriculumvitae/" +candidateCustom.getUserId()+ candidateCustom.getCvExternalPath();
				try {
					// save firstCV
					String[] nameIdData = uploadFile(requestCandidateToUpdate.getFiles(),
							""+requestCandidateToUpdate.getEmail().hashCode());
					logger.info("nameIdData:" + nameIdData[1]);
					candidateToUpdate.setCvExternalPath(nameIdData[1]);

				} catch (IOException e) {
					logger.error("Error", e);
				}

			}
			*/
			if (newCV != null && !newCV.equals("null")) {
				if (oldCV != null && !oldCV.equals("null")) {
					String filePathToDelete = CV_DIR + File.separatorChar + requestCandidateToUpdate.getOldCV();
					try {						
						Path path = Paths.get(filePathToDelete);
						Files.delete(path);
					} catch (IOException e) {
						logger.error("Error in deleting old cv file: {}", filePathToDelete,  e);
					}
				}
				try {
					String[] nameIdData = uploadFile(requestCandidateToUpdate.getFiles(), "" + buildNewFileName (requestCandidateToUpdate.getEmail()));
					logger.info("nameIdData:" + nameIdData[1]);
					candidateToUpdate.setCvExternalPath(nameIdData[1]);

				} catch (IOException e) {
					logger.error("Error", e);
				}

			}
			
			//
			//UPDATE CV - END
			//
			
			//
			//UPDATE PROFILE IMG - START
			//
			String oldImg = requestCandidateToUpdate.getOldImg();
			String newImg = requestCandidateToUpdate.getImgpath();

			// se si oldImg e si newImg
			if (newImg != null && !oldImg.equals("null") && !newImg.equals("null")) {
//				imgIsPresent = true;
//				String imgPath = "img/" +candidateCustom.getUserId()+candidateCustom.getImgpath();
				try {

					// save newImg
					String[] nameIdData = uploadFile(requestCandidateToUpdate.getFiles(), buildNewFileName(requestCandidateToUpdate.getEmail()));
					logger.info("nameIdData:" + nameIdData[0]);
					candidateToUpdate.setImgpath(nameIdData[0]);

					// delete oldImg
					//String sPath = IMG_DIR + File.separatorChar + requestCandidateToUpdate.getOldImg();
					String sPath = IMG_DIR + File.separatorChar + oldImg;
					Path path = Paths.get(sPath);
					Files.delete(path);

				} catch (IOException e) {
					logger.error("Error", e);
				}

			}

			// se no oldImg si newImg
			if (newImg != null && oldImg.equals("null") && !newImg.equals("null")) {
//				imgIsPresent = true;
//				String imgPath = "img/" +candidateCustom.getUserId()+ candidateCustom.getImgpath();

				try {

					// save firstNewImg
					String[] nameIdData = uploadFile(requestCandidateToUpdate.getFiles(), buildNewFileName(requestCandidateToUpdate.getEmail()));
					logger.info("nameIdData:" + nameIdData[0]);
					candidateToUpdate.setImgpath(nameIdData[0]);

				} catch (IOException e) {
					logger.error("Error", e);
				}

			}
			//
			// UPDATE PROFILE IMG - END
			//
			
			
			
			return update(candidateToUpdate);

		}
	}
	
	private String buildNewFileName (String email) {
		String returnName = email ;
		returnName = returnName.hashCode() + "-" + new Random().nextInt();
		return returnName ;
	}
	
	/**
	 * Invoked by CandidateCustomController.deleteCandidate method, provides retrieve existent
	 * entity, delete profile image and profile cv files, than call CandidateService.delete method 
	 * to delete entity from table.
	 * 
	 * @param id,                long number id of candidate to update
	 * 
	 */
	public void deleteCandidate(long id) throws CandidateNotFoundException {
		logger.info("deleteCandidate - START - with id {}", id);

		Optional<Candidate> optCandidate = getById(id);
		if (optCandidate.isEmpty()) {
			throw new CandidateNotFoundException("User not found, during candidate updating process.");
		} else {
			Candidate candidateToDelete = optCandidate.get();
			String oldCV = candidateToDelete.getCvExternalPath();
			
			if (oldCV != null && !oldCV.equals("null")) {
				String filePathToDelete = CV_DIR + File.separatorChar + oldCV;
				try {						
					Path path = Paths.get(filePathToDelete);
					Files.delete(path);
				} catch (NoSuchFileException e) {
					logger.error("File non trovato. Non è stato possibile cancellarlo. {}", filePathToDelete);
				} catch (Exception e) {
					logger.error("Error in deleting old cv file: {}", filePathToDelete,  e);
				}
			}

			String oldImg = candidateToDelete.getImgpath();
			if (oldImg != null && !oldImg.equals("null")) {
				String sPath = null ;
				try {
					sPath = IMG_DIR + File.separatorChar + oldImg;
					Path path = Paths.get(sPath);
					Files.delete(path);

				} catch (NoSuchFileException e) {
					logger.error("File non trovato. Non è stato possibile cancellarlo. {}", sPath);
				} catch (Exception e) {
					logger.error("Error", e);
				}

			}
			
			
			
			

		}
		deleteById(id);
	}
}
