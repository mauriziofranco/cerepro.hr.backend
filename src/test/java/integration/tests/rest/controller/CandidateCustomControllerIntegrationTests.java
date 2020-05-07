package integration.tests.rest.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import centauri.academy.cerepro.CeReProBackendApplication;
import centauri.academy.cerepro.persistence.entity.Candidate;
import centauri.academy.cerepro.persistence.entity.CandidateStates;
import centauri.academy.cerepro.persistence.entity.CoursePage;
import centauri.academy.cerepro.persistence.entity.User;
import centauri.academy.cerepro.service.CandidateService;
import centauri.academy.cerepro.service.CandidateStateService;
import centauri.academy.cerepro.service.CoursePageService;
import centauri.academy.cerepro.service.RoleService;
import centauri.academy.cerepro.service.UserService;

/**
 * Integration tests CandidateCustomControllerTest methods
 * @author m.franco
 */

@RunWith(SpringRunner.class)
@SpringBootTest (classes = CeReProBackendApplication.class, webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
//@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class CandidateCustomControllerIntegrationTests extends AbstractIntegrationTests {
	
	private Logger logger = LoggerFactory.getLogger(CandidateCustomControllerIntegrationTests.class);
	
	private final String SERVICE_URI = "/api/v1/candidatecustom/" ;
	
	@Autowired
    private MockMvc mvc;
	
	@Autowired
    private CandidateService candidateService;
	@Autowired
    private UserService userService;
	@Autowired
    private RoleService roleService;
	@Autowired
    private CoursePageService coursePageService;
	@Autowired
    private CandidateStateService candidateStateService;
	
	@Before
	public void initializeRelatedTables () throws Exception {
		logger.trace("initializeRelatedTables - START");
		candidateService.deleteAll();
		coursePageService.deleteAll();
		candidateStateService.deleteAll();
		userService.deleteAll();
		roleService.deleteAll();
	}
	
	@Test
	public void whenGetCandidateCustomById_andThereIsNot_thenStatus204() throws Exception {
		logger.trace("########################################################");
		logger.trace("########################################################");
		logger.trace("whenGetCandidateCustomById_andThereIsNot_thenStatus204 - START");
		logger.trace("########################################################");
		logger.trace("########################################################");
	    mvc.perform(get(SERVICE_URI + getRandomLongBetweenLimits())
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isNoContent());
	    logger.trace("########################################################");
		logger.trace("########################################################");
		logger.trace("whenGetCandidateCustomById_andThereIsNot_thenStatus204 - END");
		logger.trace("########################################################");
		logger.trace("########################################################");
	}
	
	@Test
	public void whenGetCandidateCustomById_andThereIsOne_thenStatus200() throws Exception {
		logger.trace("########################################################");
		logger.trace("########################################################");
		logger.trace("whenGetCandidateCustomById_andThereIsOne_thenStatus200 - START");
		logger.trace("########################################################");
		logger.trace("########################################################");
		Candidate insertedCandidate = getFakeCandidate() ;
//		RequestMatcher rm = jsonPath("$[0].firstname", is(insertedCandidate.getFirstname()));
		long candidateId = insertedCandidate.getId().longValue() ;
		logger.trace("whenGetCandidateById_andThereIsOne_thenStatus200 - DEBUG - looking for candidateId: " + candidateId);
		mvc.perform(
				  get(SERVICE_URI + candidateId).contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
//			      .andExpect(status().isOk());
//		          .andExpect(jsonPath("$[0].firstname", is(insertedCandidate.getFirstname())));
//		          .andExpect(MockMvcResultMatchers.jsonPath("$.firstname").isString());
		          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			      .andExpect(jsonPath("$.firstname", is(insertedCandidate.getFirstname())))
		          .andExpect(jsonPath("$.lastname", is(insertedCandidate.getLastname())))
		          .andExpect(jsonPath("$.email", is(insertedCandidate.getEmail())))
//		          .andExpect(jsonPath("$.candidateStatesId", is((int)insertedCandidate.getCandidateStatesId())))
		          .andExpect(jsonPath("$.courseCode", is(insertedCandidate.getCourseCode())))
//		          .andExpect(jsonPath("$.regdate", is(insertedCandidate.getRegdate())))
//		          .andExpect(jsonPath("$.insertedBy", is(insertedCandidate.getInsertedBy())))
		          ;
		logger.trace("########################################################");
		logger.trace("########################################################");
		logger.trace("whenGetCandidateCustomById_andThereIsOne_thenStatus200 - END");
		logger.trace("########################################################");
		logger.trace("########################################################");
	}
	
	@Test
	public void whenPostNewSimpleCandidateCustom_thenStatus201() throws Exception {
		logger.trace("########################################################");
		logger.trace("########################################################");
		logger.trace("whenPostNewSimpleCandidateCustom_thenStatus201 - START");
		logger.trace("########################################################");
		logger.trace("########################################################");
		User userThatProvidesToInsert = getFakeUser();
		CoursePage testCoursePage = getFakeCoursePage();
		CandidateStates testCandidateState = getFakeCandidateState();
		String testEmail = "aaa@aaa.it" ;
		String testFirstname = "Giuseppe" ;
		String testLastname = "Rossi" ;

		RequestBuilder request = post(SERVICE_URI)
		        .param("email", testEmail)
		        .param("firstname",testFirstname)
		        .param("lastname",testLastname)
		        .param("userId",""+userThatProvidesToInsert.getId().longValue())
		        .param("insertedBy",""+userThatProvidesToInsert.getId().longValue())
		        .param("courseCode",testCoursePage.getCode())
//		        .with(csrf())
		        ;

		    mvc
		        .perform(request)
		        .andDo(MockMvcResultHandlers.print())
		        .andExpect(status().isCreated())
		        .andExpect(jsonPath("$.firstname", is(testFirstname)))
//		        .andExpect(redirectedUrl("/"))
		        ;
		logger.trace("########################################################");
		logger.trace("########################################################");
		logger.trace("whenPostNewSimpleCandidateCustom_thenStatus201 - END");
		logger.trace("########################################################");
		logger.trace("########################################################");
	}
	
	
	
	
}