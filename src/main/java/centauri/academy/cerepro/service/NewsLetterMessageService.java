/**
 * 
 */
package centauri.academy.cerepro.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import centauri.academy.cerepro.persistence.entity.NewsLetterMessage;
import centauri.academy.cerepro.persistence.repository.NewsLetterMessageRepository;

/**
 * 
 * 
 * @author Jefersson Serrano
 *
 */
@Service 
public class NewsLetterMessageService {
	public static final Logger log = LoggerFactory.getLogger(NewsLetterMessageService.class);
	
	@Autowired
	private NewsLetterMessageRepository newsLetterMessageRepository;
	
	public List<NewsLetterMessage> getAll(){
		log.info("NewsletterMessageService : method getAll() - START");
		
		List<NewsLetterMessage> lisRetToController = this.newsLetterMessageRepository.findAll();
		
		log.info("NewsletterMessageService : method getAll() - RETURNED -> lisRetToController = " + lisRetToController); 
		log.info("NewsletterMessageService : method getAll() - END"); 
		return lisRetToController;
	}
	
	public Optional<NewsLetterMessage> getById(Long id){
		log.info("NewsletterMessageService :method getById(Long id) - START - : id-" + id); 
		
		Optional<NewsLetterMessage> retToController = this.newsLetterMessageRepository.findById(id);
		log.info("NewsletterMessageService : method getById(Long id) - DEBUG -> value_is_present = " + retToController.isPresent()); 
		if( retToController.isPresent()) {
			log.info("NewsletterMessageService : method getById(Long id) - RETURNED -> retToController = " + retToController.get().toString()); 
		}
		
		
		log.info("NewsletterMessageService : method getById(Long id) - RETURNED -> retToController = " + retToController); 

		log.info("NewsletterMessageService : method getById(Long id) - END"); 
		return retToController;
	}
	
