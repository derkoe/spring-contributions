/**
 * 
 */
package org.springframework.contributions.strategy.strategies;

import org.springframework.contributions.strategy.StringFormatStrategy;
import org.springframework.contributions.strategy.StringContainer;
import org.springframework.contributions.strategy.entities.BlankSeparated;

/**
 * Implementation of an {@link StringFormatStrategy} which returns a blank separated value of the
 * {@link String} encapsulated in the {@link BlankSeparated} {@link StringContainer}
 * strategy identification container. Applying this strategy to a {@link String} value 'foobar' will
 * return the blank separated {@link String} value 'f o o b a r'
 * 
 * @author Ortwin Probst
 */
public class BlankSeparatedStringFormatStrategy implements StringFormatStrategy<BlankSeparated>
{

	public String format(BlankSeparated valueContainer)
	{
		return separate(valueContainer.getStingValue());
	}

	private String separate(String value)
	{
		if (value.length() != 1)
		{
			return value.charAt(0) + " " + separate(value.substring(1, value.length()));
		}
		return value;
	}

}
