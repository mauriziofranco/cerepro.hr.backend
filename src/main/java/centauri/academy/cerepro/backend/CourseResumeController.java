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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import centauri.academy.cerepro.persistence.entity.CourseResume;
import centauri.academy.cerepro.persistence.entity.Employee;
import centauri.academy.cerepro.persistence.entity.custom.CandidateCustom;
import centauri.academy.cerepro.persistence.entity.custom.CustomErrorType;
import centauri.academy.cerepro.persistence.repository.CourseResumeRepository;

@RestController
@RequestMapping("/api/v1/courseresume")
public class CourseResumeController {
	public static final Logger logger = LoggerFactory.getLogger(CourseResumeController.class);

	@Autowired
	private CourseResumeRepository courseResumeRepository;

	@GetMapping("/paginated/{size}/{number}/")
	public ResponseEntity<Page<CourseResume>> getAllCourseResume(@PathVariable("size") final int size,
			@PathVariable("number") final int number) {
		Page<CourseResume> pcr = courseResumeRepository.findAll(PageRequest.of(number, size, Sort.Direction.ASC, "id"));
		if (pcr.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(pcr, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CeReProAbstractEntity> getCourseResumeById(@PathVariable("id") final Long id) {
		Optional<CourseResume> courseResume = courseResumeRepository.findById(id);
		if (!courseResume.isPresent()) {
			return new ResponseEntity<>(new CustomErrorType("Unable to find CourseResume with id " + id),
					HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(courseResume.get(), HttpStatus.OK);
		}
	}

	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CeReProAbstractEntity> createCourseResume(
			@Valid @RequestBody final CourseResume courseResume) {
			logger.info("Creating courseResume : {}", courseResume);
			courseResumeRepository.save(courseResume);
			return new ResponseEntity<>(courseResume, HttpStatus.CREATED);
		}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CeReProAbstractEntity> updateCourseResume(@PathVariable("id") final Long id,
			@RequestBody CourseResume courseResume) {
		CourseResume currentCourseResume = null;
		Optional<CourseResume> optCourseResume = courseResumeRepository.findById(id);

		if (!optCourseResume.isPresent()) {
			return new ResponseEntity<>(
					new CustomErrorType("Unable to update. CourseResume with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		if (optCourseResume.isPresent()) {
			currentCourseResume = optCourseResume.get();
			currentCourseResume.setTitle(courseResume.getTitle());
			currentCourseResume.setContent(courseResume.getContent());
			currentCourseResume.setCode(courseResume.getCode());

			courseResumeRepository.saveAndFlush(currentCourseResume);
			return new ResponseEntity<>(currentCourseResume, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(
					new CustomErrorType("Unable to update. CourseResume with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<CeReProAbstractEntity> deleteCourseResume(@PathVariable("id") final Long id) {
		Optional<CourseResume> courseResume = courseResumeRepository.findById(id);
		if (!courseResume.isPresent()) {
			return new ResponseEntity<>(
					new CustomErrorType("Unable to delete. CourseResume with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		courseResumeRepository.deleteById(id);
		return new ResponseEntity<>(new CustomErrorType("Good CourseResume with id " + id + " is deleted."),
				HttpStatus.NO_CONTENT);
	}
}
