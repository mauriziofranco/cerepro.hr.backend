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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import centauri.academy.cerepro.CeReProBackendApplication;
import centauri.academy.cerepro.persistence.entity.CeReProAbstractEntity;
import centauri.academy.cerepro.persistence.entity.CoursePage;
import centauri.academy.cerepro.persistence.repository.CoursePageRepository;

/**
 * @author Traian Cojocariu
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeReProBackendApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class CoursePageControllerTest {

	public static final Logger logger = LoggerFactory.getLogger(CoursePageControllerTest.class);

	@Spy
	private CoursePageController coursePageController;
	@Mock
	private CoursePageRepository coursePageRepository;

	@Before
	public void setup() {
		coursePageController = new CoursePageController();
		ReflectionTestUtils.setField(coursePageController, "coursePageRepository", coursePageRepository);
	}

	@Test
	public void testListAllCoursePages() {
		logger.info("testListAllCoursePages()  ---------------------- START");
		List<CoursePage> coursePageList = new ArrayList<CoursePage>();
		coursePageList.add(new CoursePage());
		when(this.coursePageRepository.findAll()).thenReturn(coursePageList);
		ResponseEntity<List<CoursePage>> responseEntity = this.coursePageController.listAllCoursePage();
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertEquals(1, responseEntity.getBody().size());
		logger.info("testListAllCoursePages()  ---------------------- END");
	}
	
	@Test
	public void testGetCoursePageById() {
		
		logger.info("testGetCoursePageById()  ---------------------- START");
		CoursePage cp = new CoursePage();
		cp.setId(10L);
		cp.setFileName("prova.FileName");
		cp.setTitle("prova.TitleName");
		cp.setBodyText("prova.BodyText");

		Optional<CoursePage> currOpt = Optional.of(cp);
		when(this.coursePageRepository.findById(10L)).thenReturn(currOpt);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.coursePageController.getCoursePageById(10L);
		
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertEquals("prova.FileName", ((CoursePage) responseEntity.getBody()).getFileName());
		Assert.assertEquals("prova.BodyText", ((CoursePage) responseEntity.getBody()).getBodyText());
		Assert.assertEquals("prova.TitleName", ((CoursePage) responseEntity.getBody()).getTitle());
		logger.info("testGetCoursePageById()  ---------------------- END");
	}
	
	
	@Test
	public void testInsertCoursePage() {
		
		logger.info("testInsertCoursePage()  ---------------------- START");
		CoursePage coursePage = new CoursePage();
		coursePage.setId(10L);
		coursePage.setFileName("prova.FileName1");
		coursePage.setTitle("prova.TitleName1");
		coursePage.setBodyText("prova.BodyText1");
		
		when(this.coursePageRepository.findByFileName(coursePage.getFileName())).thenReturn(null);
		when(this.coursePageRepository.save(coursePage)).thenReturn(coursePage);
		ResponseEntity<CeReProAbstractEntity> responseEntity = coursePageController.createCoursePage(coursePage);
		
		Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		Assert.assertEquals("prova.FileName1", ((CoursePage) responseEntity.getBody()).getFileName());
		Assert.assertEquals("prova.TitleName1", ((CoursePage) responseEntity.getBody()).getTitle());

		Assert.assertEquals("prova.BodyText1", ((CoursePage) responseEntity.getBody()).getBodyText());
		
		logger.info("testInsertCoursePage()  ---------------------- END");
	}
	
	@Test
	public void testDeleteCoursePage() {
		logger.info("testDeleteCoursePage()  ---------------------- START");
		CoursePage testCoursePage = new CoursePage(13L, "aaa", "bbb", "ccc");
		Optional<CoursePage> currOpt = Optional.of(testCoursePage) ;
		when(this.coursePageRepository.findById(13L)).thenReturn(currOpt);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.coursePageController.deleteCoursePage(13L);
		Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
		logger.info("testDeleteCoursePage()  ---------------------- END");
	}
	
	@Test
	public void testUpdateCoursePage() {
		logger.info("testUpdateCoursePage()  ---------------------- START");
		CoursePage testCoursePage = new CoursePage (12L, "aaa", "bbb", "ccc");
		Optional<CoursePage> currOpt = Optional.of(testCoursePage) ;
		when(this.coursePageRepository.findById(12L)).thenReturn(currOpt);
		testCoursePage.setBodyText("testerUPDATED1");
		testCoursePage.setTitle("testerUPDATED2");
		testCoursePage.setFileName("testerUPDATED3");

		ResponseEntity<CeReProAbstractEntity> responseEntity = this.coursePageController.updateCoursePage(12L, testCoursePage);
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//		Assert.assertEquals(CoursePageController.START_MESSAGE_BODY.substring(0,10), ((CoursePage)responseEntity.getBody()).getBodyText().substring(0,10));
		Assert.assertEquals("testerUPDATED1", ((CoursePage)responseEntity.getBody()).getBodyText());
		Assert.assertEquals("testerUPDATED2", ((CoursePage)responseEntity.getBody()).getTitle());
		Assert.assertEquals("testerUPDATED3", ((CoursePage)responseEntity.getBody()).getFileName());

		logger.info("testUpdateCoursePage()  ---------------------- END");
	}
	
	
	
	
	@After
	public void teardown() {
		coursePageController = null;
	}

}
