/**
 * 
 */
package centauri.academy.cerepro.integration.tests.service;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.proxima.common.mail.MailUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import centauri.academy.cerepro.CeReProBackendApplication;

/**
 * @author maurizio
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeReProBackendApplication.class)
public class ManilManagerIntegrationTest {

	public static final Logger logger = LoggerFactory.getLogger(ManilManagerIntegrationTest.class);
	
	@Test
	public void testSendSimpleMailSentOk(){
		
//		assertEquals(0,this.newsLetterMessageService.getAll().size());
//		
//		int numEntitiesCreated=4;
//		for(int cont=0; cont<numEntitiesCreated ;cont++) {
//			
//			this.newsLetterMessageService.create(new NewsLetterMessage(MESSAGE_SUBJECT_TEST_FIRST+cont,MESSAGE_TEXT_TEST_FIRST+cont));
//			logger.info(cont+"");
//		}
//		assertEquals(numEntitiesCreated,this.newsLetterMessageService.getAll().size());
		logger.info("testSendSimpleMailSentOk - START");
		boolean sent = MailUtility.sendSimpleMail("m.franco@proximanetwork.it", "cerepro.hr.backend integration test", "cerepro.hr.backend integration test - DESCRIPTION");
		logger.info("testSendSimpleMailSentOk - END - sent: " + sent);
		assertTrue(sent);
		
	}
	
}
