package centauri.academy.cerepro.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import centauri.academy.cerepro.persistence.entity.Role;
import centauri.academy.cerepro.rest.response.statistics.course_code.CourseCodeStatisticsModel;

/**
 * 
 * @author maurizio.franco@ymail.com
 *
 */
@Service
public class StatisticsService {

	public static final Logger logger = LoggerFactory.getLogger(StatisticsService.class);

//	@Autowired
//	RoleRepository roleRepository;

	/**
	 * Provides list of role entities from repository
	 * 
	 * @return List<Role>, list of Role entity objects
	 */
	public List<CourseCodeStatisticsModel> getAll() {
		logger.info("getAll - START");
//		List<Role> roles = roleRepository.findAll();
		List<CourseCodeStatisticsModel> list = new ArrayList () ;
		list.add(new CourseCodeStatisticsModel ("aaa", 36) );
		list.add(new CourseCodeStatisticsModel ("bbb", 24) );
		logger.info("getAll - END - returning " + list.size() + " items.");
		return list;
	}

}
