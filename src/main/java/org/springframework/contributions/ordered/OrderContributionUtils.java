package org.springframework.contributions.ordered;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;

/**
 * Utils for {@link OrderedContribution} bean definition.
 *
 * @author Christian K&ouml;berl
 */
final class OrderContributionUtils
{
    private OrderContributionUtils()
    {
    }

    public static BeanDefinition createContributionBeanDefinition(String beanName, Object beanValueOrReference,
                                                                  String constraints)
    {
        BeanDefinitionBuilder contributionBuilder = BeanDefinitionBuilder
            .genericBeanDefinition(OrderedContribution.class);
        contributionBuilder.addConstructorArgValue(beanName);
        contributionBuilder.addConstructorArgValue(beanValueOrReference);
        contributionBuilder.addConstructorArgValue(constraints);
        return contributionBuilder.getBeanDefinition();
    }
}
