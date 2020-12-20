package dts;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static String APPLICATION_NAME;
	public static String ID_DELIMITER = "#";

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Value("${spring.application.name:demodemo}")
	public void setHelperName(String applicationName) {
		APPLICATION_NAME = applicationName;
	}

}
