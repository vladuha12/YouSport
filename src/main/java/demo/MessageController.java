package demo;

import java.util.Date;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@RequestMapping(
			path = "/hello",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public MessageBoundary storeMessage (@RequestBody MessageBoundary newMessage) {
		// stub implementation - in the future we will store the newMessag in a storage 
		newMessage.setCreationDate(new Date());
		return newMessage;
	}
	
	/*
	 * Testing scripts for PUT
	 * 
	 * Scenario: test put with stub server with valid input and valid URL
	 * GIVEN the server is up
	 * WHEN I PUT /hello/x using {"message":"updated message"}
	 * THEN the result HTTP STATUS 2xx 
	 *    AND the server prints to the console the id=x and the input json data message="updated message"
     *
	 * Scenario: test put with stub server with valid input and invalid URL
	 * GIVEN the server is up
	 * WHEN I PUT /hello using {"message":"updated message"}
	 * THEN the result HTTP STATUS NOT 2xx 
     *
	 * Scenario: test put with stub server with invalid input and valid URL
	 * GIVEN the server is up
	 * WHEN I PUT /hello/y using {message:"updated message"}
	 * THEN the result HTTP STATUS NOT 2xx
     *
	 * Scenario: test put with stub server with valid empty input and valid URL
	 * GIVEN the server is up
	 * WHEN I PUT /hello/y using {}
	 * THEN the result HTTP STATUS 2xx
	 *    AND the server prints to the console the id=y and the input json data message=null
     *
	 * Scenario: test put with actual server
	 * GIVEN the server contains a message with id=x and the current message within the message is "old message"
	 *    AND the server is up
	 * WHEN I PUT /hello/x using {"message":"updated message"}
	 * THEN the result HTTP STATUS 2xx 
	 *    AND the database is updated with the updated message
	 */
	@RequestMapping(method = RequestMethod.PUT,
			path = "/hello/{id}",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateExistingMessage (
			@PathVariable("id") String messageId, 
			@RequestBody MessageBoundary updatedMessage){
		System.err.println("updating message with messageId: " + messageId + " using update: " + updatedMessage);
	}

	@RequestMapping(method = RequestMethod.DELETE,
			path = "/hello/deleteallusers")
	public void deleteAllMessages (){

	}
	
	@RequestMapping(method = RequestMethod.DELETE,
			path = "/hello/deleteallitems")
	public void deleteAllItems (){

	}
	
	@RequestMapping(method = RequestMethod.DELETE,
			path = "/hello/deletealloperations")
	public void deleteAllOperations (){

	}
}




