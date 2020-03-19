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
		
		Optional<NewsLetterMessage> retToController = Optional.empty();
		if(idIsPresent(id)) {
			retToController = this.newsLetterMessageRepository.findById(id);
			log.info("NewsletterMessageService : method getById(Long id) - DEBUG -> value_is_present = " + retToController.isPresent()); 
			log.info("NewsletterMessageService : method getById(Long id) - RETURNED -> retToController = " + retToController.get().toString()); 
		}
		else {
			log.info("NewsletterMessageService : method getById(Long id) - ERROR ->   method idIsPresent return false"); 

		}
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
	
	
	public boolean create(NewsLetterMessage newsLetterMessageToSave){
		log.info("NewsletterMessageService : method create(NewsletterMessage newsLetterMessageToSave) - START - newsLetterMessageToSave :" + newsLetterMessageToSave.toString()); 
		
		NewsLetterMessage newsLetterMessage = this.newsLetterMessageRepository.save(newsLetterMessageToSave);
		
		log.info("NewsletterMessageService : method create(NewsletterMessage newsLetterMessageToSave) - DEBUG -> newsLetterMessage_created = " + (newsLetterMessage.getId() > 0)); 
		log.info("NewsletterMessageService : method create(NewsletterMessage newsLetterMessageToSave) - END"); 
		
		return newsLetterMessage.getId() > 0;
	}

	
	/**
	 * 
	 * */
	public boolean update(NewsLetterMessage dataToUpdate,Long id){
		log.info("NewsletterMessageService : method update(NewsletterMessage dataToUpdate,Long id) - START  -dataToUpdate: " + dataToUpdate.toString()+"-id:"+id); 
		boolean retValue = this.idIsPresent(id);
		
		log.info("NewsletterMessageService : method update(NewsletterMessage dataToUpdate,Long id) - DEBUG -> retValue = " + retValue); 
		if(retValue) {
			Optional<NewsLetterMessage> currentNewsletterMessage = newsLetterMessageRepository.findById(id);
			log.info("NewsletterMessageService : method update(NewsletterMessage dataToUpdate,Long id) - DEBUG -> currentNewsletterMessage = " + currentNewsletterMessage.toString()); 
			NewsLetterMessage newsLetterMessagePut = currentNewsletterMessage.get();
			newsLetterMessagePut.setMessage(dataToUpdate.getMessage());
			newsLetterMessagePut.setSubject(dataToUpdate.getSubject());
			// save currentNewsletterMessage obejct
			log.info("NewsletterMessageService : method update(NewsletterMessage dataToUpdate,Long id) - DEBUG -> newsLetterMessagePut = " + newsLetterMessagePut.toString()); 
			newsLetterMessageRepository.saveAndFlush(newsLetterMessagePut);
			log.info("NewsletterMessageService : method update(NewsletterMessage dataToUpdate,Long id) - DEBUG -> newsLetterMessageRepository = " + newsLetterMessageRepository.toString()); 
		}
		else {
			log.info("NewsletterMessageService : method update(NewsletterMessage dataToUpdate,Long id) - ERROR ->   method idIsPresent return false"); 

		}
		log.info("NewsletterMessageService : method update(NewsletterMessage dataToDelete,Long id) - END"); 
		return retValue;
	}
	
	
	public boolean delete(Long idDataToDelete){
		log.info("NewsletterMessageService : method delete(Long idDataToDelete) - START : id-" + idDataToDelete); 
		boolean retValue=idIsPresent(idDataToDelete);
		
		if(retValue) {
			Optional<NewsLetterMessage> retrieve = this.newsLetterMessageRepository.findById(idDataToDelete);
			this.newsLetterMessageRepository.delete(retrieve.get());
			log.info("NewsletterMessageService : method delete(Long idDataToDelete) - DEBUG -> value_retrieve_is_present = " + retrieve.isPresent()); 
		}
		else {
			log.info("NewsletterMessageService : method delete(Long idDataToDelete)- ERROR ->   method idIsPresent return false"); 

			
		}
		log.info("NewsletterMessageService : method delete(Long idDataToDelete) - RETURNED -> retValue = " + retValue); 
		log.info("NewsletterMessageService :method delete(Long idDataToDelete) - END"); 
		return retValue;
	}

	
	public boolean deleteAll(){
		boolean retValue=false;
		log.info("NewsletterMessageService : method deleteAll() - START"); 
		try {
			this.newsLetterMessageRepository.deleteAll();
			retValue=true;
			log.info("NewsletterMessageService : method deleteAll () - DEBUG -> retValue = "+ retValue);
		} catch (Exception ex) {
			retValue = false;
			log.info("NewsletterMessageService : method deleteAll () - ERROR -> List entity NewsletterMessage to deleteAll not can be null", ex);
			ex.printStackTrace();
		}
		
		log.info("NewsletterMessageService : method deleteAll() - RETURNED -> retValue = " + retValue); 
		log.info("NewsletterMessageService : method deleteAll() - END"); 
		return retValue;
	}
	
	public boolean idIsPresent(Long id){
		log.info("NewsletterMessageService : method idIsPresent(Long id) - START : id-" + id); 
		
		boolean retValue=false;
		Optional<NewsLetterMessage> retrieve = this.newsLetterMessageRepository.findById(id);
		
		log.info("NewsletterMessageService : method idIsPresent(Long id) - DEBUG -> value_retrieve_is_present = " + retrieve.isPresent()); 

		if(retrieve.isPresent()) {
			
			log.info("NewsletterMessageService : method idIsPresent(Long id) - DEBUG -> value_retrieve = " + retrieve.get().toString()); 
			retValue = true;
			log.info("NewsletterMessageService : method idIsPresent(Long id) - DEBUG -> retValue = " + retValue); 
			
		}
		log.info("NewsletterMessageService : method idIsPresent(Long id) - RETURNED -> retValue = " + retValue); 
		log.info("NewsletterMessageService :method idIsPresent(Long id) - END"); 
		return retValue;
	}
	
	
//	public boolean delete(NewsLetterMessage dataToDelete){
//		log.info("NewsletterMessageService : method delete(NewsletterMessage dataToDelete) - START : " + dataToDelete.toString()); 
//		boolean retValue = this.delete(dataToDelete.getId());
//		log.info("NewsletterMessageService : method delete(NewsletterMessage dataToDelete) - END"); 
//		return retValue;
//	}

	
	
	
}
