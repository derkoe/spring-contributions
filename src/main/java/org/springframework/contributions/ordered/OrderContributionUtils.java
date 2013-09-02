package org.springframework.contributions.ordered;

import static org.springframework.contributions.ContributionsNamespaceHandler.ORDERED_CONTRIBUTION_PREFIX;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.ManagedList;

/**
 * Utils for {@link OrderedContribution} bean definition.
 *
 * @author Christian K&ouml;berl
 * @author Ortwin Probst
 */
public final class OrderContributionUtils
{
    private OrderContributionUtils()
    {
    }

    public static BeanDefinition parse(String contributionName, OrderedContributionBeenContext bean,
    	BeanDefinitionRegistry registry)
    {
    	List<OrderedContributionBeenContext> oneBean = new ArrayList<OrderedContributionBeenContext>(1);
    	oneBean.add(bean);
    	return parse(contributionName, oneBean, registry);
    }

    public static BeanDefinition parse(String contributionName, List<OrderedContributionBeenContext> beans,
    	BeanDefinitionRegistry registry)
    {
        String beanName = ORDERED_CONTRIBUTION_PREFIX + contributionName;

        BeanDefinition beanDefinition;
        List contributionList;
        if (registry.containsBeanDefinition(beanName))
        {
            beanDefinition = registry.getBeanDefinition(beanName);
            contributionList = (List) beanDefinition.getPropertyValues().getPropertyValue("contributionList")
                .getValue();
        }
        else
        {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder
                .rootBeanDefinition(OrderedConfigurationFactoryBean.class);
            builder.setScope("singleton");
            contributionList = new ManagedList();
            builder.addPropertyValue("contributionList", contributionList);
            beanDefinition = builder.getBeanDefinition();

            registry.registerBeanDefinition(beanName, beanDefinition);
        }

        for(OrderedContributionBeenContext beanContext : beans)
        {
	        contributionList.add(OrderContributionUtils.createContributionBeanDefinition(beanContext));
        }
        
        return beanDefinition;
    }

    static BeanDefinition createContributionBeanDefinition(OrderedContributionBeenContext contributionBeenContext)
    {
        BeanDefinitionBuilder contributionBuilder = BeanDefinitionBuilder
            .genericBeanDefinition(OrderedContribution.class);
        contributionBuilder.addConstructorArgValue(contributionBeenContext.beanName);
        contributionBuilder.addConstructorArgValue(contributionBeenContext.beanValueOrReference);
        contributionBuilder.addConstructorArgValue(contributionBeenContext.constraints);
        return contributionBuilder.getBeanDefinition();
    }

}
