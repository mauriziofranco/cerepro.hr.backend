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

	public final static Long ID_TEST = 1l;
	public final static Long ID_Empty_TEST = 999l;
	public final static String MESSAGE_SUBJECT_TEST = "messageSubjectServiceTest";
	public final static String MESSAGE_TEXT_TEST = "messageTextServiceTest";

	public static final Logger logger = LoggerFactory.getLogger(NewsLetterMessageServiceIntegrationTest.class);

	@Autowired
	private NewsLetterMessageService newsLetterMessageService;

	@Before
	@After
	public void initTable() {
		newsLetterMessageService.deleteAll();
	}

	@Test
	public void testGetAllFullOk() {
		logger.info("TEST - testGetAllFullOk - START");

		List<NewsLetterMessage> newsLetterMessagesList = newsLetterMessageService.getAll();
        assertEquals(0, newsLetterMessagesList.size());
        
        

		logger.info("TEST - testGetAllFullOk - END");
	}
	
}
