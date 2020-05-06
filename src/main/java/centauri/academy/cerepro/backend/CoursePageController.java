package centauri.academy.cerepro.backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
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
import centauri.academy.cerepro.persistence.entity.CoursePage;
import centauri.academy.cerepro.persistence.entity.custom.CustomErrorType;
import centauri.academy.cerepro.persistence.repository.CoursePageRepository;

/**
 * 
 * 
 * @author Traian Cojocariu
 * @author Orlando Platì
 * @author Sebastiano Varchetta
 *
 */

@RestController
@RequestMapping("/api/v1/coursepage")
public class CoursePageController {

	final String FINE_INDEX = "\n</body>\r\n" + "</html>\r\n" + "";
//	static final String START_MESSAGE_BODY="<!DOCTYPE html>\r\n" + 
//			"<html lang=\"it\" ng-app=\"candidating\">\r\n" + 
//			"<head>\r\n" + 
//			"\r\n" + 
//			"<meta charset=\"utf-8\">\r\n" + 
//			"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\r\n" + 
//			"    <meta name=\"author\" content=\"m.franco@proximainformatica.com\">\r\n" + 
//			"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n" + 
//			"	<meta charset=\"UTF-8\">\r\n" + 
//			"	<title>Candidati - Corso Java - Proxima Informatica</title>\r\n" + 
//			"	<meta name=\"description\"\r\n" + 
//			"		content=\"Corso Java gratuito professionalizzante su Roma e Milano. A fine corso contratto a tempo indeterminato (placement 70%). Invia subito il tuo cv!\" />	\r\n" + 
//			"	<link rel=\"icon\" href=\"http://www.proximainformatica.com/wp-content/uploads/2017/09/cropped-favicon-fb-proxima-3-32x32.png\" sizes=\"32x32\" />\r\n" + 
//			"	<link rel=\"icon\" href=\"http://www.proximainformatica.com/wp-content/uploads/2017/09/cropped-favicon-fb-proxima-3-192x192.png\" sizes=\"192x192\" />\r\n" + 
//			"	<link rel=\"apple-touch-icon-precomposed\" href=\"http://www.proximainformatica.com/wp-content/uploads/2017/09/cropped-favicon-fb-proxima-3-180x180.png\" />\r\n" + 
//			"    <link href=\"https://getbootstrap.com/docs/4.1/dist/css/bootstrap.min.css\" rel=\"stylesheet\">\r\n" + 
//			"	<link rel=\"stylesheet\" type=\"text/css\" href=\"/centauri/candidati/css/notice.css\">\r\n" + 
//			"	<link rel=\"stylesheet\" type=\"text/css\" href=\"/centauri/candidati/css/style.css\">	\r\n" + 
//			"	<link rel=\"stylesheet\" type=\"text/css\" href=\"/centauri/candidati/css/candidateregistration.css\">\r\n" + 
//			"</head>\r\n" + 
//			"<body style=\"margin-top: 30px;\">\r\n" + 
//			"\r\n" + 
//			"	<nav class=\"navbar navbar-expand-md navbar-dark bg-dark fixed-top\">\r\n" + 
//			"      <a class=\"navbar-brand\" href=\"#\">\r\n" + 
//			"          <img src=\"http://www.proximainformatica.com/wp-content/uploads/2017/11/LOGO-PROXIMA-ROSSO-white-2017.png\"\r\n" + 
//			" 				 title=\"proximainformatica\" id=\"headerlogoproxima\">\r\n" + 
//			"      </a>\r\n" + 
//			"      <button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarsExampleDefault\" aria-controls=\"navbarsExampleDefault\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\r\n" + 
//			"        <span class=\"navbar-toggler-icon\"></span>\r\n" + 
//			"      </button>\r\n" + 
//			"\r\n" + 
//			"    </nav>\r\n" + 
//			"\r\n" + 
//			"\r\n" + 
//			"\r\n" + 
//			"	<div class=\"starter-template\" ng-view></div>\r\n" + 
//			"    <footer>\r\n" + 
//			"        <img src=\"/centauri/common/img/logo/footer/centauriacademy.png\" id=\"footerimage\" title=\"ProximaInformatica\">\r\n" + 
//			"    </footer>\r\n" + 
//			"	<script src=\"/centauri/common/js/ext/jquery/3.1.1/jquery-3.3.1.slim.min.js\"> </script>\r\n" + 
//			"	<script\r\n" + 
//			"	src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js\"\r\n" + 
//			"	integrity=\"sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49\"\r\n" + 
//			"	crossorigin=\"anonymous\"></script>\r\n" + 
//			"<script\r\n" + 
//			"	src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js\"\r\n" + 
//			"	integrity=\"sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy\"\r\n" + 
//			"	crossorigin=\"anonymous\"></script>\r\n"; 
//	static final String START_MESSAGE_BODY="<!DOCTYPE html>\r\n" + 
//			"<html lang=\"it\" ng-app=\"courseregistrationsystem\">\r\n" + 
//			"<head>\r\n" + 
//			"<script src=\"/centauri/a/js/ext/angularjs/1.4.9/angular.js\"></script>\r\n" + 
//			"<script src=\"/centauri/a/js/ext/angularjs/1.4.9/angular-resource.js\"></script>\r\n" + 
//			"<script src=\"/centauri/a/js/ext/angularjs/1.4.9/angular-route.js\"></script>\r\n" + 
//			"<script src=\"/centauri/a/js/app_courses.js\"></script>\r\n" + 
//			"<script src=\"/centauri/a/js/coursePageController.js\"></script>\r\n" + 
//			"\r\n" + 
//			"\r\n" + 
//			"<meta charset=\"utf-8\">\r\n" + 
//			"<meta name=\"viewport\"\r\n" + 
//			"	content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\r\n" + 
//			"<meta name=\"author\" content=\"m.franco@proximainformatica.com\">\r\n" + 
//			"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n" + 
//			"<meta charset=\"UTF-8\">\r\n" + 
//			"<title>Candidati - Corso Java - Proxima Informatica</title>\r\n" + 
//			"<meta name=\"description\"\r\n" + 
//			"	content=\"Corso Java gratuito professionalizzante su Roma e Milano. A fine corso contratto a tempo indeterminato (placement 70%). Invia subito il tuo cv!\" />\r\n" + 
//			"<link rel=\"icon\"\r\n" + 
//			"	href=\"http://www.proximainformatica.com/wp-content/uploads/2017/09/cropped-favicon-fb-proxima-3-32x32.png\"\r\n" + 
//			"	sizes=\"32x32\" />\r\n" + 
//			"<link rel=\"icon\"\r\n" + 
//			"	href=\"http://www.proximainformatica.com/wp-content/uploads/2017/09/cropped-favicon-fb-proxima-3-192x192.png\"\r\n" + 
//			"	sizes=\"192x192\" />\r\n" + 
//			"<link rel=\"apple-touch-icon-precomposed\"\r\n" + 
//			"	href=\"http://www.proximainformatica.com/wp-content/uploads/2017/09/cropped-favicon-fb-proxima-3-180x180.png\" />\r\n" + 
//			"<link\r\n" + 
//			"	href=\"https://getbootstrap.com/docs/4.1/dist/css/bootstrap.min.css\"\r\n" + 
//			"	rel=\"stylesheet\">\r\n" + 
//			"<link rel=\"stylesheet\" type=\"text/css\"\r\n" + 
//			"	href=\"/centauri/candidati/css/notice.css\">\r\n" + 
//			"<link rel=\"stylesheet\" type=\"text/css\"\r\n" + 
//			"	href=\"/centauri/candidati/css/style.css\">\r\n" + 
//			"<link rel=\"stylesheet\" type=\"text/css\"\r\n" + 
//			"	href=\"/centauri/candidati/css/candidateregistration.css\">\r\n" + 
//			"</head>\r\n" + 
//			"<body style=\"margin-top: 30px;\">\r\n" + 
//			"\r\n" + 
//			"	<nav class=\"navbar navbar-expand-md navbar-dark bg-dark fixed-top\">\r\n" + 
//			"		<a class=\"navbar-brand\" href=\"#\"> <img\r\n" + 
//			"			src=\"http://www.proximainformatica.com/wp-content/uploads/2017/11/LOGO-PROXIMA-ROSSO-white-2017.png\"\r\n" + 
//			"			title=\"proximainformatica\" id=\"headerlogoproxima\">\r\n" + 
//			"		</a>\r\n" + 
//			"		<button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\"\r\n" + 
//			"			data-target=\"#navbarsExampleDefault\"\r\n" + 
//			"			aria-controls=\"navbarsExampleDefault\" aria-expanded=\"false\"\r\n" + 
//			"			aria-label=\"Toggle navigation\">\r\n" + 
//			"			<span class=\"navbar-toggler-icon\"></span>\r\n" + 
//			"		</button>\r\n" + 
//			"\r\n" + 
//			"	</nav>\r\n" + 
//			"\r\n" + 
//			"\r\n" + 
//			"\r\n" + 
//			"	<div class=\"starter-template\" ng-view></div>\r\n" + 
//			"	<footer>\r\n" + 
//			"		<img src=\"/centauri/common/img/logo/footer/centauriacademy.png\"\r\n" + 
//			"			id=\"footerimage\" title=\"ProximaInformatica\">\r\n" + 
//			"	</footer>\r\n" + 
//			"	<script\r\n" + 
//			"		src=\"/centauri/common/js/ext/jquery/3.1.1/jquery-3.3.1.slim.min.js\"> </script>\r\n" + 
//			"	<script\r\n" + 
//			"		src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js\"\r\n" + 
//			"		integrity=\"sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49\"\r\n" + 
//			"		crossorigin=\"anonymous\"></script>\r\n" + 
//			"	<script\r\n" + 
//			"		src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js\"\r\n" + 
//			"		integrity=\"sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy\"\r\n" + 
//			"		crossorigin=\"anonymous\"></script>";

