package com.punchlistusa.baseball.league.exceptions;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
/**
 * 
* @ClassName: ApplicationException 
* @author   SKG  
* @since:    2022-02-03  
* @version  1.0
 */
@Getter
@Setter
public class ApplicationException extends Exception implements Serializable {
	private static final long serialVersionUID = 8688318360275333394L;

	public ApplicationException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ApplicationException(final String message) {
		super(message);
	}

	public ApplicationException(final Throwable cause) {
		super(cause);
	}
}
