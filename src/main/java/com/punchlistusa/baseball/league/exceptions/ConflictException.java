package com.punchlistusa.baseball.league.exceptions;

/**
 * 
 * @ClassName: ConflictException
 * @author SKG
 * @since: 2022-02-03
 * @version 1.0
 */
public class ConflictException extends BusinessException {
	private static final long serialVersionUID = -2249736283502631807L;

	public ConflictException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ConflictException(final String message) {
		super(message);
	}

	public ConflictException(final Throwable cause) {
		super(cause);
	}
}
