package org.springframework.contributions.ordered;

import static org.springframework.contributions.ContributionsNamespaceHandler.CONTRIBUTION_NAMESPACE;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
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
    public BeanDefinition parse(final Element element, final ParserContext parserContext)
    {
        String contributionName = element.getAttribute("to");

        NodeList entryList = element.getElementsByTagNameNS(CONTRIBUTION_NAMESPACE, "entry");
        List<OrderedContributionBeenContext> beans = new ArrayList<OrderedContributionBeenContext>();
        for (int i = 0; i < entryList.getLength(); i++)
        {
            Element entry = (Element) entryList.item(i);
            String name = entry.getAttribute("name");
            String constraints = entry.getAttribute("constraints");
            Object beanValueOrReference = parserContext.getDelegate().parsePropertyValue(entry, null, name);
            beans.add(new OrderedContributionBeenContext(name, beanValueOrReference, constraints));
        }
        
        return OrderContributionUtils.parse(contributionName, beans, parserContext.getRegistry());
    }
    
}
