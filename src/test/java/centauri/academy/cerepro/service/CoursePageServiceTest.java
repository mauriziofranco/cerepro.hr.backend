package centauri.academy.cerepro.service;


import static org.assertj.core.api.Assertions.assertThat;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import centauri.academy.cerepro.CeReProBackendApplication;
import centauri.academy.cerepro.persistence.entity.CoursePage;
import centauri.academy.cerepro.persistence.repository.coursepage.CoursePageRepository;

/**
 * @author m.franco@proximainformatica.com
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeReProBackendApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class CoursePageServiceTest {

	public static final Logger logger = LoggerFactory.getLogger(CoursePageServiceTest.class);

	@TestConfiguration
    static class CoursePageServiceTestContextConfiguration {
  
        @Bean
        public CoursePageService coursePageService() {
            return new CoursePageService();
        }
    }
 
    @Autowired
    private CoursePageService coursePageService;
 
    @MockBean
    private CoursePageRepository coursePageRepository;
 
    private final String TEST_CODE = "test_code" ;
    
    @Before
    public void initialize() {
        CoursePage currentEntity = new CoursePage();
        currentEntity.setCode(TEST_CODE);
     
        Mockito.when(coursePageRepository.findByCode(TEST_CODE))
          .thenReturn(currentEntity);
    }
    
    @Test
    public void whenValidCode_thenCoursePageShouldBeFound() {
    	CoursePage currentEntity = coursePageService.getCoursePageByCode(TEST_CODE);
      
        assertThat(currentEntity.getCode()).isEqualTo(TEST_CODE);
     }
}
