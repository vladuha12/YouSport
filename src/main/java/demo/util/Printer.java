package demo.util;

import org.springframework.stereotype.Component;

import demo.AbstractPrinter;

@Component
public class Printer implements AbstractPrinter{
	public Printer() {
		System.err.println("new Printer was generated");
	}
	
	@Override
	public void printSomething () {
		System.err.println("hello from printer component");
	}
}
