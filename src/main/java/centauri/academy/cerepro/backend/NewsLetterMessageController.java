/**
 * 
 */
package centauri.academy.cerepro.backend;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import centauri.academy.cerepro.persistence.entity.CeReProAbstractEntity;
import centauri.academy.cerepro.persistence.entity.CustomErrorType;
import centauri.academy.cerepro.persistence.entity.NewsLetterMessage;
import centauri.academy.cerepro.service.NewsLetterMessageService;

/**
 * @author Jefersson Serrano
 *
 */

@RestController
@RequestMapping("/api/v1/message")
public class NewsLetterMessageController {
	public static final Logger logger = LoggerFactory.getLogger(NewsLetterMessageController.class);
//	
	@Autowired
	private NewsLetterMessageService newsLetterMessageService;

	@GetMapping("/")
	public ResponseEntity<List<NewsLetterMessage>> getAll() {
		logger.info("NewsLetterMessageController  getAll - START");
		ResponseEntity<List<NewsLetterMessage>> responseToView = null;
		List<NewsLetterMessage> newsLetterMessages = this.newsLetterMessageService.getAll();
		logger.info(
				"NewsLetterMessageController method getAll() - DEBUG -> newsLetterMessages = " + newsLetterMessages);
		if (newsLetterMessages.isEmpty()) {
			logger.info(
					"NewsLetterMessageController  All NewsLetterMessages - END  Status Code:" + HttpStatus.NO_CONTENT);
			responseToView = new ResponseEntity<>(newsLetterMessages, HttpStatus.NO_CONTENT);
			logger.info("responseToView return :" + responseToView);
			return responseToView;
		}
		responseToView = new ResponseEntity<>(newsLetterMessages, HttpStatus.OK);
		logger.info("NewsLetterMessageController All NewsLetterMessages Status Code:" + HttpStatus.OK
				+ "------ return :" + responseToView);
		logger.info("NewsLetterMessageController  getAll - END");
		return responseToView;
	}
	
