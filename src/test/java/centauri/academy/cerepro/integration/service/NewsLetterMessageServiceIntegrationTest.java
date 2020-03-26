package centauri.academy.cerepro.integration.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class NewsLetterMessageServiceIntegrationTest {
	
	// sistemare contatore
	public  static Long ID_TEST_FIRST ;
	public  static Long ID_TEST_SECOND;
	public  static Long ID_Empty_TEST;
	public final static String MESSAGE_SUBJECT_TEST_FIRST= "messageSubjectServiceTest1";
	public final static String MESSAGE_TEXT_TEST_FIRST = "messageTextServiceTest1";
	public final static String MESSAGE_SUBJECT_TEST_SECOND = "messageSubjectServiceTest2";
	public final static String MESSAGE_TEXT_TEST_SECOND = "messageTextServiceTest2";
	public final static String MESSAGE_SUBJECT_TEST_UPDATE= "messageSubjectServiceUpdate";
	public final static String MESSAGE_TEXT_TEST_UPDATE = "messageTextServiceTestUpdate";
	
	//public List<NewsLetterMessage> newsLetterMessagesList;

	public static final Logger logger = LoggerFactory.getLogger(NewsLetterMessageServiceIntegrationTest.class);

	@Autowired
	private NewsLetterMessageService newsLetterMessageService;

	@Before
	@After
	public void initTable() {
		newsLetterMessageService.deleteAll();
	}
	
	
	
	/*
	 *deleteAll 
	 *
	 *service (getAll,create,deleteAll)
	 */
	@Test
	public void testDeleteAllOk() {
		logger.info("TEST - testDeleteAllOk - START");

		List<NewsLetterMessage> newsLetterMessagesList;
		newsLetterMessagesList = newsLetterMessageService.getAll();
		assertEquals(0, newsLetterMessagesList.size());
//		logger.info("TEST - testDeleteAllOk - Debug: newsLetterMessagesList size - "+ newsLetterMessagesList.size());
//		logger.info("TEST - testDeleteAllOk - Debug: Create new NewsLetterMessage  first");
		
		this.newsLetterMessageService.create(new NewsLetterMessage(MESSAGE_SUBJECT_TEST_FIRST,MESSAGE_TEXT_TEST_FIRST));
		newsLetterMessagesList = newsLetterMessageService.getAll();
		assertEquals(1, newsLetterMessagesList.size());
//		logger.info("TEST - testDeleteAllOk - Debug: newsLetterMessagesList size - "+ newsLetterMessagesList.size());

//		logger.info("TEST - testDeleteAllOk - Debug: Create new NewsLetterMessage  second");
		this.newsLetterMessageService.create(new NewsLetterMessage(MESSAGE_SUBJECT_TEST_SECOND,MESSAGE_TEXT_TEST_SECOND));
		newsLetterMessagesList = newsLetterMessageService.getAll();
		assertEquals(2, newsLetterMessagesList.size());
//		logger.info("TEST - testDeleteAllOk - Debug: newsLetterMessagesList size - "+ newsLetterMessagesList.size());

//		logger.info("TEST - testDeleteAllOk - Debug: deleteAll");
		this.newsLetterMessageService.deleteAll();

		newsLetterMessagesList = newsLetterMessageService.getAll();
		assertEquals(0, newsLetterMessagesList.size());
//		logger.info("TEST - testDeleteAllOk - Debug: newsLetterMessagesList size - "+ newsLetterMessagesList.size());

		logger.info("TEST - testDeleteAllOk - END");

		
	}
	
//	public void listTest(String method, int size) {
//		logger.info("TEST -listTest- START");
//
//		List<NewsLetterMessage> newsLetterMessagesList;
//		newsLetterMessagesList = newsLetterMessageService.getAll();
//		assertEquals(size, newsLetterMessagesList.size());
//		logger.info("TEST - "+method+" - Debug: newsLetterMessagesList size - "+ newsLetterMessagesList.size());
//		logger.info("TEST -listTest- END");
//	}
//	
//	
//	@Test
//	public void testGetByIdOk() {
//		logger.info("TEST - testGetByIdOk - START");
//		
//		listTest("testGetByIdOk",0);
//		
//
//		logger.info("TEST - testDeleteAllOk - START");
//	}
//	
	
	
	
	
	@Test
	public void testFullOk() {
		logger.info("TEST - testFullOk - START");
		List<NewsLetterMessage> newsLetterMessagesList;
		NewsLetterMessage newsLetterMessageFirst = new NewsLetterMessage(MESSAGE_SUBJECT_TEST_FIRST,MESSAGE_TEXT_TEST_FIRST);
		NewsLetterMessage newsLetterMessageSecond = new NewsLetterMessage(MESSAGE_SUBJECT_TEST_SECOND,MESSAGE_TEXT_TEST_SECOND);
		Optional<NewsLetterMessage> newsLetterMessageOptionalFirst ;
		Optional<NewsLetterMessage> newsLetterMessageOptionalSecond ;
		Optional<NewsLetterMessage> newsLetterMessageOptionalGetById ;
		Optional<NewsLetterMessage> newsLetterMessageOptionalUpdate ;

		// id controllare come resettare il contatore automatico del db cosi inizia con 1 e 2 
		
		newsLetterMessagesList = newsLetterMessageService.getAll();
		
		/*
		 * list size 0
		 */
		logger.info("TEST - testFullOk - Debug: newsLetterMessagesList size - "+newsLetterMessagesList.size()+"");
		logger.info("TEST - testFullOk - test: test newsLetterMessagesList size - [0]==["+newsLetterMessagesList.size()+"]");
		assertEquals(0, newsLetterMessagesList.size());
		
		/*
		 *create 1 
		 */
		logger.info("TEST - testFullOk - Debug: Create new NewsLetterMessage  first");
		newsLetterMessageOptionalFirst=this.newsLetterMessageService.create(newsLetterMessageFirst);
		ID_TEST_FIRST= newsLetterMessageOptionalFirst.get().getId(); // eliminare dopo aver sistemato il contatore
		
		/*
		 * list size 1
		 */
		newsLetterMessagesList = newsLetterMessageService.getAll();
		logger.info("TEST - testFullOk - Debug: newsLetterMessagesList size - "+newsLetterMessagesList.size()+"");
		logger.info("TEST - testFullOk - test: test newsLetterMessagesList size - [1]==["+newsLetterMessagesList.size()+"]");
		assertEquals(1, newsLetterMessagesList.size());
		
		
		/**
		 * test newsLetterMessageOptionalFirst
		 */
		logger.info("TEST - testFullOk - Debug: start test newsLetterMessageOptionalFirst");
		
		logger.info("TEST - testFullOk - Test: newsLetterMessageOptionalFirst  Present  ["+true+"]==["+newsLetterMessageOptionalFirst.isPresent()+"] ");
		assertEquals(true, newsLetterMessageOptionalFirst.isPresent());
		
		
		logger.info("TEST - testFullOk - Test: newsLetterMessageOptionalFirst  ID  ["+ID_TEST_FIRST+"]==["+newsLetterMessageOptionalFirst.get().getId()+"] ");
		assertEquals(ID_TEST_FIRST, newsLetterMessageOptionalFirst.get().getId());//id controllo sistemare 

		logger.info("TEST - testFullOk - Test: newsLetterMessageOptionalFirst  subject  ["+MESSAGE_SUBJECT_TEST_FIRST+"]==["+newsLetterMessageOptionalFirst.get().getSubject()+"] ");
		assertEquals(MESSAGE_SUBJECT_TEST_FIRST , newsLetterMessageOptionalFirst.get().getSubject());
		
		logger.info("TEST - testFullOk - Test: newsLetterMessageOptionalFirst  text  ["+MESSAGE_TEXT_TEST_FIRST+"]==["+ newsLetterMessageOptionalFirst.get().getMessage()+"] ");
		assertEquals(MESSAGE_TEXT_TEST_FIRST, newsLetterMessageOptionalFirst.get().getMessage());
		
		/*
		 * create 2
		 */
		logger.info("TEST - testFullOk - Debug: Create new NewsLetterMessage  second");
		newsLetterMessageOptionalSecond=this.newsLetterMessageService.create(newsLetterMessageSecond);
		ID_TEST_SECOND= newsLetterMessageOptionalSecond.get().getId(); // eliminare dopo aver sistemato il contatore
		
		/*
		 * list size 2
		 */
		newsLetterMessagesList = newsLetterMessageService.getAll();
		logger.info("TEST - testFullOk - Debug: newsLetterMessagesList size - "+newsLetterMessagesList.size()+"");
		logger.info("TEST - testFullOk - test: test newsLetterMessagesList size - [2]==["+newsLetterMessagesList.size()+"]");
		assertEquals(2, newsLetterMessagesList.size());
		
		/*
		 * test newsLetterMessageOptionalSecond
		 */
		
		logger.info("TEST - testFullOk - Debug: start test newsLetterMessageOptionalSecond");
		
		logger.info("TEST - testFullOk - Test: newsLetterMessageOptionalSecond  Present  ["+true+"]==["+newsLetterMessageOptionalSecond.isPresent()+"] ");
		assertEquals(true, newsLetterMessageOptionalSecond.isPresent());
		
		
		logger.info("TEST - testFullOk - Test: newsLetterMessageOptionalSecond  ID  ["+ID_TEST_SECOND+"]==["+newsLetterMessageOptionalSecond.get().getId()+"] ");
		assertEquals(ID_TEST_SECOND, newsLetterMessageOptionalSecond.get().getId());//id controllo sistemare 

		logger.info("TEST - testFullOk - Test: newsLetterMessageOptionalSecond  subject  ["+MESSAGE_SUBJECT_TEST_SECOND+"]==["+newsLetterMessageOptionalSecond.get().getSubject()+"] ");
		assertEquals(MESSAGE_SUBJECT_TEST_SECOND, newsLetterMessageOptionalSecond.get().getSubject());
				
		logger.info("TEST - testFullOk - Test: newsLetterMessageOptionalSecond  text  ["+MESSAGE_TEXT_TEST_SECOND+"]==["+ newsLetterMessageOptionalSecond.get().getMessage()+"] ");
		assertEquals(MESSAGE_TEXT_TEST_SECOND, newsLetterMessageOptionalSecond.get().getMessage());
		
		/*
		 * getById
		 */
		
		newsLetterMessageOptionalGetById=this.newsLetterMessageService.getById(ID_TEST_FIRST);
		
		/*
		 * test getById
		 */
		logger.info("TEST - testFullOk - Debug: start test newsLetterMessageSecond");
		
		logger.info("TEST - testFullOk - Test: newsLetterMessageSecond  Present  ["+true+"]==["+newsLetterMessageOptionalSecond.isPresent()+"] ");
		assertEquals(true, newsLetterMessageOptionalSecond.isPresent());
		
		
		logger.info("TEST - testFullOk - Test: newsLetterMessageSecond  ID  ["+ID_TEST_SECOND+"]==["+newsLetterMessageOptionalSecond.get().getId()+"] ");
		assertEquals(ID_TEST_SECOND, newsLetterMessageOptionalSecond.get().getId());//id controllo sistemare 

		logger.info("TEST - testFullOk - Test: newsLetterMessageSecond  ID  ["+MESSAGE_SUBJECT_TEST_SECOND+"]==["+newsLetterMessageOptionalSecond.get().getSubject()+"] ");
		assertEquals(MESSAGE_SUBJECT_TEST_SECOND, newsLetterMessageOptionalSecond.get().getSubject());
				
		logger.info("TEST - testFullOk - Test: newsLetterMessageSecond  ID  ["+MESSAGE_TEXT_TEST_SECOND+"]==["+ newsLetterMessageOptionalSecond.get().getMessage()+"] ");
		assertEquals(MESSAGE_TEXT_TEST_SECOND, newsLetterMessageOptionalSecond.get().getMessage());
		


		logger.info("TEST - testFullOk - END");
	}
	
}
