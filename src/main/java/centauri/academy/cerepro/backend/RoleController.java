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
import centauri.academy.cerepro.persistence.entity.Role;
import centauri.academy.cerepro.persistence.entity.custom.CustomErrorType;
import centauri.academy.cerepro.persistence.repository.RoleRepository;
import centauri.academy.cerepro.service.RoleService;



/**
 * @author maurizio
 *@author Orlando Platì
 */

@RestController
@RequestMapping("/api/v1/role")
public class RoleController {
	public static final Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	@Autowired
	private RoleRepository roleRepository ;
	@Autowired
	private RoleService roleService ;

	@GetMapping("/")
	public ResponseEntity<List<Role>> getRoles() {
		List<Role> roles = roleService.getAll();
		
		if (roles.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<>(roles, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CeReProAbstractEntity> getRoleById(@PathVariable("id") final Long id){
		Optional<Role> optRole = roleRepository.findById(id);
		
		if (!optRole.isPresent()) {
			return new ResponseEntity<>(
				new CustomErrorType("Role with id " + id + " not found"),
				HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(optRole.get(), HttpStatus.OK);
	}
	
	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CeReProAbstractEntity> createRole(
			@Valid @RequestBody final Role role) {
		logger.info("Creating Role : {}", role);
		
		if (roleRepository.findByLevel(role.getLevel()) != null) {
			
			return new ResponseEntity<>(
					new CustomErrorType(
							"Unable to create new role. A Role with level " + role.getLevel() + " already exist."),
					HttpStatus.CONFLICT);
		}
		
		Role insertedRole = roleService.insert(role);
		return new ResponseEntity<>(insertedRole, HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CeReProAbstractEntity> updateRole(
			@PathVariable("id") final Long id, @RequestBody Role role) {
	
		Optional<Role> optRole = roleRepository.findById(id);
		
		if (!optRole.isPresent()) {
			return new ResponseEntity<>(
					new CustomErrorType("Unable to upate. Role with id " + id + " not found."), 
					HttpStatus.NOT_FOUND);
		}
		
		Role currentRole = optRole.get();
	
		currentRole.setLabel(role.getLabel());
		currentRole.setDescription(role.getDescription());
		currentRole.setLevel(role.getLevel());
		// save currentUser obejct
		roleRepository.save(currentRole);
		// return ResponseEntity object
		return new ResponseEntity<>(currentRole, HttpStatus.OK);
	}
	 
	@DeleteMapping("/{id}")
	public ResponseEntity<CeReProAbstractEntity> deleteRole(@PathVariable("id") final Long id) {
		Optional<Role> optRole = roleRepository.findById(id);
		
		if (!optRole.isPresent()) {
			return new ResponseEntity<>(
					new CustomErrorType("Unable to delete. Role with id " + id + " not found."), 
					HttpStatus.NOT_FOUND);
		}
		
		roleRepository.delete(optRole.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
