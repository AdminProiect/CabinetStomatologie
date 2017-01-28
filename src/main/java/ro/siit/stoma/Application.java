package ro.siit.stoma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ContextConfiguration;

import ro.siit.stoma.cfg.ApplicationConfiguration;

@SpringBootApplication
@ContextConfiguration(classes = {ApplicationConfiguration.class})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
