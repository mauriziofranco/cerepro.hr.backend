package centauri.academy.cerepro.backend;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import centauri.academy.cerepro.persistence.entity.Candidate;

/**
 * @author m.franco
 */
public abstract class AbstractMockModelGenerator {
	private static final Logger logger = LoggerFactory.getLogger(AbstractMockModelGenerator.class);

//	
//	protected Role getFakeRole() {
//		return getFakeRole(100);
//	}
//
//	protected Role getFakeRole(int level) {
//		Role role = new Role();
//		role.setLabel("admin");
//		role.setDescription("administrator");
//		role.setLevel(level);
//		Role roles = roleRepository.findByLevel(level);
//		if (roles == null) {
//			roleRepository.save(role);
//		}
//		return role;
//	}
//
//	protected User getFakeUser() {
//
//		return getFakeUser(100);
//	}
//
//	protected User getFakeUser(int level) {
//		User testUser = new User();
//		int random = (int) (Math.random() * 10000);
//		testUser.setEmail(random + "pippo@prova.com");
//		testUser.setPassword("pippo");
//		testUser.setFirstname("pippo");
//		testUser.setLastname("prova");
//		testUser.setDateOfBirth(LocalDate.parse(("1989-10-21")));
//		testUser.setRegdate(LocalDateTime.now());
//		testUser.setRole(getFakeRole(level).getLevel());
//		testUser.setImgpath("impPippo");
//		userRepository.save(testUser);
//		return testUser;
//	}

