package centauri.academy.cerepro.service;


import static org.assertj.core.api.Assertions.assertThat;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import centauri.academy.cerepro.CeReProBackendApplication;
import centauri.academy.cerepro.backend.NewsLetterMessageController;
import centauri.academy.cerepro.persistence.entity.CoursePage;
import centauri.academy.cerepro.persistence.entity.NewsLetterMessage;
import centauri.academy.cerepro.persistence.repository.CoursePageRepository;
import centauri.academy.cerepro.persistence.repository.NewsLetterMessageRepository;
import centauri.academy.cerepro.persistence.repository.NoteTemplateRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeReProBackendApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class NewsLetterMessageServiceTest {

	public static final Logger logger = LoggerFactory.getLogger(NewsLetterMessageServiceTest.class);

	
	
	
	
	
	@Spy
	private NewsLetterMessageController newsLetterMessageController;
	
	@Mock
	private NewsLetterMessageRepository newsLetterMessageRepository;
	
	@Before
	public void setup() {
		newsLetterMessageController = new NewsLetterMessageController();
	}
	

	
}
