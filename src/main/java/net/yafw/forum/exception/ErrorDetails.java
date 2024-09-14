package net.yafw.forum.exception;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public record ErrorDetails(LocalDateTime timestamp, String errorMessage, String errorDescription) {

	/**
	 * @param timestamp        timestamp of the error
	 * @param errorMessage     the error message
	 * @param errorDescription the error description
	 */
	public ErrorDetails {
	}


}
