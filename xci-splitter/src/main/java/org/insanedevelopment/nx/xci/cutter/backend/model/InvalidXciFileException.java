package org.insanedevelopment.nx.xci.cutter.backend.model;

public class InvalidXciFileException extends RuntimeException {

	private static final long serialVersionUID = 5933002440509574043L;

	public InvalidXciFileException() {
	}

	public InvalidXciFileException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidXciFileException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidXciFileException(String message) {
		super(message);
	}

	public InvalidXciFileException(Throwable cause) {
		super(cause);
	}

}
