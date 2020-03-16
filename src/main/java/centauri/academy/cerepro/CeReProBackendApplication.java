package centauri.academy.cerepro;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@ComponentScan({"centauri.academy.cerepro"})
@EntityScan("centauri.academy.cerepro.persistence.entity")
@EnableJpaRepositories("centauri.academy.cerepro.persistence.repository")
@EnableCaching

public class CeReProBackendApplication extends SpringBootServletInitializer {

	private static final Logger logger = LoggerFactory.getLogger(CeReProBackendApplication.class);
	
	public static void main(String[] args) {
		logger.info("APPLICATION IS STARTING!!!!!!");
		ApplicationContext applicationContext = 
				SpringApplication.run(CeReProBackendApplication.class, args);
		
//		for (String name : applicationContext.getBeanDefinitionNames()) {
//			logger.info(name);
//		}
		logger.info("APPLICATION STARTED!!!!!!");
	}
	
}
