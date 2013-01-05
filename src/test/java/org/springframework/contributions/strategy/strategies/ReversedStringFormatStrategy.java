/**
 * 
 */
package org.springframework.contributions.strategy.strategies;

import org.springframework.contributions.strategy.StringFormatStrategy;
import org.springframework.contributions.strategy.StringContainer;
import org.springframework.contributions.strategy.entities.Reversed;

/**
 * Implementation of an {@link StringFormatStrategy} which returns a reverted value of the
 * {@link String} encapsulated in the {@link Reversed} {@link StringContainer}
 * strategy identification container. Applying this strategy to a {@link String} value 'foobar' will
 * return the reversed {@link String} value 'raboof'
 * 
 * @author Ortwin Probst
 */
public class ReversedStringFormatStrategy implements StringFormatStrategy<Reversed>
{

	public String format(Reversed valueContainer)
	{
		return revert(valueContainer.getStingValue());
	}

	private String revert(String value)
	{
		if (value.length() != 0)
		{
			return revert(value.substring(1, value.length())) + value.charAt(0);
		}
		return "";
	}

}
