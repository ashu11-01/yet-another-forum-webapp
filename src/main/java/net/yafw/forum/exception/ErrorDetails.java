package net.yafw.forum.exception;

import java.time.LocalDateTime;

public class ErrorDetails {

	private LocalDateTime timestamp;
	private String errorMessage;
	private String errorDescription;

	
	/**
	 * @param timestamp
	 * @param errorMessage
	 * @param errorDescription
	 */
	public ErrorDetails(LocalDateTime timestamp, String errorMessage, String errorDescription) {
		super();
		this.timestamp = timestamp;
		this.errorMessage = errorMessage;
		this.errorDescription = errorDescription;
	}


	public LocalDateTime getTimestamp() {
		return timestamp;
	}


	public String getErrorMessage() {
		return errorMessage;
	}


	public String getErrorDescription() {
		return errorDescription;
	}
	
	

}