	/**
	 * 
	 * @param subject
	 * @return
	 */
	//posso passarli un entita newslettermessage senza fare controlli e prendermi lattributo subject, trovare altra soluzione
	@GetMapping(value = "/subject/", produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<NewsLetterMessage>> getAllBySubject(@Valid @RequestBody String subject) {
		logger.info("NewsLetterMessageController  getAllBySubject - START");
		logger.info("NewsLetterMessageController  getAllBySubject - DEBUG subject:"+subject);
		ResponseEntity<List<NewsLetterMessage>> responseToView = null;
		List<NewsLetterMessage> newsLetterMessagesBySubject = this.newsLetterMessageService.getBySubject(subject);
		logger.info(
				"NewsLetterMessageController method getAllBySubject() - DEBUG -> newsLetterMessagesBySubject = " + newsLetterMessagesBySubject);
		if (newsLetterMessagesBySubject.isEmpty()) {
			logger.info(
					"NewsLetterMessageController  All newsLetterMessagesBySubject - END  Status Code:" + HttpStatus.NO_CONTENT);
			responseToView = new ResponseEntity<>(newsLetterMessagesBySubject, HttpStatus.NO_CONTENT);
			logger.info("responseToView return :" + responseToView);
			return responseToView;
		}
		responseToView = new ResponseEntity<>(newsLetterMessagesBySubject, HttpStatus.OK);
		logger.info("NewsLetterMessageController All newsLetterMessagesBySubject Status Code:" + HttpStatus.OK
				+ "------ return :" + responseToView);
		logger.info("NewsLetterMessageController  getAllBySubject - END");
		return responseToView;
	}
	
	/**
	 * decidere quale dei due usare subject
	 * @param subject
	 * @return
	 */
	@GetMapping(value = "/subject/{subject}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<NewsLetterMessage>> getAllBySubject2(@Valid @RequestBody @PathVariable("subject")String subject) {
		logger.info("NewsLetterMessageController  getAllBySubject2 - START");
		ResponseEntity<List<NewsLetterMessage>> responseToView = null;
		List<NewsLetterMessage> newsLetterMessagesBySubject = this.newsLetterMessageService.getBySubject(subject);
		logger.info(
				"NewsLetterMessageController method getAllBySubject2() - DEBUG -> newsLetterMessagesBySubject = " + newsLetterMessagesBySubject);
		if (newsLetterMessagesBySubject.isEmpty()) {
			logger.info(
					"NewsLetterMessageController  All newsLetterMessagesBySubject - END  Status Code:" + HttpStatus.NO_CONTENT);
			responseToView = new ResponseEntity<>(newsLetterMessagesBySubject, HttpStatus.NO_CONTENT);
			logger.info("responseToView return :" + responseToView);
			return responseToView;
		}
		responseToView = new ResponseEntity<>(newsLetterMessagesBySubject, HttpStatus.OK);
		logger.info("NewsLetterMessageController All newsLetterMessagesBySubject Status Code:" + HttpStatus.OK
				+ "------ return :" + responseToView);
		logger.info("NewsLetterMessageController  getAllBySubject2 - END");
		return responseToView;
	}
	

	/*
	 * 
	 * GET NewsLetterMessage By ID
	 * 
	 */
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CeReProAbstractEntity> getNewsLetterMessageById(@PathVariable("id")  final Long  id) {
		logger.info("NewsLetterMessageController  Get by Id NewsLetterMessage - START-" + id);
		ResponseEntity<CeReProAbstractEntity> responseToView = null;
		Optional<NewsLetterMessage> newsLetterMessage = this.newsLetterMessageService.getById(id);
		if (newsLetterMessage.isEmpty()) {
			responseToView = new ResponseEntity<>(new CustomErrorType("NewsLetterMessage with id " + id + " not found"),
					HttpStatus.NOT_FOUND);

			logger.info("NewsLetterMessageController  Get by Id NewsLetterMessage - END:" + HttpStatus.NOT_FOUND);
			return responseToView;
		}
		responseToView = new ResponseEntity<>(newsLetterMessage.get(), HttpStatus.OK);

		logger.info("NewsLetterMessageController getNewsLetterMessageById Status Code:" + HttpStatus.OK
				+ "------ return :" + responseToView);
		logger.info("NewsLetterMessageController  getNewsLetterMessageById - END");
		return responseToView;
	}
	
	/**
	 * 
	 * @param newsLetterMessage
	 * @return
	 */
	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CeReProAbstractEntity> createNewsLetterMessage(
			@Valid @RequestBody final NewsLetterMessage newsLetterMessage) {
		logger.info("NewsLetterMessageController  Insert NewsLetterMessage - START - " + newsLetterMessage.toString());
		logger.info("Creating NewsLetterMessage : {}", newsLetterMessage);
		ResponseEntity<CeReProAbstractEntity> responseToView = null;

		if (this.newsLetterMessageService.create(newsLetterMessage).isEmpty()) {
			responseToView = new ResponseEntity<>(new CustomErrorType("Unable to create new NewsLetterMessage"),
					HttpStatus.CONFLICT);
			logger.info("NewsLetterMessageController createNewsLetterMessage - END:" + HttpStatus.CONFLICT);
			return responseToView;
		}

		responseToView = new ResponseEntity<>(newsLetterMessage, HttpStatus.CREATED);
		logger.info("NewsLetterMessageController createNewsLetterMessage Status Code:" + HttpStatus.CREATED
				+ "------ return :" + responseToView);

		logger.info("NewsLetterMessageController  createNewsLetterMessage - END");

		return responseToView;
	}

	
	/**
	 * 
	 * @param id
	 * @return
	 */
	//	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<CeReProAbstractEntity> delete(@PathVariable Long id) {
		logger.info("NewsLetterMessageController  delete by Id - START - id:" + id);

		ResponseEntity<CeReProAbstractEntity> responseToView = null;
		Optional<NewsLetterMessage> retrieve=this.newsLetterMessageService.delete(id);
		if (retrieve.isEmpty()) {
			responseToView = new ResponseEntity<>(
					new CustomErrorType("Unable to delete. NewsLetterMessage with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
			logger.info("NewsLetterMessageController delete by Id - END:" + HttpStatus.NOT_FOUND);
			return responseToView;
		}

		responseToView = new ResponseEntity<>(retrieve.get(),HttpStatus.NO_CONTENT);
		logger.info("test get -------------------"+retrieve.get());

		logger.info("NewsLetterMessageController delete by Id Status Code:" + HttpStatus.NO_CONTENT + "------ return :"
				+ responseToView);
		logger.info("NewsLetterMessageController  delete by Id - END");
		return responseToView;
	}

//	@RequestMapping(value = "/", method = RequestMethod.DELETE)
	@DeleteMapping(value = "/")
	public ResponseEntity<CeReProAbstractEntity> deleteAll() {
		logger.info("NewsLetterMessageController  deleteAll - START - ");

		ResponseEntity<CeReProAbstractEntity> responseToView = null;
		this.newsLetterMessageService.deleteAll();
//
//		if (!deleteAll) {
//			responseToView = new ResponseEntity<>(new CustomErrorType("Unable to delete all NewsLetterMessages"),
//					HttpStatus.BAD_REQUEST);
//			logger.info("NewsLetterMessageController deleteAll - END:" + HttpStatus.BAD_REQUEST);
//			return responseToView;
//		}
//		
		responseToView = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		logger.info("NewsLetterMessageController deleteAll Status Code:" + HttpStatus.NO_CONTENT + "------ return :"
				+ responseToView);
		logger.info("NewsLetterMessageController  deleteAll - END");

		return responseToView;
	}

	/**
	 * 
	 * @param id
	 * @param newsLetterMessage
	 * @return
	 */
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CeReProAbstractEntity> updateNewsLetterMessage(@PathVariable("id") final Long id,@Valid @RequestBody NewsLetterMessage newsLetterMessage) {
		
		logger.info("NewsLetterMessageController  updateNewsLetterMessage - START - id:"+id+"-NewsLetterMessage:"+newsLetterMessage.toString());
		ResponseEntity<CeReProAbstractEntity> responseToView=null;

		Optional<NewsLetterMessage> retrieve= this.newsLetterMessageService.update(newsLetterMessage,id);
		if(retrieve.isEmpty()) {
			responseToView = new ResponseEntity<>(new CustomErrorType("Unable to update. NewsLetterMessage with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
			logger.info("NewsLetterMessageController updateNewsLetterMessage - END:"+HttpStatus.NOT_FOUND); 
			return responseToView;
		}
		
		responseToView = new ResponseEntity<>(this.newsLetterMessageService.getById(id).get(),HttpStatus.OK);

		responseToView = new ResponseEntity<>(retrieve.get(),HttpStatus.OK);
		logger.info("NewsLetterMessageController updateNewsLetterMessage Status Code:"+HttpStatus.OK+"------ return :"+responseToView);
		logger.info("NewsLetterMessageController  updateNewsLetterMessage - END" );
		return responseToView;
	}
	
}
