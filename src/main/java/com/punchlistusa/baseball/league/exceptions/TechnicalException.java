package com.punchlistusa.baseball.league.exceptions;

/**
 * 
 * @ClassName: TechnicalException
 * @author SKG
 * @since: 2022-02-03
 * @version 1.0
 */
public class TechnicalException extends ApplicationException {

	private static final long serialVersionUID = -2249736283502631807L;

	public TechnicalException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public TechnicalException(final String message) {
		super(message);
	}

	public TechnicalException(final Throwable cause) {
		super(cause);
	}

}
