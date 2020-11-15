package app;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	private AbstractPrinter printer;
	
	@Autowired
	public void setPrinter(AbstractPrinter printer) {
		this.printer = printer;
		System.err.println("setPrinter...");
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		//this is a test
	}
	
	@PostConstruct
	public void afterInit() {
		System.err.println("afterInit...");
		this.printer.printSomething();
	}
}
