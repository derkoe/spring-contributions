package org.springframework.contributions.ordered;

import static org.springframework.contributions.ContributionsNamespaceHandler.ORDERED_CONTRIBUTION_PREFIX;

import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.BeanDefinitionDecorator;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Parsers <code>contributeTo</code> attribute an contribute sub-tag of beans.
 *
 * @author Christian K&ouml;berl
 */
public class ContributionBeanDefinitionDecorator implements BeanDefinitionDecorator
{
    /**
     * {@inheritDoc}
     */
    public BeanDefinitionHolder decorate(Node node, BeanDefinitionHolder definition, ParserContext parserContext)
    {
        String beanName = definition.getBeanName();

        if (node instanceof Attr)
        {
            Attr attr = (Attr) node;
            String contributionName = attr.getValue();
            addToContributionService(
                contributionName,
                OrderContributionUtils.createContributionBeanDefinition(beanName, definition, null),
                parserContext);
        }
        else if (node instanceof Element)
        {
            Element element = (Element) node;
            String contributionName = element.getAttribute("to");
            String constraints = element.getAttribute("constraints");

            addToContributionService(
                contributionName,
                OrderContributionUtils.createContributionBeanDefinition(beanName, definition, constraints),
                parserContext);
        }

        return definition;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void addToContributionService(String contributionName, BeanDefinition contribution,
                                          ParserContext parserContext)
    {
        final String beanName = ORDERED_CONTRIBUTION_PREFIX + contributionName;
        final BeanDefinitionRegistry registry = parserContext.getRegistry();
        if (registry.containsBeanDefinition(beanName))
        {
            BeanDefinition beanDefinition = parserContext.getRegistry().getBeanDefinition(beanName);
            List list = (List) beanDefinition.getPropertyValues().getPropertyValue("contributionList").getValue();
            list.add(contribution);
        }
        else
        {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder
                .rootBeanDefinition(OrderedConfigurationFactoryBean.class);
            List contributionList = new ManagedList();
            contributionList.add(contribution);
            builder.addPropertyValue("contributionList", contributionList);
            parserContext.getRegistry().registerBeanDefinition(beanName, builder.getBeanDefinition());
        }
    }
}
