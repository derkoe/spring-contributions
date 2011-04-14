package org.springframework.contributions.impl;

import org.springframework.contributions.Callable;

public class GenericCallable implements Callable
{
	private final StringBuilder logBuffer;

	private final String message;

	GenericCallable(StringBuilder logBuffer, String message)
	{
		this.logBuffer = logBuffer;
		this.message = message;
	}

	public void call()
	{
		logBuffer.append(message);
	}
}
