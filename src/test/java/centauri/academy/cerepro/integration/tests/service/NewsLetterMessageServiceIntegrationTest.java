package centauri.academy.cerepro.integration.tests.service;

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
import com.jayway.jsonpath.Option;

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
	public  static Long ID_Empty_TEST =999l;
	public final static String MESSAGE_SUBJECT_TEST_FIRST= "messageSubjectServiceTestFirst";
	public final static String MESSAGE_TEXT_TEST_FIRST = "messageTextServiceTestFirst";
	public final static String MESSAGE_SUBJECT_TEST_SECOND = "messageSubjectServiceTestSecond";
	public final static String MESSAGE_TEXT_TEST_SECOND = "messageTextServiceTestSecond";
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
	
	
	
	@Test
	public void testGetAllOk(){
		
		assertEquals(0,this.newsLetterMessageService.getAll().size());
		
		int numEntitiesCreated=4;
		for(int cont=0; cont<numEntitiesCreated ;cont++) {
			
			this.newsLetterMessageService.create(new NewsLetterMessage(MESSAGE_SUBJECT_TEST_FIRST+cont,MESSAGE_TEXT_TEST_FIRST+cont));
			logger.info(cont+"");
		}
		assertEquals(numEntitiesCreated,this.newsLetterMessageService.getAll().size());
		
	}
	
	
	 
	
	
	
	/*
	 *deleteAll 
	 *
	 *service (getAll,create,deleteAll)
	 */
	@Test
	public void testDeleteAllOk() {
		logger.info("TEST - testDeleteAllOk - START");

		assertEquals(0,this.newsLetterMessageService.getAll().size());
		
		this.newsLetterMessageService.create(new NewsLetterMessage(MESSAGE_SUBJECT_TEST_FIRST,MESSAGE_TEXT_TEST_FIRST));
		assertEquals(1,this.newsLetterMessageService.getAll().size());

//		logger.info("TEST - testDeleteAllOk - Debug: Create new NewsLetterMessage  second");
		this.newsLetterMessageService.create(new NewsLetterMessage(MESSAGE_SUBJECT_TEST_SECOND,MESSAGE_TEXT_TEST_SECOND));
		assertEquals(2,this.newsLetterMessageService.getAll().size());

//		logger.info("TEST - testDeleteAllOk - Debug: deleteAll");
		this.newsLetterMessageService.deleteAll();

		assertEquals(0,this.newsLetterMessageService.getAll().size());

		logger.info("TEST - testDeleteAllOk - END");		
	}
	
	
	@Test
	public void testDeleteAllEmpty() {
		logger.info("TEST - testDeleteAllEmpty - START");
		
		assertEquals(0,newsLetterMessageService.getAll().size());
		this.newsLetterMessageService.deleteAll();
		assertEquals(0, newsLetterMessageService.getAll().size());
		
		logger.info("TEST - testDeleteAllEmpty - END");		
	}
	
	
	@Test
	public void testDeleteByIdOk() {
		logger.info("TEST - testDeleteByIdOk - START");
		
		Optional<NewsLetterMessage> newsLetterMessageOptional;
		assertEquals(0,newsLetterMessageService.getAll().size());
		newsLetterMessageOptional=this.newsLetterMessageService.create(new NewsLetterMessage(MESSAGE_SUBJECT_TEST_FIRST,MESSAGE_TEXT_TEST_FIRST));
		assertEquals(true,newsLetterMessageOptional.isPresent());
		assertEquals(1,newsLetterMessageService.getAll().size());
		this.newsLetterMessageService.delete(newsLetterMessageOptional.get().getId());
		assertEquals(0, newsLetterMessageService.getAll().size());
		
		logger.info("TEST - testDeleteByIdOk - END");		
	}
	
	
	@Test
	public void testDeleteByIdKo() {
		logger.info("TEST - testDeleteByIdKo - START");
		
		assertEquals(0,newsLetterMessageService.getAll().size());
		
		Optional<NewsLetterMessage> newsLetterMessageOptional=this.newsLetterMessageService.delete(ID_Empty_TEST);
		assertEquals(true,newsLetterMessageOptional.isEmpty());
		
		assertEquals(0, newsLetterMessageService.getAll().size());

		logger.info("TEST - testDeleteByIdKo - END");		
	}
	
	
	
	
	
	@Test
	public void testFullOk() {
		logger.info("TEST - testFullOk - START");
		NewsLetterMessage newsLetterMessageFirst = new NewsLetterMessage(MESSAGE_SUBJECT_TEST_FIRST,MESSAGE_TEXT_TEST_FIRST);
		NewsLetterMessage newsLetterMessageSecond = new NewsLetterMessage(MESSAGE_SUBJECT_TEST_SECOND,MESSAGE_TEXT_TEST_SECOND);
		Optional<NewsLetterMessage> newsLetterMessageOptionalFirst ;
		Optional<NewsLetterMessage> newsLetterMessageOptionalSecond ;
		Optional<NewsLetterMessage> newsLetterMessageOptionalGetById ;
		Optional<NewsLetterMessage> newsLetterMessageOptionalUpdate ;

		// id controllare come resettare il contatore automatico del db cosi inizia con 1 e 2 
		
		
		/*
		 * list size 0
		 */
		assertEquals(0, this.newsLetterMessageService.getAll().size());
		
		/*
		 *create 1 
		 */
		logger.info("TEST - testFullOk - Debug: Create new NewsLetterMessage  first");
		newsLetterMessageOptionalFirst=this.newsLetterMessageService.create(newsLetterMessageFirst);
		ID_TEST_FIRST= newsLetterMessageOptionalFirst.get().getId(); // eliminare dopo aver sistemato il contatore
		
		/*
		 * list size 1
		 */
		assertEquals(1, this.newsLetterMessageService.getAll().size());
		
		
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
		assertEquals(2, this.newsLetterMessageService.getAll().size());
		
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
