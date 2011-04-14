package org.springframework.contributions.ordered;

import java.util.Arrays;

import org.springframework.contributions.util.Orderable;

/**
 * Holder class for ordered contribution.
 *
 * @author Christian K&ouml;berl
 */
public class OrderedContribution implements Orderable
{
	private final String beanName;

	private final Object beanValueOrReference;

	private final String[] constraints;

	/**
	 * Create ordered contribution.
	 * 
	 * @param beanName the bean name
	 * @param beanValueOrReference the Spring reference or value
	 * @param constraints ordering constraints separated via ","
	 */
	public OrderedContribution(final String beanName, final Object beanValueOrReference,
		final String... constraints)
	{
		super();

		this.beanName = beanName;
		this.beanValueOrReference = beanValueOrReference;
		this.constraints = constraints;
	}

	public String getBeanName()
	{
		return beanName;
	}

	public Object getBeanValueOrReference()
	{
		return beanValueOrReference;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getOrderableId()
	{
		return beanName;
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getOrderableConstraints()
	{
		return constraints;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		return "OrderedContribution [beanName=" + beanName + ", constraints=" + Arrays.toString(constraints) + "]";
	}
}
