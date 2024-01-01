package net.yafw.forum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

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
	private static final long serialVersionUID = 1L;

}
