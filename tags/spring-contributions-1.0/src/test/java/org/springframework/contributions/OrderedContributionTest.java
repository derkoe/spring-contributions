package org.springframework.contributions;

import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.contributions.ordered.OrderedContribution;
import org.springframework.contributions.util.Orderer;

public class OrderedContributionTest
{
	@Test
	public void testSorting()
	{
		verifySort(Arrays.asList(//
			new OrderedContribution("Two", null, "after:One"),//
			new OrderedContribution("One", null, "before:*"),//
			new OrderedContribution("Three", null, "after:Two"),//
			new OrderedContribution("Four", null, "after:*")),// 
			Arrays.asList("One", "Two", "Three", "Four"));

		// indirect
		verifySort(Arrays.asList(//
			new OrderedContribution("Three", null),//
			new OrderedContribution("Two", null, "before:Three"),//
			new OrderedContribution("One", null, "before:Two")),//
			Arrays.asList("One", "Two", "Three"));

		try
		{
			verifySort(Arrays.asList(//
				new OrderedContribution("One", null, "after:Two"),//
				new OrderedContribution("Two", null, "after:One")),//
				Arrays.asList("Two", "One"));

			Assert.fail("Exception should have been thrown");
		}
		catch (final IllegalArgumentException e)
		{
			// ok
		}

		verifySort(Arrays.asList(//
			new OrderedContribution("One", null),//
			new OrderedContribution("Two", null, "after:One"),//
			new OrderedContribution("Three", null, "after:O*")),//
			Arrays.asList("One", "Two", "Three"));

		// keep ordering if not specified
		verifySort(Arrays.asList(//
			new OrderedContribution("b", null),//
			new OrderedContribution("i", null, "after: *"),//
			new OrderedContribution("c", null),//
			new OrderedContribution("d", null),//
			new OrderedContribution("a", null, "before: *"),//
			new OrderedContribution("e", null),//
			new OrderedContribution("g", null),//
			new OrderedContribution("f", null, "before: g"),//
			new OrderedContribution("h", null)),//
			Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i"));

		// complex
		verifySort(Arrays.asList(//
			new OrderedContribution("i", null, "AFTER: *"),//
			new OrderedContribution("h", null, "after: *", "before: i"),//
			new OrderedContribution("b", null, "BEFORE: *"),//
			new OrderedContribution("a", null, "before: /[c-z]/", "before: b"),//
			new OrderedContribution("f", null),//
			new OrderedContribution("d", null, "after: c"),//
			new OrderedContribution("e", null, "after: d", "before: f"),//
			new OrderedContribution("c", null, "before: e"),//
			new OrderedContribution("g", null, "after: r, f", "before: g")),//
			Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i"));

		// worst case
		verifySort(Arrays.asList(//
			new OrderedContribution("i", null),//
			new OrderedContribution("h", null, "after: *", "before: i"),//
			new OrderedContribution("g", null, "after: *", "before: h"),//
			new OrderedContribution("f", null, "after: *", "before: g"),//
			new OrderedContribution("e", null, "after: *", "before: f"),//
			new OrderedContribution("d", null, "after: *", "before: e"),//
			new OrderedContribution("c", null, "after: *", "before: d"),//
			new OrderedContribution("b", null, "after: *", "before: c"),//
			new OrderedContribution("a", null, "after: *", "before: b")),//
			Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i"));

		verifySort(Arrays.asList(//
			new OrderedContribution("One", null),//
			new OrderedContribution("Three", null, "after:Three,after:Two"),//
			new OrderedContribution("Two", null, "after:One")), Arrays.asList("One", "Two", "Three"));

		verifySort(Arrays.asList(//
			new OrderedContribution("Three", null),//
			new OrderedContribution("Two", null, "before:Three"),//
			new OrderedContribution("One", null, "before:Two")),//
			Arrays.asList("One", "Two", "Three"));
	}

	private void verifySort(final List<OrderedContribution> contributions, final List<String> orderedBeanNames)
	{
		final List<OrderedContribution> ordered = Orderer.build(contributions);

		for (int i = 0; i < orderedBeanNames.size(); i++)
		{
			Assert.assertEquals(orderedBeanNames.get(i), ordered.get(i).getBeanName());
		}
	}
}
