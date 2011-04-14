package org.springframework.contributions.mapped;

import static org.springframework.contributions.ContributionsNamespaceHandler.MAPPED_CONTRIBUTION_PREFIX;

import java.util.Map;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.MapFactoryBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Parser for mapped contribution bean definitions.
 *
 * @author Christian K&ouml;berl
 */
public class MappedContributionBeanDefinitionParser implements BeanDefinitionParser
{
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("rawtypes")
    public BeanDefinition parse(Element element, ParserContext parserContext)
    {
        String contributionName = element.getAttribute("to");
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(MapFactoryBean.class);

        element.setAttribute("merge", "true");
        Map parsedMap = parserContext.getDelegate().parseMapElement(element, builder.getRawBeanDefinition());

        final String beanName = MAPPED_CONTRIBUTION_PREFIX + contributionName;
        BeanDefinition beanDefinition;
        BeanDefinitionRegistry registry = parserContext.getRegistry();
        if (registry.containsBeanDefinition(beanName))
        {
            beanDefinition = registry.getBeanDefinition(beanName);
            beanDefinition.getPropertyValues().addPropertyValue("sourceMap", parsedMap);

        }
        else
        {
            builder.addPropertyValue("sourceMap", parsedMap);
            beanDefinition = builder.getBeanDefinition();
            registry.registerBeanDefinition(beanName, beanDefinition);
        }

        return beanDefinition;
    }
}
