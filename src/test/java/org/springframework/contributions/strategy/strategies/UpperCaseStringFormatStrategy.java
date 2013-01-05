package org.springframework.contributions.strategy.strategies;

import org.springframework.contributions.strategy.StringFormatStrategy;
import org.springframework.contributions.strategy.StringContainer;
import org.springframework.contributions.strategy.entities.UpperCase;

/**
 * Implementation of an {@link StringFormatStrategy} which returns a upper case value of the
 * {@link String} encapsulated in the {@link UpperCase} {@link StringContainer}
 * strategy identification container. Applying this strategy to a {@link String} value 'foobar' will
 * return the reversed {@link String} value 'FOOBAR'
 * 
 * @author Ortwin Probst
 */
public class UpperCaseStringFormatStrategy implements StringFormatStrategy<UpperCase>
{

	public String format(UpperCase valueContainer)
	{
		return valueContainer.getStingValue().toUpperCase();
	}

}
