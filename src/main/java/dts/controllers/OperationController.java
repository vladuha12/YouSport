package dts.controllers;

import java.util.Date;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dts.boundaries.OperationBoundary;

@RestController
public class OperationController {

	@RequestMapping(path = "/dts/operations", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationBoundary performOperation(@RequestBody OperationBoundary newOperation) {
		newOperation.setCreatedTimestamp(new Date());
		return newOperation;
	}

}