	public List<NewsLetterMessage> getBySubject(String subject){
		log.info("NewsletterMessageService :method getBySubject(String subject) - START - subject : " + subject); 
		List<NewsLetterMessage> lisRetToController = this.newsLetterMessageRepository.findBySubject(subject);
		log.info("NewsletterMessageService : method getBySubject(String subject) - DEBUG -> value_is_Empty = " + lisRetToController.isEmpty()); 
		log.info("NewsletterMessageService : method getBySubject(String subject) - DEBUG -> retToController.toString = " + lisRetToController.toString()); 
		log.info("NewsletterMessageService : method getBySubject(String subject) - DEBUG -> retToController.size = " + lisRetToController.size()); 
		log.info("NewsletterMessageService : method getBySubject(String subject) - RETURNED -> retToController = " + lisRetToController); 

		log.info("NewsletterMessageService : method getBySubject(String subject) - END"); 
		return lisRetToController;
	}
	
	
	public Optional<NewsLetterMessage> create(NewsLetterMessage newsLetterMessageToSave){
		log.info("NewsletterMessageService : method create(NewsletterMessage newsLetterMessageToSave) - START - newsLetterMessageToSave :" + newsLetterMessageToSave.toString()); 
		NewsLetterMessage newsLetterMessage =this.newsLetterMessageRepository.save(newsLetterMessageToSave);
		log.info("NewsletterMessageService : method save(NewsletterMessage newsLetterMessageToSave) - DEBUG - newsLetterMessage :" + newsLetterMessage.toString()); 

		Optional<NewsLetterMessage> newsLetterMessageOptional = Optional.ofNullable(newsLetterMessage);

		log.info("NewsletterMessageService : method create(NewsletterMessage newsLetterMessageToSave) - DEBUG -> newsLetterMessage_created = " + (newsLetterMessageOptional)); 
		log.info("NewsletterMessageService : method create(NewsletterMessage newsLetterMessageToSave) - END"); 
		
		return newsLetterMessageOptional;
	}

	
	/**
	 * 
	 * */
	public Optional<NewsLetterMessage> update(NewsLetterMessage dataToUpdate,Long id){
		log.info("NewsletterMessageService : method update(NewsletterMessage dataToUpdate,Long id) - START  -dataToUpdate: " + dataToUpdate.toString()+"-id:"+id); 
		
		log.info("NewsletterMessageService : method update(NewsletterMessage dataToUpdate,Long id) - DEBUG -> isEmpty = " + newsLetterMessageRepository.findById(id).isEmpty()); 

		Optional<NewsLetterMessage> currentNewsletterMessage = newsLetterMessageRepository.findById(id);

		if(!currentNewsletterMessage.isEmpty()) {
			log.info("NewsletterMessageService : method update(NewsletterMessage dataToUpdate,Long id) - DEBUG -> currentNewsletterMessage = " + currentNewsletterMessage.toString()); 

			currentNewsletterMessage.get().setMessage(dataToUpdate.getMessage());
			currentNewsletterMessage.get().setSubject(dataToUpdate.getSubject());
			 
			log.info("NewsletterMessageService : method update(NewsletterMessage dataToUpdate,Long id) - DEBUG -> newsLetterMessagePut = " + currentNewsletterMessage.toString()); 
			newsLetterMessageRepository.saveAndFlush(currentNewsletterMessage.get());
			log.info("NewsletterMessageService : method update(NewsletterMessage dataToUpdate,Long id) - DEBUG -> newsLetterMessageRepository = " + newsLetterMessageRepository.toString()); 
		}
		else {
			log.info("NewsletterMessageService : method update(NewsletterMessage dataToUpdate,Long id) - ERROR ->   method isEmpty return tru"); 

		}
		log.info("NewsletterMessageService : method update(NewsletterMessage dataToDelete,Long id) - END"); 
		return currentNewsletterMessage;
		
		/*
		dataToUpdate.setId(id);
		if(!newsLetterMessageRepository.findById(dataToUpdate.getId()).isEmpty()) {	
			newsLetterMessageRepository.saveAndFlush(dataToUpdate);
			return Optional.ofNullable(dataToUpdate);
		}
		return Optional.empty();
		*/
		
	}
	
	
	public Optional<NewsLetterMessage> delete(Long idDataToDelete){
		log.info("NewsletterMessageService : method delete(Long idDataToDelete) - START : id-" + idDataToDelete); 
		
		Optional<NewsLetterMessage> retrieve = this.newsLetterMessageRepository.findById(idDataToDelete);
		if(!retrieve.isEmpty()) {
			this.newsLetterMessageRepository.delete(retrieve.get());
			log.info("NewsletterMessageService : method delete(Long idDataToDelete) - DEBUG -> value_retrieve_isEmpty = " + retrieve.isEmpty()); 
		}
		else {
			log.info("NewsletterMessageService : method delete(Long idDataToDelete)- ERROR ->   method isEmpty return false"); 
		}
		log.info("NewsletterMessageService : method delete(Long idDataToDelete) - RETURNED -> retrieve = " + retrieve); 
		log.info("NewsletterMessageService :method delete(Long idDataToDelete) - END"); 
		return retrieve;
	}

	
	public void deleteAll(){
		log.info("NewsletterMessageService : method deleteAll() - START"); 
		//try {
			this.newsLetterMessageRepository.deleteAll();
			/*
			log.info("NewsletterMessageService : method deleteAll () - DEBUG -> retValue = "+ retValue);
		} catch (Exception ex) {
			log.info("NewsletterMessageService : method deleteAll () - ERROR -> List entity NewsletterMessage to deleteAll not can be null", ex);
			ex.printStackTrace();
		}
		
		log.info("NewsletterMessageService : method deleteAll() - RETURNED -> retValue = " + retValue);
		*/
		log.info("NewsletterMessageService : method deleteAll() - END"); 
	}
//	
//	public boolean delete(NewsLetterMessage dataToDelete){
//		log.info("NewsletterMessageService : method delete(NewsletterMessage dataToDelete) - START : " + dataToDelete.toString()); 
//		boolean retValue = this.delete(dataToDelete.getId());
//		log.info("NewsletterMessageService : method delete(NewsletterMessage dataToDelete) - END"); 
//		return retValue;
//	}

	
	
	
}
