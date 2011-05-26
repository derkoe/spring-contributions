package org.springframework.contributions.ordered;

import static org.springframework.contributions.ContributionsNamespaceHandler.CONTRIBUTION_NAMESPACE;
import static org.springframework.contributions.ContributionsNamespaceHandler.ORDERED_CONTRIBUTION_PREFIX;

import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Parses <code>contribution</code> tags in Spring XML config.
 * @author Christian K&ouml;berl
 */
public class OrderedContributionBeanDefinitionParser implements BeanDefinitionParser
{
    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public BeanDefinition parse(final Element element, final ParserContext parserContext)
    {
        String contributionName = element.getAttribute("to");
        String beanName = ORDERED_CONTRIBUTION_PREFIX + contributionName;

        BeanDefinitionRegistry registry = parserContext.getRegistry();
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
            contributionList = new ManagedList();
            builder.addPropertyValue("contributionList", contributionList);
            beanDefinition = builder.getBeanDefinition();
            
            // FIX by ORT
            registry.registerBeanDefinition(beanName, beanDefinition);
        }

        NodeList entryList = element.getElementsByTagNameNS(CONTRIBUTION_NAMESPACE, "entry");
        for (int i = 0; i < entryList.getLength(); i++)
        {
            Element entry = (Element) entryList.item(i);
            String name = entry.getAttribute("name");
            String constraints = entry.getAttribute("constraints");
            Object beanValueOrReference = parserContext.getDelegate().parsePropertyValue(entry, null, name);
            contributionList.add(OrderContributionUtils.createContributionBeanDefinition(
                name,
                beanValueOrReference,
                constraints));
        }
        
        return beanDefinition;
    }
}
