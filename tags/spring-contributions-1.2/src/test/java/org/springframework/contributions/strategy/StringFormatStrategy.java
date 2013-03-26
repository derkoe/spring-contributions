package org.springframework.contributions.strategy;

/**
 * This interface has to be implemented by strategy services which want to provide {@link String}
 * formatting functionality for {@link String} values encapsulated in
 * {@link StringContainer} objects.
 * 
 * @author Ortwin Probst
 */
public interface StringFormatStrategy<T extends StringContainer>
{
	String format(T valueContainer);
}
