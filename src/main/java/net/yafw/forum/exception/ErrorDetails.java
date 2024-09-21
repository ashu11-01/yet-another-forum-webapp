package net.yafw.forum.exception;

import java.time.LocalDateTime;


public record ErrorDetails(LocalDateTime timestamp, String errorMessage, String errorDescription) {

	/**
	 * @param timestamp        timestamp of the error
	 * @param errorMessage     the error message
	 * @param errorDescription the error description
	 */
	public ErrorDetails {
	}


}
