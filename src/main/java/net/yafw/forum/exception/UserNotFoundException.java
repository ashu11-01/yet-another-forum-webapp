package net.yafw.forum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends Exception {

	public UserNotFoundException(String string) {
		super(string);
	}

	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 1L;
	
	
}