	protected Candidate getFakeMockCandidate() {
		logger.info("getFakeMockCandidate - START");
		long userId = 1l ;
		String code = "TEST_COURSE_CODE";
		long candidateStatesId=2l;
		LocalDateTime regdate = LocalDateTime.now();
		long insertedBy = userId ;
		String firstname = "Test_Firstname" ;
		String lastname = "Test_Lasstname" ;
		String email = "test@email.com" ;
		LocalDateTime candidacyDateTime = LocalDateTime.now();
		Candidate testCandidate = new Candidate(code,candidateStatesId, email, firstname, lastname, regdate, insertedBy, candidacyDateTime);
		return testCandidate;
	}
	
	
//	protected Candidate getFakeMockCandidateByCandidacyTimeAndCourseCode(LocalDateTime ldt, String courseCode) {
//		logger.info("getFakeMockCandidateByCandidacyTimeAndCourseCode - START");
//		Candidate testCandidate = getFakeMockCandidate();
//		testCandidate.setCourseCode(courseCode);
//		testCandidate.setCandidacyDateTime(ldt);
//		return testCandidate;
//	}
//	
//	protected SurveyInterview getFakeSurveyInterview() {
//		long surveyId = getFakeSurvey().getId();
//		long interviewId = getFakeInterview().getId();
//		SurveyInterview testSI = new SurveyInterview();
//		testSI.setInterviewId(interviewId);
//		testSI.setSurveyId(surveyId);
//		surveyInterviewRepository.save(testSI);
//		return testSI;
//	}
//
//	protected ItConsultant getFakeItConsultant() {
//		long userId = getFakeUser(Role.ITCONSULTANT_LEVEL).getId();
//		ItConsultant current = new ItConsultant(userId);
//		itConsultantRepository.save(current);
//		return current;
//	}
//
//	protected Employee getFakeEmployee() {
//		Employee employee = new Employee();
//		employee.setUserId(getFakeUser().getId());
//		employee.setDomicileCity("Milano");
//		employee.setDomicileStreetName("Viale Monza");
//		employee.setDomicileHouseNumber("10");
//		employee.setMobile("118");
//		employee.setCvExternalPath("C://documents/mycv.doc");
//		employeeRepository.save(employee);
//		return employee;
//	}
//
//	protected Survey getFakeSurvey() {
//		Survey testSurvey = new Survey();
//		testSurvey.setDescription("ciccioBello");
//		testSurvey.setTime(20L);
//		testSurvey.setLabel("pelleDiPesca");
//		surveyRepository.save(testSurvey);
//		return testSurvey;
//	}
//
//	protected Question getFakeQuestion() {
//		Question testQuestion = new Question("FakeQuestion");
//		questionRepository.save(testQuestion);
//		return testQuestion;
//	}
//	
//	protected Interview getFakeInterview() {
//		Interview testInterview = new Interview();
//		testInterview.setQuestionText("FakeQuestion");
//		interviewRepository.save(testInterview);
//		return testInterview;
//	}
//
//	protected SurveyQuestion getFakeSurveyQuestion() {
//		long questionId = getFakeQuestion().getId();
//		long surveyId = getFakeSurvey().getId();
//		SurveyQuestion testSQ = new SurveyQuestion();
//		testSQ.setSurveyId(surveyId);
//		testSQ.setQuestionId(questionId);
//		surveyQuestionRepository.save(testSQ);
//		return testSQ;
//	}
//	
//	
//
//	protected SurveyReply getFakeSurveyReply() {
//		SurveyReply testSR = new SurveyReply();
//		testSR.setUserId(getFakeUser().getId());
//		testSR.setSurveyId(getFakeSurvey().getId());
//		testSR.setStarttime(LocalDateTime.of(LocalDate.now(), LocalTime.now()));
//		testSR.setEndtime(LocalDateTime.of(LocalDate.now(), LocalTime.now()));
//		testSR.setAnswers("qualcosa");
//		testSR.setPdffilename("qualcosa");
//		testSR.setPoints("bravo");
//		surveyReplyRepository.save(testSR);
//		return testSR;
//	}
//	
//	
//	protected UserTokenSurvey getFakeUserTokenSurvey() {
//		UserTokenSurvey testUST = new UserTokenSurvey(1l);
//		List<User> users = userRepository.findAll();
//		if (users.isEmpty())
//			testUST.setUserid(getFakeUser().getId());
//		else
//			testUST.setUserid(users.get(0).getId());
//		testUST.setSurveyid(getFakeSurvey().getId());
////		testUST.setGeneratedtoken("AAABBBCCCC");
//		testUST.setExpirationdate(LocalDateTime.now());
//		userSurveyTokenRepository.save(testUST);
//		return testUST;
//	}
//
//	protected CoursePage getFakeCoursePageWithCode(String code) {
//		CoursePage testCoursePage = new CoursePage();
//		int random = (int) (Math.random() * 10000);
//		testCoursePage.setTitle("Fake title " + random);
//		testCoursePage.setBodyText("FakeBodyText " + random);
//		testCoursePage.setFileName("FakeFileName " + random);
//		testCoursePage.setCode(code);
//		coursePageRepository.save(testCoursePage);
//		return testCoursePage;
//	}
//
//	protected CoursePage getFakeCoursePage() {
//		int random = (int) (Math.random() * 10000);
//		return getFakeCoursePageWithCode("FakeFileName " + random);
//	}
//
//	protected UserTokenSurvey getFakeUserTokenSurveyExpired() {
//		UserTokenSurvey testUST = new UserTokenSurvey(1l);
//		List<User> users = userRepository.findAll();
//		if (users.isEmpty())
//			testUST.setUserid(getFakeUser().getId());
//		else
//			testUST.setUserid(users.get(0).getId());
//		testUST.setSurveyid(getFakeSurvey().getId());
////		testUST.setGeneratedtoken("AAABBBCCCC");
//		testUST.setExpirationdate(LocalDateTime.now());
//		testUST.setExpired(true);
//		userSurveyTokenRepository.save(testUST);
//		return testUST;
//	}
//
//	protected NoteTemplate getFakeNoteTemplate() {
//		NoteTemplate Note = new NoteTemplate();
//		Note.setTitle("Inter");
//		Note.setContent("dream team");
//		NoteTemplateRepository.save(Note);
//		return Note;
//	}
//	
//	protected CandidateStates getFakeCandidateStates() {
//		CandidateStates  csTest = new CandidateStates();
//		List<Role> roles = roleRepository.findAll();
//		if(roles.isEmpty()) {
//			csTest.setRoleId(getFakeRole().getId());
//		}
//		else {
//			csTest.setRoleId(roles.get(0).getId());
//		}
//		csTest.setStatusCode(1);
//		csTest.setStatusLabel("a status label");
//		csTest.setStatusDescription("a status description");
//		csTest.setStatusColor("#FF0000");
//		candidateStatesRepository.save(csTest);
//		return csTest;
//	}
//	
//	protected OriginSite getFakeOriginSite() {
//		OriginSite os = new OriginSite();
//		os.setLabel("Inter");
//		os.setDescription("dream team");
//		os.setImgpath("dream team");
//		originSiteRepository.save(os);
//		return os;
//	}
//	
//	protected NewsLetterMessage getFakeNewsLetterMessage() {
//		NewsLetterMessage newsLetterMessage = new NewsLetterMessage();
//		newsLetterMessage.setSubject("Fake subject 1");
//		newsLetterMessage.setMessage("Fake message 1");
//		newsLetterMessageRepository.save(newsLetterMessage);
//		return newsLetterMessage;
//	}
//	
//	protected Trainee getFakeTrainee() {
//		Trainee tr = new Trainee();
//		tr.setEmail("email@mail.com");
//		tr.setFirstname("bob");
//		tr.setLastname("esponja");
//		tr.setPassword("password!!");
//		traineeRepository.save(tr);
//		return tr;
//	}
}
