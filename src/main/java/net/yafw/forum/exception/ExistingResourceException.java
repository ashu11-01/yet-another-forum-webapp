package net.yafw.forum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.CONFLICT)
public class ExistingResourceException extends Exception {

	public ExistingResourceException(String message) {
		super(message);
	}
	
	public ExistingResourceException(String message, Throwable cause) {
		super(message,cause);	
	}
	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 1L;

}
