package integration.tests.rest.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import centauri.academy.cerepro.persistence.entity.Candidate;
import centauri.academy.cerepro.persistence.entity.CandidateStates;
import centauri.academy.cerepro.persistence.entity.CoursePage;
import centauri.academy.cerepro.persistence.entity.Role;
import centauri.academy.cerepro.persistence.entity.User;
import centauri.academy.cerepro.persistence.repository.CandidateStatesRepository;
import centauri.academy.cerepro.persistence.repository.CoursePageRepository;
import centauri.academy.cerepro.persistence.repository.RoleRepository;
import centauri.academy.cerepro.persistence.repository.UserRepository;
import centauri.academy.cerepro.service.CandidateService;

/**
 * @author m.franco
 */
public abstract class AbstractIntegrationTests {
	private static final Logger logger = LoggerFactory.getLogger(AbstractIntegrationTests.class);

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CoursePageRepository coursePageRepository;
	@Autowired
	private CandidateStatesRepository candidateStatesRepository;
	@Autowired
	private CandidateService candidateService;

	
	protected Role getFakeRole() {
		return getFakeRole((int)getRandomLongBetweenLimits ());
	}

	protected Role getFakeRole(int level) {
		Role role = new Role();
		role.setLabel("admin");
		role.setDescription("administrator");
		role.setLevel(level);
		Role roles = roleRepository.findByLevel(level);
		if (roles == null) {
			roleRepository.save(role);
		}
		return role;
	}

	protected User getFakeUser() {

		return getFakeUser(new Random().nextInt());
	}

	protected User getFakeUser(int level) {
		User testUser = new User();
		testUser.setEmail(getRandomLongBetweenLimits () + "pippo@prova.com");
		testUser.setPassword("pippo");
		testUser.setFirstname("pippo");
		testUser.setLastname("prova");
		testUser.setDateOfBirth(LocalDate.parse(("1989-10-21")));
		testUser.setRegdate(LocalDateTime.now());
		testUser.setRole(getFakeRole(level).getLevel());
		testUser.setImgpath("impPippo");
		userRepository.save(testUser);
		return testUser;
	}
	
	protected CoursePage getFakeCoursePageWithCode(String code) {
		CoursePage testCoursePage = new CoursePage();
		int random = (int)getRandomLongBetweenLimits ();
		testCoursePage.setTitle("Fake title " + random);
		testCoursePage.setBodyText("FakeBodyText " + random);
		testCoursePage.setFileName("FakeFileName " + random);
		testCoursePage.setCode(code);
		coursePageRepository.save(testCoursePage);
		return testCoursePage;
	}

	protected CoursePage getFakeCoursePage() {
		return getFakeCoursePageWithCode("FakeFileName " + new Random().nextInt());
	}
	
	protected CandidateStates getFakeCandidateState() {
		logger.info("getFakeCandidateState - START");
		CandidateStates  csTest = new CandidateStates();
		List<Role> roles = roleRepository.findAll();
		if(roles.isEmpty()) {
			csTest.setRoleId(getFakeRole().getId());
		}
		else {
			csTest.setRoleId(roles.get(0).getId());
		}
		csTest.setStatusCode(1);
		csTest.setStatusLabel("a status label");
		csTest.setStatusDescription("a status description");
		csTest.setStatusColor("#FF0000");
		candidateStatesRepository.save(csTest);
		logger.debug("getFakeCandidateState - END - candidate state inserted: {} ", csTest);
		return csTest;
	}

	protected Candidate getFakeCandidate() {
		logger.info("getFakeCandidate - START");
		long userId = getFakeUser(Role.JAVA_COURSE_CANDIDATE_LEVEL).getId();
		String code = getFakeCoursePage().getCode();
		long candidateStatesId=getFakeCandidateState().getId();
		logger.debug("getFakeCandidate - DEBUG - candidateStatesId: " + candidateStatesId);
		//Candidate testCandidate = new Candidate(userId, code,candidateStatesId);
		LocalDateTime regdate = LocalDateTime.now();
		long insertedBy = userId ;
		String firstname = "Test_Firstname" ;
		String lastname = "Test_Lasstname" ;
		String email = "test@email.com" ;
		LocalDateTime candidacyDateTime = LocalDateTime.now();
		Candidate testCandidate = new Candidate(userId, code,candidateStatesId, email, firstname, lastname, regdate, insertedBy, candidacyDateTime);
		candidateService.insert(testCandidate);
		return testCandidate;
	}
	
//	protected Candidate getFakeCandidateByCandidacyTimeAndCourseCode(LocalDateTime ldt, String courseCode) {
//		long userId = getFakeUser(Role.JAVA_COURSE_CANDIDATE_LEVEL).getId();
//		long candidateStatesId=getFakeCandidateStates().getId();
//		Candidate testCandidate = new Candidate(userId, courseCode, candidateStatesId);
//		testCandidate.setCandidacyDateTime(ldt);
//		candidateRepository.save(testCandidate);
//		return testCandidate;
//	}
	
	protected Candidate getFakeCandidateByCandidacyTimeAndCourseCode(LocalDateTime ldt, String courseCode) {
		logger.info("getFakeCandidateByCandidacyTimeAndCourseCode - START");
		Candidate testCandidate = getFakeCandidate();
		testCandidate.setCourseCode(courseCode);
		testCandidate.setCandidacyDateTime(ldt);
		candidateService.update(testCandidate);
		return testCandidate;
	}
	
	public long getRandomLongBetweenLimits () {
		long leftLimit = 100L;
	    long rightLimit = 1000L;
	    long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
	    logger.trace("getRandomLongBetweenLimits GENERATED: " + generatedLong);
		return generatedLong ;
	}
	
}
