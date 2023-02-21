/**
 * 
 */
package centauri.academy.cerepro.backend;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import centauri.academy.cerepro.rest.response.statistics.course_code.CourseCodeStatisticsModel;
import centauri.academy.cerepro.service.StatisticsService;

/**
 * @author maurizio
 *
 */
@RestController
@RequestMapping("/api/v1/statistics")
public class StatisticsController {
    public static final Logger logger = LoggerFactory.getLogger(StatisticsController.class);	

    @Autowired
	private StatisticsService statisticsService ;
    
	@GetMapping("/")
	public ResponseEntity<List<CourseCodeStatisticsModel>> getCourseCodeStatisticsModelList() {
		List<CourseCodeStatisticsModel> list = statisticsService.getAll();
		
		if (list.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
}
