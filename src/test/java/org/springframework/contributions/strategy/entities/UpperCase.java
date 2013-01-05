/**
 * 
 */
package org.springframework.contributions.strategy.entities;

import org.springframework.contributions.strategy.StringContainer;

/**
 * This @link{@link StringContainer} encapsulates {@link String} values which should
 * be formatted by the {@link UpperCaseStringFormatStrategy}
 * 
 * @author Ortwin Probst
 */
public class UpperCase implements StringContainer
{

	private final String value;

	/**
	 * @param value
	 *            the {@link String} value to be formatted
	 */
	public UpperCase(String value)
	{
		this.value = value;
	}

	public String getStingValue()
	{
		return value;
	}

}
