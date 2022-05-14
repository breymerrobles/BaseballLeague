package com.punchlistusa.baseball.league.exceptions;

/**
 * 
 * @ClassName: NotFoundException
 * @author SKG
 * @since: 2022-02-03
 * @version 1.0
 */
public class NotFoundException extends BusinessException {
	private static final long serialVersionUID = -2249736283502631807L;

	public NotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public NotFoundException(final String message) {
		super(message);
	}

	public NotFoundException(final Throwable cause) {
		super(cause);
	}
}
