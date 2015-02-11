package org.springframework.contributions.annotation;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.context.annotation.Configuration;
import org.springframework.contributions.annotation.ContributionMapped.NoEnumKey;
import org.springframework.contributions.mapped.MappedContributionUtils;
import org.springframework.contributions.ordered.OrderContributionUtils;
import org.springframework.contributions.ordered.OrderedContributionBeenContext;
import org.springframework.core.type.MethodMetadata;
import org.springframework.util.StringUtils;

/**
 * This post processor is used to handle contribution configurations
 * defined within a class annotated with {@link Configuration}.
 * It will handle configuration for {@link Contribution} or {@link ContributionMapped} annotated configuration methods.
 * 
 * @author Ortwin Probst
 *
 */
public class AnnotationContributionPostProcessor implements BeanDefinitionRegistryPostProcessor
{

	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException
	{
		Map<String, Object> beans = beanFactory.getBeansWithAnnotation(Configuration.class);
		for (String configuration : beans.keySet())
		{
			BeanDefinition beanDefinition = beanFactory.getBeanDefinition(configuration);
			if (beanDefinition instanceof AnnotatedBeanDefinition)
			{
				AnnotatedBeanDefinition beanDef = (AnnotatedBeanDefinition) beanDefinition;
				
				//handle ordered contributions
				Set<MethodMetadata> orderedContributions = beanDef.getMetadata().
					getAnnotatedMethods(Contribution.class.getName());
				handleOrderedContributions(beanFactory, orderedContributions);

				//handle mapped contributions
				Set<MethodMetadata> mappedContributions = beanDef.getMetadata().
					getAnnotatedMethods(ContributionMapped.class.getName());
				handleMappedContributions(beanFactory, mappedContributions);
			}
		}
	}

	private void handleMappedContributions(ConfigurableListableBeanFactory beanFactory,
		Set<MethodMetadata> mappedContributions)
	{
		for (MethodMetadata metadata : mappedContributions)
		{
			Map<String, Object> attributes = metadata.getAnnotationAttributes(ContributionMapped.class.getName());
			
			String contributionName = (String)attributes.get("to");

			String beanName = getBeanName(metadata, attributes);
			Object beanValueOrReference = beanFactory.getBean(beanName);
			ManagedMap map = new ManagedMap();
			map.setMergeEnabled(true);

			Class keyClass = (Class) attributes.get("keyClass");
			String keyString = (String) attributes.get("key");
			Class keyEnumClass = (Class) attributes.get("keyEnumClass");

			if (!keyClass.equals(ContributionMapped.class))
			{
			    map.put(keyClass, beanValueOrReference);
			}
			else if (StringUtils.hasLength(keyString))
			{
				verifyUniqueKeyDefinition(beanName, map);
				map.put(keyString, beanValueOrReference);
			}
			else if (!keyEnumClass.equals(NoEnumKey.class))
			{
				verifyUniqueKeyDefinition(beanName, map);
				String enumValue = (String)attributes.get("keyEnumValue");
				map.put(Enum.valueOf(keyEnumClass, enumValue), beanValueOrReference);
			}
			else
			{
				throw new FatalBeanException("No key was given for adding the bean ' " + beanName 
					+ "to the mapped contribution '" + contributionName + "'");
			}

			MappedContributionUtils.addToContribution(contributionName, map, (BeanDefinitionRegistry)beanFactory);
		}
	}

	private void verifyUniqueKeyDefinition(String beanName, ManagedMap map)
	{
		if (!map.isEmpty())
		{
			throw new FatalBeanException("More than one mapped contribution key type was defined for the bean '" 
				+ beanName + "'");
		}
	}

	private void handleOrderedContributions(ConfigurableListableBeanFactory beanFactory,
		Set<MethodMetadata> orderedContributions)
	{
		for (MethodMetadata metadata : orderedContributions)
		{
			Map<String, Object> attributes = metadata.getAnnotationAttributes(Contribution.class.getName());
			
			String contributionName = (String)attributes.get("to");
			
			String constraints = (String)attributes.get("constraints");
			String beanName = getBeanName(metadata, attributes);
			Object beanValueOrReference = beanFactory.getBeanDefinition(beanName);
			OrderedContributionBeenContext beanContext =
				new OrderedContributionBeenContext(beanName, beanValueOrReference, constraints);
			OrderContributionUtils.addToContribution(contributionName, beanContext, (BeanDefinitionRegistry)beanFactory);
		}
	}

	private String getBeanName(MethodMetadata metadata, Map<String, Object> attributes)
	{
		String beanName = StringUtils.hasLength((String)attributes.get("name"))?
			(String)attributes.get("name") : metadata.getMethodName();
		return beanName;
	}

	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException
	{
		//unimplemented
	}

}
