package org.springframework.contributions.ordered;

public class OrderedContributionBeenContext
{
	final String beanName;
	final Object beanValueOrReference;
	final String constraints;
    
	public OrderedContributionBeenContext(String beanName, Object beanValueOrReference,
        String constraints)
	{
		this.beanName = beanName;
		this.beanValueOrReference = beanValueOrReference;
		this.constraints = constraints;
	}
}
