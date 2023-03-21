package centauri.academy.cerepro.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import centauri.academy.cerepro.persistence.entity.PositionUserOwner;
import centauri.academy.cerepro.persistence.repository.PositionUserOwnerRepository;

/**
 * 
 * @author m.peruzza@proximanetwork.it
 *
 */

@Service
public class PositionUserOwnerService {

	public static final Logger logger = LoggerFactory.getLogger(PositionUserOwnerService.class);
	
	@Autowired
	private PositionUserOwnerRepository positionUserOwnerRepository;
	
	public List<PositionUserOwner> getAll() {
		logger.debug("getAll - START");
		return (List<PositionUserOwner>)positionUserOwnerRepository.findAll();
	}
	
	public PositionUserOwner insert(PositionUserOwner positionUserOwner) {
		logger.debug("insert - START");
		return positionUserOwnerRepository.save(positionUserOwner);
	}
	
	public void deleteAll() {
		logger.debug("deleteAll - START");
		positionUserOwnerRepository.deleteAll();
	}
	
	public Optional<PositionUserOwner> getByCoursePageId(Long coursePageId) {
		logger.debug("getByCoursePageIdAndUserId - START");
		Optional<PositionUserOwner> positionUserOwner =positionUserOwnerRepository.findByCoursePageId(coursePageId);
		return positionUserOwner;
	}
	
}
