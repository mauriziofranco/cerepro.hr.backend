package centauri.academy.cerepro.backend;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import centauri.academy.cerepro.CeReProBackendApplication;
import centauri.academy.cerepro.persistence.entity.CeReProAbstractEntity;
import centauri.academy.cerepro.persistence.entity.NewsLetterMessage;
import centauri.academy.cerepro.persistence.entity.NoteTemplate;
import centauri.academy.cerepro.persistence.repository.NoteTemplateRepository;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = CeReProBackendApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
import centauri.academy.cerepro.service.NewsLetterMessageService;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeReProBackendApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class NewsLetterMessageControllerTest {

	public static final Logger logger = LoggerFactory.getLogger(NewsLetterMessageControllerTest.class);
	
	
	@Spy
	private NewsLetterMessageController newsLetterMessageController;
	
	
	@Mock
	private NewsLetterMessageService newsLetterMessageService;
	
	
	
	@Before
	public void setup() {
		newsLetterMessageController = new NewsLetterMessageController();
		ReflectionTestUtils.setField(newsLetterMessageController, "newsLetterMessageService", newsLetterMessageService);
	}
	
	
	@Test
	public void testGetAllEmpty() {
		List<NewsLetterMessage> newsLetterMessagesList = new ArrayList<NewsLetterMessage>();
		when(this.newsLetterMessageService.getAll()).thenReturn(newsLetterMessagesList);
		ResponseEntity<List<NewsLetterMessage>> responseEntity = this.newsLetterMessageController.getAll();
		
		Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
		Assert.assertEquals(0, responseEntity.getBody().size());
		Assert.assertEquals(true, responseEntity.getBody().isEmpty());
		
	}
	
	
	
	
	@After
	public void teardown() {
		newsLetterMessageController = null;
	}
	
	
}
