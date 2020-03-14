package centauri.academy.cerepro.backend;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import centauri.academy.cerepro.CeReProBackendApplication;
import centauri.academy.cerepro.persistence.entity.CeReProAbstractEntity;
import centauri.academy.cerepro.persistence.entity.OriginSite;
import centauri.academy.cerepro.persistence.repository.originsite.OriginSiteRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeReProBackendApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class OriginSiteControllerTest {

	public static final Logger logger = LoggerFactory.getLogger(OriginSiteControllerTest.class);

	@Spy
	private OriginSiteController originSiteController;
	@Mock
	private OriginSiteRepository originSiteRepository;

	@Before
	public void setup() {
		originSiteController = new OriginSiteController();
		ReflectionTestUtils.setField(originSiteController, "originSiteRepository", originSiteRepository);
	}

	@Test
	public void testListAllOriginSites() {
		List<OriginSite> originSiteList = new ArrayList<OriginSite>();
		originSiteList.add(new OriginSite());
		when(this.originSiteRepository.findAll()).thenReturn(originSiteList);
		ResponseEntity<List<OriginSite>> responseEntity = this.originSiteController.getAllOriginSite();
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertEquals(1, responseEntity.getBody().size());
	}

	@Test
	public void testGetOriginSiteById() {
		OriginSite testOriginSite = new OriginSite(100L, "test", "tester", "testing");
		Optional<OriginSite> currOpt = Optional.of(testOriginSite) ;
		when(this.originSiteRepository.findById(100L)).thenReturn(currOpt);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.originSiteController.getOriginSiteById(100L);
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertEquals("test", ((OriginSite)responseEntity.getBody()).getDescription());
		Assert.assertEquals("tester", ((OriginSite)responseEntity.getBody()).getImgpath());
		Assert.assertEquals("testing", ((OriginSite)responseEntity.getBody()).getLabel());
	}
	
	@Test
	public void testInsertOriginSiteSuccessfully() {
		OriginSite testOriginSite = new OriginSite(100L, "test", "tester", "testing");
		when(this.originSiteRepository.save(testOriginSite)).thenReturn(testOriginSite);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.originSiteController.createOriginSite(testOriginSite);
		Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		Assert.assertEquals("test", ((OriginSite)responseEntity.getBody()).getDescription());
		Assert.assertEquals("tester", ((OriginSite)responseEntity.getBody()).getImgpath());
		Assert.assertEquals("testing", ((OriginSite)responseEntity.getBody()).getLabel());
	}
	
	@Test
	public void testDeleteOriginSiteSuccessfully() {
		OriginSite testOriginSite = new OriginSite(100L, "test", "tester", "testing");
		Optional<OriginSite> currOpt = Optional.of(testOriginSite) ;
		when(this.originSiteRepository.findById(100L)).thenReturn(currOpt);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.originSiteController.deleteOriginSite(100L);
		Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
	}
	
	@Test
	public void testUpdateOriginSiteSuccessfully() {
		OriginSite testOriginSite = new OriginSite(100L, "test", "tester", "testing");
		Optional<OriginSite> currOpt = Optional.of(testOriginSite) ;
		when(this.originSiteRepository.findById(100L)).thenReturn(currOpt);
		testOriginSite.setDescription("testUPDATED");
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.originSiteController.updateOriginSite(100L, testOriginSite);
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertEquals("testUPDATED", ((OriginSite)responseEntity.getBody()).getDescription());
	}
	
	@After
	public void teardown() {
		originSiteController = null;
	}

}
