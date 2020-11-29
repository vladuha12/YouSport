package dts.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dts.boundaries.OperationBoundary;
import dts.logic.OperationsService;

@RestController
public class OperationController {

	private OperationsService operationsHandler;

	@Autowired
	public void setOperationsHandler(OperationsService operationsHandler) {
		this.operationsHandler = operationsHandler;
	}

	@RequestMapping(path = "/dts/operations", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationBoundary performOperation(@RequestBody OperationBoundary newOperation) {
		newOperation.setCreatedTimestamp(new Date());
		return newOperation;
		// return (OperationBoundary) operationsHandler.invokeOperation(newOperation);
	}

}
