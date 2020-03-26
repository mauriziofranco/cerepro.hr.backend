package centauri.academy.cerepro.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.fasterxml.jackson.datatype.jdk8.OptionalDoubleSerializer;

import centauri.academy.cerepro.CeReProBackendApplication;
import centauri.academy.cerepro.persistence.entity.NewsLetterMessage;
import centauri.academy.cerepro.persistence.repository.NewsLetterMessageRepository;
import centauri.academy.cerepro.service.NewsLetterMessageService;

/**
 * @author jefersson serrano
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeReProBackendApplication.class)
public class NewsLetterMessageServiceTest {

	public final static Long ID_TEST = 1l;
	public final static Long ID_Empty_TEST = 999l;
	public final static String MESSAGE_SUBJECT_TEST = "messageSubjectServiceTest";
	public final static String MESSAGE_TEXT_TEST = "messageTextServiceTest";

	public static final Logger logger = LoggerFactory.getLogger(NewsLetterMessageServiceTest.class);

	@Spy
	private NewsLetterMessageService newsLetterMessageService;

	@Mock
	private NewsLetterMessageRepository newsLetterMessageRepository;

	@Before
	public void setup() {
		newsLetterMessageService = new NewsLetterMessageService();
		ReflectionTestUtils.setField(newsLetterMessageService, "newsLetterMessageRepository",
				newsLetterMessageRepository);
	}

	@Test
	public void testGetAllFullOk() {
		logger.info("TEST - testGetAllFullOk - START");

		logger.info("TEST - testGetAllFullOk - DEBUG: Created newsLetterMessagesList");
		List<NewsLetterMessage> newsLetterMessagesList = new ArrayList<NewsLetterMessage>();

		logger.info("TEST - testGetAllFullOk - DEBUG: added new NewsLetterMessage");
		newsLetterMessagesList.add(new NewsLetterMessage(MESSAGE_SUBJECT_TEST, MESSAGE_TEXT_TEST));

		logger.info("TEST - testGetAllFullOk - DEBUG:" + this.newsLetterMessageRepository.findAll());

		when(this.newsLetterMessageRepository.findAll()).thenReturn(newsLetterMessagesList);

		logger.info("TEST - testGetAllFullOk - DEBUG: test1  [1]==[" + newsLetterMessageService.getAll().size() + "]");
		assertEquals(1, newsLetterMessageService.getAll().size());

		logger.info("TEST - testGetAllFullOk - DEBUG: test2  [" + MESSAGE_SUBJECT_TEST + "]==["
				+ newsLetterMessageService.getAll().get(0).getSubject() + "]");
		assertEquals(MESSAGE_SUBJECT_TEST, newsLetterMessageService.getAll().get(0).getSubject());

		logger.info("TEST - testGetAllFullOk - DEBUG: test2  [" + MESSAGE_TEXT_TEST + "]==["
				+ newsLetterMessageService.getAll().get(0).getSubject() + "]");
		assertEquals(MESSAGE_TEXT_TEST, newsLetterMessageService.getAll().get(0).getMessage());

		logger.info("TEST - testGetAllFullOk - END");
	}

	@Test
	public void testGetAllEmptyOk() {
		logger.info("TEST - testGetAllEmptyOk - START");

		logger.info("TEST - testGetAllEmptyOk - DEBUG: Created newsLetterMessagesList");
		List<NewsLetterMessage> newsLetterMessagesList = new ArrayList<NewsLetterMessage>();

		when(this.newsLetterMessageRepository.findAll()).thenReturn(newsLetterMessagesList);

		logger.info("TEST - testGetAllEmptyOk - DEBUG: test1  [0]==[" + newsLetterMessageService.getAll().size() + "]");
		assertEquals(0, newsLetterMessageService.getAll().size());

		logger.info("TEST - testGetAllEmptyOk - END");
	}

//    
//    
	@Test
	public void testGetByIdOk() {
		logger.info("TEST - testGetByIdOk - START");
		when(this.newsLetterMessageRepository.findById(ID_TEST)).thenReturn(Optional.of(new NewsLetterMessage()));
		assertEquals(true, this.newsLetterMessageService.getById(ID_TEST).isPresent());
		logger.info("TEST - testGetByIdOk - END");

	}

	@Test
	public void testGetByIdOkValues() {
		logger.info("TEST - testGetByIdOkValues - START");
		Optional<NewsLetterMessage> newsLetterMessage = Optional
				.ofNullable(new NewsLetterMessage(MESSAGE_SUBJECT_TEST, MESSAGE_TEXT_TEST));
		when(this.newsLetterMessageRepository.findById(ID_TEST)).thenReturn(newsLetterMessage);

		Optional<NewsLetterMessage> newsLetterMessageTest = this.newsLetterMessageService.getById(ID_TEST);
		logger.info("TEST - testGetByIdOkValues - DEBUG: test1  [" + true + "]==[" + newsLetterMessageTest.isPresent()
				+ "]");
		assertEquals(true, newsLetterMessageTest.isPresent());

		logger.info("TEST - testGetByIdOkValues - DEBUG: test2  [" + MESSAGE_SUBJECT_TEST + "]==["
				+ newsLetterMessageTest.get().getSubject() + "]");
		assertEquals(MESSAGE_SUBJECT_TEST, newsLetterMessageTest.get().getSubject());

		logger.info("TEST - testGetByIdOkValues - DEBUG: test3  [" + MESSAGE_TEXT_TEST + "]==["
				+ newsLetterMessageTest.get().getMessage() + "]");
		assertEquals(MESSAGE_TEXT_TEST, newsLetterMessageTest.get().getMessage());

		logger.info("TEST - testGetByIdOkValues - END");

	}

	@Test
	public void testGetByIdKo() {
		logger.info("TEST - testGetByIdKo - START");
		when(this.newsLetterMessageRepository.findById(ID_Empty_TEST)).thenReturn(Optional.empty());
		assertEquals(false, this.newsLetterMessageService.getById(ID_Empty_TEST).isPresent());
		logger.info("TEST - testGetByIdKo - END");

	}

	@Test
	public void testGetBySubjectFullOk() {
		logger.info("TEST - testGetBySubjectFullOk - START");

		List<NewsLetterMessage> newsLetterMessagesList = new ArrayList<NewsLetterMessage>();

		logger.info("TEST - testGetBySubjectFullOk - DEBUG: added new NewsLetterMessage");
		newsLetterMessagesList.add(new NewsLetterMessage(MESSAGE_SUBJECT_TEST, MESSAGE_TEXT_TEST));

		logger.info("TEST - testGetBySubjectFullOk - DEBUG:"
				+ this.newsLetterMessageRepository.findBySubject(MESSAGE_SUBJECT_TEST));

		when(this.newsLetterMessageRepository.findBySubject(MESSAGE_SUBJECT_TEST)).thenReturn(newsLetterMessagesList);

		List<NewsLetterMessage> lisRetToController = this.newsLetterMessageRepository
				.findBySubject(MESSAGE_SUBJECT_TEST);

		logger.info("TEST - testGetBySubjectFullOk - DEBUG: test1  [1]==[" + lisRetToController.size() + "]");
		assertEquals(1, lisRetToController.size());

		logger.info("TEST - testGetBySubjectFullOk - DEBUG: test2  [" + MESSAGE_SUBJECT_TEST + "]==["
				+ lisRetToController.get(0).getSubject() + "]");
		assertEquals(MESSAGE_SUBJECT_TEST, lisRetToController.get(0).getSubject());

		logger.info("TEST - testGetBySubjectFullOk - DEBUG: test2  [" + MESSAGE_TEXT_TEST + "]==["
				+ lisRetToController.get(0).getMessage() + "]");
		assertEquals(MESSAGE_TEXT_TEST, lisRetToController.get(0).getMessage());

		logger.info("TEST - testGetBySubjectFullOk - END");

	}

	@Test
	public void testGetBySubjectEmptyOk() {
		logger.info("TEST - testGetBySubjectEmptyOk - START");
		List<NewsLetterMessage> newsLetterMessagesList = new ArrayList<NewsLetterMessage>();
		when(this.newsLetterMessageRepository.findBySubject(MESSAGE_SUBJECT_TEST)).thenReturn(newsLetterMessagesList);

		List<NewsLetterMessage> lisRetToController = this.newsLetterMessageRepository
				.findBySubject(MESSAGE_SUBJECT_TEST);

		logger.info("TEST - testGetBySubjectEmptyOk - DEBUG: test1  [0]==[" + lisRetToController.size() + "]");
		assertEquals(0, lisRetToController.size());

		logger.info("TEST - testGetBySubjectEmptyOk - END");

	}

	@Test
	public void testDeleteAllOk() {
		logger.info("testDeleteAllOk - START");
		doNothing().when(this.newsLetterMessageRepository).deleteAll();
		this.newsLetterMessageService.deleteAll();
		verify(this.newsLetterMessageRepository, times(1)).deleteAll();
		logger.info("testDeleteAllOk - END");
	}

	@Test
	public void testDeleteAllOk2() {
		logger.info("testDeleteAllOk2 - START");
		doNothing().when(this.newsLetterMessageRepository).deleteAll();
		this.newsLetterMessageService.deleteAll();
		this.newsLetterMessageService.deleteAll();
		verify(this.newsLetterMessageRepository, times(2)).deleteAll();
		logger.info("testDeleteAllOk2 - END");
	}

	@Test
	public void testCreateOk() {

		logger.info("TEST - testCreateOk - START");
		NewsLetterMessage newsLetterMessage = new NewsLetterMessage(ID_TEST, MESSAGE_SUBJECT_TEST, MESSAGE_TEXT_TEST);
		NewsLetterMessage newsLetterMessage2 = new NewsLetterMessage(MESSAGE_SUBJECT_TEST, MESSAGE_TEXT_TEST);

		when(this.newsLetterMessageRepository.save(newsLetterMessage2)).thenReturn(newsLetterMessage);

		Optional<NewsLetterMessage> newsLetterMessageTest = this.newsLetterMessageService.create(newsLetterMessage2);

		logger.info("TEST - testCreateOk - DEBUG: test1 [" + true + "]==[" + newsLetterMessageTest.isPresent() + "]");
		assertEquals(true, newsLetterMessageTest.isPresent());

		logger.info("TEST - testCreateOk - DEBUG: test1 [" + MESSAGE_SUBJECT_TEST + "]==["
				+ newsLetterMessageTest.get().getSubject() + "]");
		assertEquals(MESSAGE_SUBJECT_TEST, newsLetterMessageTest.get().getSubject());

		logger.info("TEST - testCreateOk - DEBUG: test1 [" + MESSAGE_TEXT_TEST + "]==["
				+ newsLetterMessageTest.get().getMessage() + "]");
		assertEquals(MESSAGE_TEXT_TEST, newsLetterMessageTest.get().getMessage());

		logger.info("TEST - testCreateOk - END");

	}

//	@Test
//	public void testCreateKo() {
//		
//		logger.info("TEST - testCreateKo - START");
//		// creare un controllo per verificare il ko del metodo Create del service NewsLetterMessage
//		logger.info("TEST - testCreateKo - END");
//
//	}

	@Test
	public void testDeleteKo() {

		logger.info("TEST - testDeleteKo - START");
		when(this.newsLetterMessageRepository.findById(ID_Empty_TEST)).thenReturn(Optional.empty());
		Optional<NewsLetterMessage> newsLetterMessageTest = this.newsLetterMessageService.delete(ID_Empty_TEST);

		logger.info("TEST - testDeleteKo - DEBUG: test1  [" + true + "]==[" + newsLetterMessageTest.isEmpty() + "]");
		assertEquals(true, newsLetterMessageTest.isEmpty());

		logger.info("TEST - testDeleteKo - END");

	}

	@Test
	public void testDeleteOk() {
		logger.info("TEST - testDeleteOk - START");
		NewsLetterMessage newsLetterMessage = new NewsLetterMessage(ID_TEST, MESSAGE_SUBJECT_TEST, MESSAGE_TEXT_TEST);

		when(this.newsLetterMessageRepository.findById(ID_TEST)).thenReturn(Optional.ofNullable(newsLetterMessage));

		Optional<NewsLetterMessage> newsLetterMessageTest = this.newsLetterMessageService.delete(ID_TEST);

//		logger.info(
//				"TEST - testDeleteOk - DEBUG: test1  [" + ID_TEST + "]==[" + newsLetterMessageTest.get().getId() + "]");
		assertEquals(ID_TEST, newsLetterMessageTest.get().getId());

//		logger.info("TEST - testDeleteOk - DEBUG: test2  [" + MESSAGE_SUBJECT_TEST + "]==["
//				+ newsLetterMessageTest.get().getSubject() + "]");
		assertEquals(MESSAGE_SUBJECT_TEST, newsLetterMessageTest.get().getSubject());

//		logger.info("TEST - testDeleteOk - DEBUG: test3  [" + MESSAGE_TEXT_TEST + "]==["
//				+ newsLetterMessageTest.get().getMessage() + "]");
		assertEquals(MESSAGE_TEXT_TEST, newsLetterMessageTest.get().getMessage());

//		logger.info("TEST - testDeleteOk - DEBUG: test4 [" + true + "]==[" + newsLetterMessageTest.isPresent() + "]");
		assertEquals(true, newsLetterMessageTest.isPresent());
		
		
		
		verify(this.newsLetterMessageRepository, times(1)).delete(newsLetterMessage);// method deleteAll Repository
		verify(this.newsLetterMessageRepository, times(1)).findById(ID_TEST);

		logger.info("TEST - testDeleteOk - END");
	}

	@Test
	public void testUpdateOk() {

		logger.info("TEST - testUpdateOk - START");
		NewsLetterMessage newsLetterMessage = new NewsLetterMessage(MESSAGE_SUBJECT_TEST, MESSAGE_TEXT_TEST);
		when(this.newsLetterMessageRepository.findById(ID_TEST)).thenReturn(Optional
				.of(new NewsLetterMessage(ID_TEST, MESSAGE_SUBJECT_TEST + "before", MESSAGE_TEXT_TEST + " before")));
		Optional<NewsLetterMessage> newsLetterMessageTest = this.newsLetterMessageService.update(newsLetterMessage,
				ID_TEST);

		logger.info("TEST - testUpdateOk - DEBUG: test1  [" + ID_TEST + "]==[" + newsLetterMessageTest.get().getId() + "]");
		assertEquals(ID_TEST, newsLetterMessageTest.get().getId());

		logger.info("TEST - testUpdateOk - DEBUG: test2  [" + MESSAGE_SUBJECT_TEST + "]==["+ newsLetterMessageTest.get().getSubject() + "]");
		assertEquals(MESSAGE_SUBJECT_TEST, newsLetterMessageTest.get().getSubject());

		logger.info("TEST - testUpdateOk - DEBUG: test3  [" + MESSAGE_TEXT_TEST + "]==["+ newsLetterMessageTest.get().getMessage() + "]");
		assertEquals(MESSAGE_TEXT_TEST, newsLetterMessageTest.get().getMessage());
		
		logger.info("TEST - testUpdateOk - DEBUG: test4  [" + true + "]==[" + newsLetterMessageTest.isPresent() + "]");
		assertEquals(true, newsLetterMessageTest.isPresent());

		logger.info("TEST - testUpdateOk - END");

	}

	@Test
	public void testUpdateKoById() {

		logger.info("TEST - testUpdateKo - START");
		when(this.newsLetterMessageRepository.findById(ID_Empty_TEST)).thenReturn(Optional.empty());

		Optional<NewsLetterMessage> newsLetterMessageTest = this.newsLetterMessageService
				.update(new NewsLetterMessage(), ID_Empty_TEST);
		logger.info("TEST - testUpdateOk - DEBUG: test1  [" + true + "]==[" + newsLetterMessageTest.isEmpty() + "]");
		assertEquals(true, newsLetterMessageTest.isEmpty());

		logger.info("TEST - testUpdateKo - END");

	}

//	logger.info("TEST - testCreateOk - DEBUG: test1 ["++"]==["++"]");
}
