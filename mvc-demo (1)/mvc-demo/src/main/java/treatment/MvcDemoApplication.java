package treatment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ContextConfiguration;

import treatment.ems.cfg.ApplicationConfiguration;

@SpringBootApplication
@ContextConfiguration(classes = {ApplicationConfiguration.class})
public class MvcDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MvcDemoApplication.class, args);
	}
}
