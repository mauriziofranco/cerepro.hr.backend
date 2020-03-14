package centauri.academy.cerepro.backend;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import centauri.academy.cerepro.CeReProBackendApplication;
import centauri.academy.cerepro.persistence.entity.CeReProAbstractEntity;
import centauri.academy.cerepro.persistence.entity.CourseResume;
import centauri.academy.cerepro.persistence.repository.CourseResumeRepository;

/**
 * @author ivan borriello
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeReProBackendApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class CourseResumeControllerTest {

	public static final Logger logger = LoggerFactory.getLogger(CourseResumeControllerTest.class);

	@Spy
	private CourseResumeController courseResumeController;
	@Mock
	private CourseResumeRepository courseResumeRepository;

	@Before
	public void setup() {
		courseResumeController = new CourseResumeController();
		ReflectionTestUtils.setField(courseResumeController, "courseResumeRepository", courseResumeRepository);
	}

	
	@Test
	public void testGetCourseResumeById() {
		
		logger.info("testGetCourseResumeById()  ---------------------- START");
		CourseResume cr = new CourseResume();
		cr.setId(10L);
		cr.setTitle("prova title");
		cr.setContent("prova content");

		Optional<CourseResume> currOpt = Optional.of(cr);
		when(this.courseResumeRepository.findById(10L)).thenReturn(currOpt);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.courseResumeController.getCourseResumeById(10L);
		
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertEquals("prova title", ((CourseResume) responseEntity.getBody()).getTitle());
		Assert.assertEquals("prova content", ((CourseResume) responseEntity.getBody()).getContent());
		logger.info("testGetCourseResumeById()  ---------------------- END");
	}
	
	
	@Test
	public void testInsertCourseResume() {
		
		logger.info("testInsertCourseResume()  ---------------------- START");
		CourseResume cr = new CourseResume();
		cr.setId(10L);
		cr.setTitle("prova title");
		cr.setContent("prova content");
		
		when(this.courseResumeRepository.findById(cr.getId())).thenReturn(null);
		when(this.courseResumeRepository.save(cr)).thenReturn(cr);
		ResponseEntity<CeReProAbstractEntity> responseEntity = courseResumeController.createCourseResume(cr);
		
		Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		Assert.assertEquals("prova title", ((CourseResume) responseEntity.getBody()).getTitle());
		Assert.assertEquals("prova content", ((CourseResume) responseEntity.getBody()).getContent());
		
		logger.info("testInsertCourseResume()  ---------------------- END");
	}
	
	@Test
	public void testDeleteCourseResume() {
		logger.info("testDeleteCourseResume()  ---------------------- START");
		CourseResume testCourseResume = new CourseResume(13L, "aaa", "bbb", "ccc");
		Optional<CourseResume> currOpt = Optional.of(testCourseResume) ;
		when(this.courseResumeRepository.findById(13L)).thenReturn(currOpt);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.courseResumeController.deleteCourseResume(13L);
		Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
		logger.info("testDeleteCourseResume()  ---------------------- END");
	}
	
	@Test
	public void testUpdateCourseResume() {
		logger.info("testUpdateCourseResume()  ---------------------- START");
		CourseResume testCourseResume = new CourseResume (12L, "aaa", "bbb", "ccc");
		Optional<CourseResume> currOpt = Optional.of(testCourseResume) ;
		when(this.courseResumeRepository.findById(12L)).thenReturn(currOpt);
		testCourseResume.setTitle("prova title");
		testCourseResume.setContent("prova content");

		ResponseEntity<CeReProAbstractEntity> responseEntity = this.courseResumeController.updateCourseResume(12L, testCourseResume);
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertEquals("prova title", ((CourseResume) responseEntity.getBody()).getTitle());
		Assert.assertEquals("prova content", ((CourseResume) responseEntity.getBody()).getContent());
		
		logger.info("testUpdateCourseResume()  ---------------------- END");
	}
	
	
	@After
	public void teardown() {
		courseResumeController = null;
	}

}
