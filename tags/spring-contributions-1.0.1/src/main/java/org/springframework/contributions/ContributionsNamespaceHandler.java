package org.springframework.contributions;

import org.springframework.beans.factory.xml.NamespaceHandler;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.contributions.mapped.MappedContributionBeanDefinitionParser;
import org.springframework.contributions.mapped.MappedContributionRefBeanDefinitionParser;
import org.springframework.contributions.ordered.ContributionBeanDefinitionDecorator;
import org.springframework.contributions.ordered.OrderedContributionBeanDefinitionParser;
import org.springframework.contributions.ordered.OrderedContributionRefBeanDefinitionParser;

/**
 * Spring namespace handler for contributions.
 *
 * @author Christian K&ouml;berl
 */
public class ContributionsNamespaceHandler extends NamespaceHandlerSupport implements NamespaceHandler
{
    /**
     * XML namespace for contributions.
     */
    public static final String CONTRIBUTION_NAMESPACE = "http://www.springframework.org/schema/contributions";

    /**
     * Prefix for mapped contributions.
     */
    public static final String MAPPED_CONTRIBUTION_PREFIX = "org.springframework.contributions.mapped.";

    /**
     * Prefix for ordered contributions.
     */
    public static final String ORDERED_CONTRIBUTION_PREFIX = "org.springframework.contributions.ordered.";

    /**
     * {@inheritDoc}
     */
    public void init()
    {
        ContributionBeanDefinitionDecorator contributionBeanDefinitionDecorator =
            new ContributionBeanDefinitionDecorator();
        registerBeanDefinitionDecoratorForAttribute("contributeTo", contributionBeanDefinitionDecorator);
        registerBeanDefinitionDecorator("contribute", contributionBeanDefinitionDecorator);

        registerBeanDefinitionParser("contribution-ref", new OrderedContributionRefBeanDefinitionParser());
        registerBeanDefinitionParser("contribution", new OrderedContributionBeanDefinitionParser());

        registerBeanDefinitionParser("mapped-contribution", new MappedContributionBeanDefinitionParser());
        registerBeanDefinitionParser("mapped-contribution-ref", new MappedContributionRefBeanDefinitionParser());
    }
}
