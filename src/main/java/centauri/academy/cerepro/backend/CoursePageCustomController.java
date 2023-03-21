package centauri.academy.cerepro.backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import centauri.academy.cerepro.persistence.entity.custom.CoursePageCustom;
import centauri.academy.cerepro.persistence.repository.coursepage.CoursePageRepositoryCustom;
import centauri.academy.cerepro.service.CoursePageService;

/**
 * 
 * 
 * @author 
 *
 */

@RestController
@RequestMapping("/api/v1/coursepagecustom")
public class CoursePageCustomController {
	
	public static final Logger logger = LoggerFactory.getLogger(CoursePageCustomController.class);

	@Autowired
	private CoursePageService coursePageService;

	@GetMapping("/")
	public ResponseEntity<List<CoursePageCustom>> listAllCoursePageCustom() {
		
		List<CoursePageCustom> coursePages = coursePageService.getAllCoursePageCustom();
		for (CoursePageCustom current : coursePages) {
			logger.info(current.toString());
		}

		if (coursePages.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		return new ResponseEntity<>(coursePages, HttpStatus.OK);
	}

	
}
