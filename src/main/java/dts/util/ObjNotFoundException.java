package dts.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ObjNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 6773472154750274424L;

	public ObjNotFoundException() {
	}

	public ObjNotFoundException(String message) {
		super(message);
	}

	public ObjNotFoundException(Throwable cause) {
		super(cause);
	}

	public ObjNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
