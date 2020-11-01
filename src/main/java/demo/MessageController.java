package demo;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

	@RequestMapping(
		method = RequestMethod.GET,
		path = "/hello",
		produces = MediaType.APPLICATION_JSON_VALUE)
	public MessageBoundary hello() {
//		MessageBoundary message = new MessageBoundary();
//		message.setMessage("hello world");
//		return message;
		
		return new MessageBoundary("hello world");
	}
	
	@RequestMapping(
			method = RequestMethod.GET,
			path = "/hello/{username}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public MessageBoundary hello(
			@PathVariable("username") int username) {
			
		return new MessageBoundary("hello " + username);
	}
	
	@RequestMapping(
			method = RequestMethod.GET,
			path = "/hellos",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public MessageBoundary[] getMultipleValues() {
		// mockup that generates multiple values
		return new MessageBoundary[]{
				new MessageBoundary ("value #1"),
				new MessageBoundary ("value #2"),
				new MessageBoundary ("value #3")};
	}
}
