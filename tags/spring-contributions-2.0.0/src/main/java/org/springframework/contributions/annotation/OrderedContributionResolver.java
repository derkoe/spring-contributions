package org.springframework.contributions.annotation;

import static org.springframework.contributions.ContributionsNamespaceHandler.ORDERED_CONTRIBUTION_PREFIX;

import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

/**
 * A service which can be used to retrieve a ordered contribution bean.
 * This service should be used in a beans java configuration method for getting a needed contribution.
 * 
 * @author Ortwin Probst
 *
 * @param <T>
 */
public class OrderedContributionResolver<T extends Object>
{
	private final Log LOG = LogFactory.getLog(OrderedContributionResolver.class);

	private ApplicationContext context;

	public OrderedContributionResolver(ApplicationContext context)
	{
		this.context = context;
	}

	/**
	 * 
	 * @param contribution the name of the desired ordered contribution bean
	 * @return the contribution bean identified by its name, or an empty list if no contribution was found
	 */
	@SuppressWarnings("unchecked")
    public List<T> resolve(String contribution)
	{
		try
		{
			return (List<T>) context.getBean(ORDERED_CONTRIBUTION_PREFIX + contribution);
		}
		//TODO check this; here is the Java-Config way different to the XML-Config, which would throw an exception
		catch (Exception e)
		{
			LOG.warn("Coluld not recieve the ordered contribution bean named '" + contribution + "'");
		}
		return Collections.emptyList();
	}
}
