/**
 * 
 */
package centauri.academy.cerepro.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import centauri.academy.cerepro.persistence.entity.custom.SurveyReplyCustom;
import centauri.academy.cerepro.persistence.repository.surveyreply.SurveyReplyRepository;

/**
 * @author marco fulgosi
 * @author anna
 *
 */
@Service
public class SurveyReplyService {

	public static final Logger logger = LoggerFactory.getLogger(SurveyReplyService.class);

	@Autowired
	private SurveyReplyRepository surveyReplyRepository;
	
	/**
	 * Provides to delete all entities from surveyReply table
	 */
	public void deleteAll() {
		logger.debug("deleteAll - START");
		surveyReplyRepository.deleteAll();
	}
	
	public long getSurveyReplyCountForDay (LocalDate date) {
		LocalDateTime dateTimeStart = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 0, 0);
        logger.info("dateTimeStart: " + dateTimeStart);
		LocalDateTime dateTimeEnd = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 23, 59);
		logger.info("dateTimeEnd: " + dateTimeEnd);
		long count = surveyReplyRepository.getSurveyReplyCountBetweenDates(dateTimeStart, dateTimeEnd);
		return count ;
	}
	
	public List<SurveyReplyCustom> throwbackPeriods(long timeJump) {
		List<SurveyReplyCustom> toReturn = new ArrayList<SurveyReplyCustom>();
		LocalDate today = LocalDate.now();	
		SurveyReplyCustom currentCustomSurveyReply;
		for (int i = 0; i < timeJump; i++) {
			LocalDate backday = today.minusDays(i);
			currentCustomSurveyReply = new SurveyReplyCustom();		
			currentCustomSurveyReply.setDate(backday);
			currentCustomSurveyReply.setNumber(getSurveyReplyCountForDay(backday));
			toReturn.add(currentCustomSurveyReply);
		}
		return toReturn;
	}
	
	//surveyFilledInLastWeek
	public long surveyFilledInLastWeek(long period) {	
		logger.info("surveyFilledInPeriod() started");
		LocalDate today = LocalDate.now();
		SurveyReplyCustom currentSrc;
		long count = 0;
		for(int i = 6; i < period; i++) {	
			LocalDate yesterday = today.minusDays(i);
			currentSrc = new SurveyReplyCustom();
			currentSrc.setDate(yesterday);
			currentSrc.setNumber(getPeriod(yesterday));
			count += currentSrc.getNumber();
			logger.info("surveyFilledInPeriod() end");
		}
		return count;
	}
	
	public long surveyFilledInPeriod(long period) {	
		logger.info("surveyFilledInPeriod() started");
		LocalDate today = LocalDate.now();
		SurveyReplyCustom currentSrc;
		long count = 0;
		for(int i = 0; i < period; i++) {	
			LocalDate yesterday = today.minusDays(i);
			currentSrc = new SurveyReplyCustom();
			currentSrc.setDate(yesterday);
			currentSrc.setNumber(getPeriod(yesterday));
			count += currentSrc.getNumber();
			logger.info("surveyFilledInPeriod() end");
		}
		return count;
	}
	
	public long getPeriod(LocalDate date) {
		logger.info("getPeriod() started");
		LocalDateTime ldtstart = LocalDateTime.of(date.getYear(), date.getMonth() , date.getDayOfMonth(), 0, 0, 0);
		LocalDateTime ldtend = LocalDateTime.of(date.getYear(), date.getMonth() , date.getDayOfMonth(), 23, 59, 59);
		long count = surveyReplyRepository.getSurveyReplyCountToday(ldtstart, ldtend);
		logger.info("getPeriod() end");
		return count;
	}
	
	public void updatePdfName(String name, long id) {
		logger.info("updatePdfName() started");
		
		surveyReplyRepository.updatePdfFileName(name, id);
		
		logger.info("updatePdfName() end");
	}

}
