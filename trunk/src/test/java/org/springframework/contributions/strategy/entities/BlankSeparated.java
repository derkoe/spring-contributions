package org.springframework.contributions.strategy.entities;

import org.springframework.contributions.strategy.StringContainer;
import org.springframework.contributions.strategy.strategies.BlankSeparatedStringFormatStrategy;

/**
 * This @link{@link StringContainer} encapsulates {@link String} values which should
 * be formatted by the {@link BlankSeparatedStringFormatStrategy}
 * 
 * @author Ortwin Probst
 */
public class BlankSeparated implements StringContainer
{

	private final String value;

	/**
	 * @param value
	 *            the {@link String} value to be formatted
	 */
	public BlankSeparated(String value)
	{
		this.value = value;
	}

	public String getStingValue()
	{
		return value;
	}

}
