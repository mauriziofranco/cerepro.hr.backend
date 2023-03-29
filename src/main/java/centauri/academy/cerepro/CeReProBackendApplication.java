package centauri.academy.cerepro;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@ComponentScan({"centauri.academy.cerepro"})
@EntityScan("centauri.academy.cerepro.persistence.entity")
@EnableJpaRepositories("centauri.academy.cerepro.persistence.repository")
//@EnableCaching

public class CeReProBackendApplication extends SpringBootServletInitializer {

	private static final Logger logger = LoggerFactory.getLogger(CeReProBackendApplication.class);
	
	public static void main(String[] args) {
		logger.info("APPLICATION IS STARTING!!!!!!");
		ApplicationContext applicationContext = SpringApplication.run(CeReProBackendApplication.class, args);
//		ApplicationContext applicationContext = SpringApplication.run(CeReProBackendApplication.class, args);
//		ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(CeReProBackendApplication.class)
//				.properties("spring.config.name:env")
//				.properties("server.servlet.context-path:/ce########################################repro.beAAA")
//				.build()
//				.run(args);
//		for (String name : applicationContext.getBeanDefinitionNames()) {
//			logger.info(name);
//		}
	
//		SpringApplication application = new SpringApplication(CeReProBackendApplication.class);

//        Properties properties = new Properties();
////        properties.put("server.port", 9999);
////        properties.put("spring.main.allow-bean-definition-overriding", true);
//        application.setDefaultProperties(properties);
//
//        application.run(args);
//        
//        
//        
//        Properties prop = System.getProperties();
//		printProperties(prop);
		
//		Properties prop2 = application.get;
//		printProperties(prop);
		
		logger.info("APPLICATION STARTED!!!!!!");
	}

	public static void printProperties(Properties prop) {
		for (Object key: prop.keySet()) {
			logger.trace("PROPERTY: " + key + ": " + prop.getProperty(key.toString()));
		}
	}
	
}