	public static final Logger logger = LoggerFactory.getLogger(CoursePageController.class);

	@Value("${app.folder.page.pubblication}")
	private String COURSE_PAGES_FOLDER;
	@Autowired
	private CoursePageRepository coursePageRepository;

	@Autowired
	public void setCoursePageJpaRepository(CoursePageRepository coursePageRepository) {
		this.coursePageRepository = coursePageRepository;
	}

	
	@GetMapping("/")
	public ResponseEntity<List<CoursePage>> listAllCoursePage() {
		List<CoursePage> coursePage = coursePageRepository.findAll();

		if (coursePage.isEmpty()) {

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(coursePage, HttpStatus.OK);
	}

	@CacheEvict(value = "codes", allEntries = true)
	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CeReProAbstractEntity> createCoursePage(@Valid @RequestBody final CoursePage coursePage) {

		logger.info("Creating coursePage : {}", coursePage);
		coursePageRepository.save(coursePage);
		return new ResponseEntity<>(coursePage, HttpStatus.CREATED);
	}
	
	
	@CacheEvict(value = "codes", allEntries = true)
	@PutMapping(value = "/online/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> setCoursePageOnline(@PathVariable("id") final Long id) {

		logger.info("setCoursePageOnline coursePage id : {}", id);
		
//		creaDirCourses();			
		Optional<CoursePage> current = coursePageRepository.findById(id);
		if (current.isPresent()) {
			CoursePage currentCP = current.get();
			try {
//			currentCP.setFileName(UUID.randomUUID().toString().substring(0, 5)) ;

//			String fileName = currentCP.getFileName();			
//			String fileToWrite = fileName +".html" ; 
//			currentCP.setFileName(fileToWrite);

				currentCP.setFileName("index.html");
				logger.info("setCoursePageOnline currentCP: {}", currentCP);
				coursePageRepository.save(currentCP);
				logger.info("setCoursePageOnline coursePage id : {}", id+" end");
				return new ResponseEntity<>(HttpStatus.OK);
			} catch (Exception e) {
				logger.error("error", e);
				logger.info("setCoursePageOnline coursePage id : {}", id+" end");
				return new ResponseEntity<>(HttpStatus.CONFLICT);
				
			}
		} else {
			logger.info("setCoursePageOnline coursePage id : {}", id+" end");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

//	@PutMapping(value = "/addBodyText/{code}", consumes = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<?> setCoursePageAddBody(@PathVariable("code") final String code) {
//
//		logger.info("setCoursePageAddBody coursePage code : {}", code);
//
////		creaDirCourses();			
//		CoursePage currentCP = coursePageRepository.findByCode(code);
//		if (currentCP != null) {
////			CoursePage currentCP = current.get();
//			try {
////			currentCP.setFileName(UUID.randomUUID().toString().substring(0, 5)) ;
//
////			String fileName = currentCP.getFileName();			
////			String fileToWrite = fileName +".html" ; 
////			currentCP.setFileName(fileToWrite);
//
//				currentCP.setFileName("index.html");
//				String fileToWrite = COURSE_PAGES_FOLDER + File.separatorChar + "index.html";
//				File f = new File(fileToWrite);
//
//				FileOutputStream fos = new FileOutputStream(f);
//
//				PrintWriter pw = new PrintWriter(fos);
//
//				pw.println(START_MESSAGE_BODY + currentCP.getBodyText() + FINE_INDEX);
//				pw.close();
//
//				coursePageRepository.save(currentCP);
//				return new ResponseEntity<>(HttpStatus.OK);
//			} catch (Exception e) {
//				logger.error("error", e);
//				return new ResponseEntity<>(HttpStatus.CONFLICT);
//			}
//		} else {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//
//	}
	
	//metodo get copiato da user
	@GetMapping(value = "/getBodyText/{code}")
	public ResponseEntity<?> getCoursePageByCode(@PathVariable("code") final String code) {
		logger.info("getCoursePageByCode coursePage code : {}", code);
		
		CoursePage currentCP = coursePageRepository.findByCode(code);
		logger.info("getCoursePageByCode coursePage code : {} begin", code);
		if (currentCP!=null) {
			logger.info("getCoursePageByCode coursePage code : {} end", code);
			return new ResponseEntity<CoursePage>(currentCP, HttpStatus.OK);
			
		} else {
			logger.info("getCoursePageByCode coursePage code : {} end", code);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		

	}

	@PutMapping(value = "/offline/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> setCoursePageOffline(@PathVariable("id") final Long id) {

		logger.info("setCoursePageOnline coursePage id : {}", id+" begin");

//		creaDirCourses();			
		Optional<CoursePage> current = coursePageRepository.findById(id);
		if (current.isPresent()) {
			CoursePage currentCP = current.get();
			try {
//			currentCP.setFileName(UUID.randomUUID().toString().substring(0, 5)) ;

//			String fileName = currentCP.getFileName();			
//			String fileToWrite = fileName +".html" ; 
//			currentCP.setFileName(fileToWrite);

				currentCP.setFileName(null);
				coursePageRepository.save(currentCP);
				logger.info("setCoursePageOnline coursePage id : {}", id+" end");
				return new ResponseEntity<>(HttpStatus.OK);
			} catch (Exception e) {
				logger.error("error", e);
				logger.info("setCoursePageOnline coursePage id : {}", id+" end");
				return new ResponseEntity<>(HttpStatus.CONFLICT);
				
			}
		} else {
			logger.info("setCoursePageOnline coursePage id : {}", id+" end");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<CeReProAbstractEntity> getCoursePageById(@PathVariable("id") final Long id) {
		Optional<CoursePage> coursePage = coursePageRepository.findById(id);

		if (!coursePage.isPresent()) {

			return new ResponseEntity<>(new CustomErrorType("CoursePage with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(coursePage.get(), HttpStatus.OK);
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CeReProAbstractEntity> updateCoursePage(@PathVariable("id") final Long id,
			@RequestBody CoursePage coursePage) {

		Optional<CoursePage> currentCoursePage = coursePageRepository.findById(id);

		if (!currentCoursePage.isPresent()) {
			return new ResponseEntity<>(
					new CustomErrorType("Unable to update. CoursePage with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentCoursePage.get().setFileName(coursePage.getFileName());
		currentCoursePage.get().setTitle(coursePage.getTitle());
		currentCoursePage.get().setCode(coursePage.getCode());
		currentCoursePage.get().setBodyText(coursePage.getBodyText());

		coursePageRepository.saveAndFlush(currentCoursePage.get());

		return new ResponseEntity<>(currentCoursePage.get(), HttpStatus.OK);
	}

	@CacheEvict(value = "codes", allEntries = true)
	@DeleteMapping("/{id}")
	public ResponseEntity<CeReProAbstractEntity> deleteCoursePage(@PathVariable("id") final Long id) {
		Optional<CoursePage> coursePage = coursePageRepository.findById(id);
		if (!coursePage.isPresent()) {
			return new ResponseEntity<>(
					new CustomErrorType("Unable to delete. CoursePage with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		coursePageRepository.delete(coursePage.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping(value = "/deleteFile/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity deleteFile(@PathVariable("id") final Long id) throws FileNotFoundException {

		Optional<CoursePage> current = coursePageRepository.findById(id);
		logger.info("setCoursePageOffline coursePage id : {}", id);

//		creaDirCourses();			

		CoursePage cp = new CoursePage();
		String fileName = cp.getFileName();
		String fileToDelete = COURSE_PAGES_FOLDER + File.separatorChar + fileName + ".html";//

		File f = new File(fileToDelete);

		if (!f.exists())
			throw new IllegalArgumentException("Il File o la Directory non esiste: " + fileToDelete);

		if (!f.canWrite())
			throw new IllegalArgumentException("Non ho il permesso di scrittura: " + fileToDelete);

		boolean success = f.delete();

		if (!success)
			throw new IllegalArgumentException("Cancellazione fallita");

		return new ResponseEntity<>(current.get(), HttpStatus.OK);

	}

	private boolean deleteCoursePageFile(CoursePage cp) {

		logger.info("deleteCoursePageFile coursePage : {}", cp);

//		creaDirCourses();			

		String fileName = "index.html";

		String fileToDelete = COURSE_PAGES_FOLDER + File.separatorChar + fileName;// + ".html"

		File f = new File(fileToDelete);

		if (!f.exists())
			throw new IllegalArgumentException("Il File o la Directory non esiste: " + fileToDelete);

		if (!f.canWrite())
			throw new IllegalArgumentException("Non ho il permesso di scrittura: " + fileToDelete);

		boolean success = f.delete();

		if (!success)
			throw new IllegalArgumentException("Cancellazione fallita");

		return success;

	}
	@Cacheable("codes")
	@GetMapping("/codes")
	public ResponseEntity<List<CustomCode>> listAllCourseCode() {
		List<CoursePage> coursePage = coursePageRepository.findAll(Sort.by(Sort.Direction.ASC, "code"));
		

		if (coursePage.size()==0) {

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		else
		{	
			List<CustomCode> codes= new ArrayList<CustomCode>();
			CustomCode currentCustomCode= new CustomCode();
//			coursePage.stream().forEach((x)->currentCustomCode.setCode(x.getCode()));
//			coursePage.stream().forEach((y)->codes.add(currentCustomCode));
		
			
			for(CoursePage a: coursePage)
			{
				String code= a.getCode();
				currentCustomCode= new CustomCode();
				System.out.println(code);
				currentCustomCode.setCode(code);
				System.out.println(currentCustomCode.getCode());
				codes.add(currentCustomCode);
				
			}
			
			
			return new ResponseEntity<>(codes,HttpStatus.OK);
		}
	}

}
