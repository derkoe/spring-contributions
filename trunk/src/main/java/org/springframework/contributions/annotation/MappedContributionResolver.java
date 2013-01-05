package org.springframework.contributions.annotation;

import static org.springframework.contributions.ContributionsNamespaceHandler.MAPPED_CONTRIBUTION_PREFIX;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.Log4JLogger;
import org.springframework.context.ApplicationContext;

/**
 * A service which can be used to retrieve a mapped contribution bean.
 * This service should be used in a beans java configuration method for getting a needed contribution.
 * 
 * @author Ortwin Probst
 *
 * @param <T>
 */
public class MappedContributionResolver<T extends Map<?, ?>>
{
	private final Log LOG = LogFactory.getLog(MappedContributionResolver.class);

	private ApplicationContext context;

	public MappedContributionResolver(ApplicationContext context)
	{
		this.context = context;
	}

	/**
	 * 
	 * @param contribution the name of the desired mapped contribution bean
	 * @return the contribution bean identified by its name, or an empty list if no contribution was found
	 */
	public T resolve(String contribution)
	{
		try
		{
			return (T)context.getBean(MAPPED_CONTRIBUTION_PREFIX + contribution);
		}
		catch (Exception e)
		{
			LOG.warn("Coluld not recieve the mapped contribution bean named '" + contribution + "'");
		}
		return (T)new HashMap();
	}
}
