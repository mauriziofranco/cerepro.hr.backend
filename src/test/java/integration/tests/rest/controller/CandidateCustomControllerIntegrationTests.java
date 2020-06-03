package integration.tests.rest.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.util.Base64Utils;

import centauri.academy.cerepro.CeReProBackendApplication;
import centauri.academy.cerepro.persistence.entity.Candidate;
import centauri.academy.cerepro.persistence.entity.CandidateStates;
import centauri.academy.cerepro.persistence.entity.CoursePage;
import centauri.academy.cerepro.persistence.entity.Role;
import centauri.academy.cerepro.persistence.entity.User;
import centauri.academy.cerepro.service.CandidateService;
import centauri.academy.cerepro.service.CandidateStateService;
import centauri.academy.cerepro.service.CoursePageService;
import centauri.academy.cerepro.service.ITConsultantService;
import centauri.academy.cerepro.service.RoleService;
import centauri.academy.cerepro.service.SurveyReplyService;
import centauri.academy.cerepro.service.UserService;
import centauri.academy.cerepro.service.UserSurveyTokenService;

/**
 * Integration tests CandidateCustomControllerTest methods
 * @author m.franco
 */

@RunWith(SpringRunner.class)
@SpringBootTest (classes = CeReProBackendApplication.class, webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
//@WebMvcTest(controllers = { CandidateCustomController.class }, secure = false)
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
	@Autowired
    private SurveyReplyService surveyReplyService;
	@Autowired
    private UserSurveyTokenService userSurveyTokenService;
	@Autowired
    private ITConsultantService iTConsultantService;
	
	@Before
	public void initializeRelatedTables () throws Exception {
		logger.trace("initializeRelatedTables - START");
		candidateService.deleteAll();
		coursePageService.deleteAll();
		candidateStateService.deleteAll();
		surveyReplyService.deleteAll();
		userSurveyTokenService.deleteAll();
		iTConsultantService.deleteAll();
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
		User operativeUser = getFakeUser(Role.ADMIN_LEVEL);
        logger.trace("auth  : " + Base64Utils.encodeToString((operativeUser.getEmail()+":"+TEST_DECODED_USER_PASSWORD).getBytes()));
//        logger.trace("password: " + Base64Utils.encodeToString("ciao1234".getBytes()));
//        logger.trace("httpBasic(user.getEmail(),\"ciao1234\"): " + (httpBasic(user.getEmail(),TEST_DECODED_USER_PASSWORD)).toString());
//        logger.trace("user.getPassword().getBytes(): " + user.getPassword().getBytes());
        String auth2 = Base64Utils.encodeToString((operativeUser.getEmail()+":" + TEST_DECODED_USER_PASSWORD).getBytes()) ;
        logger.trace("auth2 : " + auth2);
        logger.trace("password: " + Base64Utils.encodeToString(TEST_DECODED_USER_PASSWORD.getBytes()));
        logger.trace("decodeFromString(auth2) : " + Base64Utils.decodeFromString(auth2));
        logger.trace("decoded string:" + new String(Base64Utils.decodeFromString(auth2), "UTF-8").toString());
        
        String pwd = Base64Utils.encodeToString((TEST_DECODED_USER_PASSWORD).getBytes()) ;
        logger.trace("pwd : " + pwd);
        String encoded=new BCryptPasswordEncoder().encode(pwd);
        logger.trace("pwd encoded: " + encoded);
        logger.trace("decodeFromString(pwd) : " + Base64Utils.decodeFromString(pwd));
        logger.trace("decoded string:" + new String(Base64Utils.decodeFromString(pwd), "UTF-8").toString());
        
        
        String encoded2=new BCryptPasswordEncoder().encode(TEST_DECODED_USER_PASSWORD);
        logger.trace("pwd encoded2: " + encoded2);
//        logger.trace("match1: " + new BCryptPasswordEncoder().matches("ciao1234", TEST_ENCODED_USER_PASSWORD));
        logger.trace("match2: " + new BCryptPasswordEncoder().matches(TEST_DECODED_USER_PASSWORD, encoded2));
//        logger.trace("decodeFromUrlSafeString: " + Base64Utils.decodeFromUrlSafeString("$2a$10$FKozujcHmWdulk6naR/XveW3x46hWPnRY2S/cyI/XhmjZZEOwz.bW"));
//        logger.trace("decodeFromString: " + Base64Utils.decodeFromString("$2a$10$FKozujcHmWdulk6naR/XveW3x46hWPnRY2S/cyI/XhmjZZEOwz.bW"));
//        logger.trace("decodeFromString.toString: " + (Base64Utils.decodeFromString("$2a$10$FKozujcHmWdulk6naR/XveW3x46hWPnRY2S/cyI/XhmjZZEOwz.bW")).toString());
//        String s = new String(Base64Utils.decodeFromString(user.getPassword()), "UTF-8");
//        logger.trace("new String:" + s);
	    mvc.perform(get(SERVICE_URI + getRandomLongBetweenLimits())
	      .with(httpBasic(operativeUser.getEmail(),TEST_DECODED_USER_PASSWORD))
//	      .header(HttpHeaders.AUTHORIZATION,
//	                    "Basic " + Base64Utils.encodeToString((user.getEmail()+":"+"ciao1234").getBytes()))
	      .contentType(MediaType.APPLICATION_JSON)
	      )
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
		User operativeUser = getFakeUser(Role.ADMIN_LEVEL);
		Candidate insertedCandidate = getFakeCandidate() ;
//		RequestMatcher rm = jsonPath("$[0].firstname", is(insertedCandidate.getFirstname()));
		long candidateId = insertedCandidate.getId().longValue() ;
		logger.trace("whenGetCandidateById_andThereIsOne_thenStatus200 - DEBUG - looking for candidateId: " + candidateId);
		mvc.perform(
				  get(SERVICE_URI + candidateId).contentType(MediaType.APPLICATION_JSON)
				      .with(httpBasic(operativeUser.getEmail(),TEST_DECODED_USER_PASSWORD))
				  )
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
		User operativeUser = getFakeUser(Role.ADMIN_LEVEL);
		CoursePage testCoursePage = getFakeCoursePage();
		CandidateStates testCandidateState = getFakeCandidateState();
		String testEmail = "aaa@aaa.it" ;
		String testFirstname = "Giuseppe" ;
		String testLastname = "Rossi" ;

		RequestBuilder request = post(SERVICE_URI)
		        .param("email", testEmail)
		        .param("firstname",testFirstname)
		        .param("lastname",testLastname)
		        .param("userId",""+ operativeUser.getId().longValue())
		        .param("insertedBy",""+ operativeUser.getId().longValue())
		        .param("courseCode",testCoursePage.getCode())
//		        .with(csrf())
		        .with(httpBasic(operativeUser.getEmail(),TEST_DECODED_USER_PASSWORD))
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