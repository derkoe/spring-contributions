package org.springframework.contributions.mapped;

import static org.springframework.contributions.ContributionsNamespaceHandler.MAPPED_CONTRIBUTION_PREFIX;

import java.util.Map;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.MapFactoryBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

public class MappedContributionUtils
{
	public static BeanDefinition addContribution(String contributionName, Map map, BeanDefinitionRegistry registry)
	{
		final String beanName = MAPPED_CONTRIBUTION_PREFIX + contributionName;
        BeanDefinition beanDefinition;
        if (registry.containsBeanDefinition(beanName))
        {
            beanDefinition = registry.getBeanDefinition(beanName);
            beanDefinition.getPropertyValues().addPropertyValue("sourceMap", map);

        }
        else
        {
        	BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(MapFactoryBean.class);
            builder.addPropertyValue("sourceMap", map);
            beanDefinition = builder.getBeanDefinition();
            registry.registerBeanDefinition(beanName, beanDefinition);
        }

        return beanDefinition;
	}
}
