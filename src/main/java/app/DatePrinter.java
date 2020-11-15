package app;

import java.util.Date;

//import org.springframework.stereotype.Component;

//@Component
public class DatePrinter implements AbstractPrinter{
	public DatePrinter() {
		System.err.println("a new DatePrinter was generated");
	}

	@Override
	public void printSomething() {
		System.err.println(new Date());
		
	}
}
