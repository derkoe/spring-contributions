package org.springframework.contributions.strategy.entities;

import org.springframework.contributions.strategy.StringContainer;
import org.springframework.contributions.strategy.strategies.ReversedStringFormatStrategy;

/**
 * This @link{@link StringContainer} encapsulates {@link String} values which should
 * be formatted by the {@link ReversedStringFormatStrategy}
 * 
 * @author Ortwin Probst
 */
public class Reversed implements StringContainer
{
	private final String value;

	/**
	 * @param value
	 *            the {@link String} value to be formatted
	 */
	public Reversed(String value)
	{
		this.value = value;
	}

	public String getStingValue()
	{
		return value;
	}

}
