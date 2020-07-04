package com.digishaala.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomMessageException extends Exception {

	private static final long serialVersionUID = 1L;

	public CustomMessageException(String message) {
		super(message);
	}
}
