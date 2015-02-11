package org.springframework.contributions.strategy;

/**
 * An implementation of this interface will be a strategy identifier and a {@link String} value
 * container. It will provide information of a desired string format by its object type. It will
 * also encapsulate the {@link String} value which should be formatted by the
 * {@link StringFormatStrategy}
 * 
 * @author Ortwin Probst
 */
public interface StringContainer
{
	/**
	 * 
	 * @return the {@link String} value which should be formatted
	 */
	String getStingValue();
}
