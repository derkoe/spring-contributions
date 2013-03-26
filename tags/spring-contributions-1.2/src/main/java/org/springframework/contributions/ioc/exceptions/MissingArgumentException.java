package org.springframework.contributions.ioc.exceptions;

public class MissingArgumentException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8335377402868091691L;

	public MissingArgumentException(String message)
	{
		super(message);
	}
}
