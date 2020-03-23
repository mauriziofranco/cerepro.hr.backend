package centauri.academy.cerepro.service;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import centauri.academy.cerepro.CeReProBackendApplication;
import centauri.academy.cerepro.persistence.entity.Role;
import centauri.academy.cerepro.persistence.repository.RoleRepository;

/**
 * @author m.franco@proximanetwork.it
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeReProBackendApplication.class)
public class RoleServiceTest {

	public final static String ROLE_LABEL_TEST = "aaa" ;
	public final static String ROLE_DESCRIPTION_TEST = "bbb" ;
	public final static int ROLE_LEVEL_TEST = 999 ;
	
	public static final Logger logger = LoggerFactory.getLogger(RoleServiceTest.class);
 
    @Spy
    private RoleService roleService;
    
    @Mock
    private RoleRepository roleRepository ;
    
    @Before
    public void setup() {
    	roleService = new RoleService();    	
   	    ReflectionTestUtils.setField(roleService, "roleRepository", roleRepository);
    }
    
    @Test
    public void testGetAllFullOk() {
   	    logger.info("testGetAllFullOk - START");
   	    List<Role> rolesList = new ArrayList<Role>();
   	    rolesList.add(new Role(ROLE_LABEL_TEST, ROLE_DESCRIPTION_TEST, ROLE_LEVEL_TEST));   	 
   	    when(roleRepository.findAll()).thenReturn(rolesList);
	    assertEquals(1, roleService.getAll().size());
	    assertEquals(ROLE_LABEL_TEST,       roleService.getAll().get(0).getLabel());
	    assertEquals(ROLE_DESCRIPTION_TEST, roleService.getAll().get(0).getDescription());
	    assertEquals(ROLE_LEVEL_TEST, roleService.getAll().get(0).getLevel());
	    logger.info("testGetAllFullOk - END");
    }
    
    @Test
    public void testGetAllEmptyOk() {
   	    logger.info("testGetAllEmptyOk - START");
   	    List<Role> rolesList = new ArrayList<Role>();
	    when(roleRepository.findAll()).thenReturn(rolesList);
	    assertEquals(0, roleService.getAll().size());	    
	    logger.info("testGetAllEmptyOk - END");
    }

}
