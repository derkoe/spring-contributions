package org.springframework.contributions.ordered;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.contributions.util.DictionaryComparator;
import org.springframework.contributions.util.Orderable;
import org.springframework.contributions.util.Orderer;

/**
 * Spring factory bean for the ordered contribution list.
 *
 * @author Christian K&ouml;berl
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class OrderedConfigurationFactoryBean extends AbstractFactoryBean<List>
{
	private static final Comparator<Orderable> COMPARATOR = new DictionaryComparator();

	private List<OrderedContribution> contributionList;

	/**
	 * Set the contribution List, typically populated via XML "contribute" element.
	 * 
	 * @param contributionList list of {@link OrderedContribution}s
	 */
	public void setContributionList(final List<OrderedContribution> contributionList)
	{
		if (contributionList == null)
		{
			throw new IllegalArgumentException("'sourceList' is required");
		}

		final List<OrderedContribution> copiedList = new ArrayList<OrderedContribution>(contributionList);

		// alphabetically ordering of the list
		// this is necessary to create a consistent ordering over all JVMs
		Collections.sort(copiedList, COMPARATOR);
		Orderer.order(copiedList);

		this.contributionList = Collections.unmodifiableList(copiedList);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected List createInstance() throws Exception
	{
		if (contributionList == null)
		{
			throw new IllegalArgumentException("'sourceList' is required");
		}

		final List result = new ArrayList(contributionList.size());

		for (final OrderedContribution contribution : contributionList)
		{
			result.add(contribution.getBeanValueOrReference());
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<?> getObjectType()
	{
		return List.class;
	}
}
