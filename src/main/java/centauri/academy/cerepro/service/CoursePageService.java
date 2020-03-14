/**
 * 
 */
package centauri.academy.cerepro.service;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import centauri.academy.cerepro.persistence.entity.CoursePage;
import centauri.academy.cerepro.persistence.repository.CoursePageRepository;

/**
 * @author m.franco@proximainformatica.com
 *
 */
@Service 
public class CoursePageService {
	public static final Logger logger = LoggerFactory.getLogger(CoursePageService.class);
	
	@Autowired
	private CoursePageRepository cpRepository;
	
    public CoursePage getCoursePageByCode(String code) {
        return cpRepository.findByCode(code);
    }
	
	/**
	 * Check if course_code exists into coursePage table
	 * If no, provides a default value.
	 * @return a String representative of a valid course page code
	 */
	public String checkCoursePageCode(String code) {
		logger.info("checkCoursePageCode START - code: " + code);
		if ((code==null) || (code.trim().length()==0)) 
		{
			return CoursePage.GENERIC_CANDIDATURE_CODE ;
			}
        CoursePage current = cpRepository.findByCode(code);
        if (current!=null) 
        	return current.getCode();
//        else return cpRepository.findByCode(CoursePage.GENERIC_CANDIDATURE_CODE).getCode() ;
        else 
        	return CoursePage.GENERIC_CANDIDATURE_CODE ;
	}
	
}
