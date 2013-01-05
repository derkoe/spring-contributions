package org.springframework.contributions.ordered;

import static org.springframework.contributions.ContributionsNamespaceHandler.ORDERED_CONTRIBUTION_PREFIX;

import org.springframework.beans.factory.config.BeanReferenceFactoryBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Parses <code>contribution-ref</code> element in Spring XML config.
 *
 * @author Christian K&ouml;berl
 */
@SuppressWarnings("rawtypes")
public class OrderedContributionRefBeanDefinitionParser extends AbstractSingleBeanDefinitionParser
{
    protected Class getBeanClass(Element element)
    {
        return BeanReferenceFactoryBean.class;
    }

    protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder)
    {
        String contributionName = element.getAttribute("name");
        String beanName = ORDERED_CONTRIBUTION_PREFIX + contributionName;

        BeanDefinitionRegistry registry = parserContext.getRegistry();
        if (!registry.containsBeanDefinition(beanName))
        {
            BeanDefinitionBuilder contributionBeanBuilder =
                BeanDefinitionBuilder.rootBeanDefinition(OrderedConfigurationFactoryBean.class);

            contributionBeanBuilder.addPropertyValue("contributionList", new ManagedList());

            parserContext.getRegistry().registerBeanDefinition(beanName, contributionBeanBuilder.getBeanDefinition());
        }

        builder.addPropertyValue("targetBeanName", beanName);
    }

}
