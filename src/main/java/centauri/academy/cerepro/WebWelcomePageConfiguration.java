/**
 * 
 */
package centauri.academy.cerepro;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Configure the welcome page
 * 
 * @author m.franco@proximainformatica.com
 * 
 */
@Configuration
public class WebWelcomePageConfiguration extends WebMvcConfigurerAdapter implements WebMvcConfigurer {

	/**
	 * Redirect a user to the relative areas welcome pages.
	 * I.E.: for /candidati redirect should be /candidati/index.html
	 * 
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/candidati").setViewName("forward:/candidati/index.html");
		registry.addViewController("/").setViewName("forward:/index.html");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
		super.addViewControllers(registry);
	}

}
