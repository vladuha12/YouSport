package dts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
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
		// newOperation.setCreatedTimestamp(new Date());
		// return newOperation;
		return (OperationBoundary) operationsHandler.invokeOperation(newOperation);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/dts/operations/{adminSpace}/{adminEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationBoundary[] getAllMessages(@PathVariable("adminSpace") String adminSpace,
			@PathVariable("adminEmail") String adminEmail) {
		return this.operationsHandler.getAllOperations(adminSpace, adminEmail).toArray(new OperationBoundary[0]);
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/dts/operations/{adminSpace}/{adminEmail}")
	public void clear(@PathVariable("adminSpace") String adminSpace, @PathVariable("adminEmail") String adminEmail) {
		operationsHandler.deleteAllActions(adminSpace, adminEmail);
	}

}
