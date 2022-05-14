package com.punchlistusa.baseball.league.exceptions;

/**
 * 
 * @ClassName: BusinessException
 * @author SKG
 * @since: 2022-02-03
 * @version 1.0
 */
public class BusinessException extends ApplicationException {

	private static final long serialVersionUID = -2249736283502631807L;

	public BusinessException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public BusinessException(final String message) {
		super(message);
	}

	public BusinessException(final Throwable cause) {
		super(cause);
	}

}
