package org.springframework.contributions.util;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

// CHECKSTYLE:OFF
public class OrdererTest
{

	private static final int ITERATIONS = 1;

	@Test(expected = IllegalArgumentException.class)
	public void testReportCycle()
	{
		final List<TestEntry> list = new ArrayList<TestEntry>();

		list.add(new TestEntry("a", "after: b"));
		list.add(new TestEntry("b", "after: c"));
		list.add(new TestEntry("c", "after: a"));

		Orderer.build(list);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testReportDuplicate()
	{
		final List<TestEntry> list = new ArrayList<TestEntry>();

		list.add(new TestEntry("a"));
		list.add(new TestEntry("b"));
		list.add(new TestEntry("a"));

		Orderer.build(list);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testReportMissingConstraint()
	{
		final List<TestEntry> list = new ArrayList<TestEntry>();

		list.add(new TestEntry("a", "b"));
		list.add(new TestEntry("b"));

		Orderer.build(list);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testReportInvalidConstraint()
	{
		final List<TestEntry> list = new ArrayList<TestEntry>();

		list.add(new TestEntry("a", "nowhere: b"));
		list.add(new TestEntry("b"));

		Orderer.build(list);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testReportInvalidPattern()
	{
		final List<TestEntry> list = new ArrayList<TestEntry>();

		list.add(new TestEntry("a", "before: /*/"));
		list.add(new TestEntry("b"));

		Orderer.build(list);
	}

	@Test
	public void testWildcards()
	{
		final List<TestEntry> list = new ArrayList<TestEntry>();

		list.add(new TestEntry("myB"));
		list.add(new TestEntry("myA", "before: my*"));

		List<TestEntry> result = Orderer.build(list);
		
		Assert.assertEquals("[myA, myB]", result.toString());
	}

	@Test
	public void testComplex()
	{
		System.out.println("Complex:");
		
		final long nanos = System.nanoTime();
		final List<TestEntry> list = new ArrayList<TestEntry>();

		for (int i = 0; i < ITERATIONS; i += 1)
		{
			list.clear();

			list.add(new TestEntry("h", "after: *, before: i"));
			list.add(new TestEntry("i", "AFTER: *"));
			list.add(new TestEntry("b", "BEFORE: *"));
			list.add(new TestEntry("a", "before: /[c-z]/", "before: b"));
			list.add(new TestEntry("f"));
			list.add(new TestEntry("d", "after: c"));
			list.add(new TestEntry("e", "after: d", "before: f"));
			list.add(new TestEntry("c", "before: e"));
			list.add(new TestEntry("g", "after: r, f", "before: g"));

			if (i == 0)
			{
				System.out.println(Orderer.describe(list));
			}
			Orderer.order(list);

			Assert.assertEquals("[a, b, c, d, e, f, g, h, i]", list.toString());
		}

		System.out.println(String.format("Time: %,.6f", (double) (System.nanoTime() - nanos) / 1000000000l));
		System.out.println(list);
	}

	@Test
	public void testOrderPersistence()
	{
		System.out.println("Order Persistence:");

		final long nanos = System.nanoTime();
		final List<TestEntry> list = new ArrayList<TestEntry>();

		for (int i = 0; i < ITERATIONS; i += 1)
		{
			list.clear();

			list.add(new TestEntry("b"));
			list.add(new TestEntry("i", "after: *"));
			list.add(new TestEntry("c"));
			list.add(new TestEntry("d"));
			list.add(new TestEntry("a", "before: *"));
			list.add(new TestEntry("e"));
			list.add(new TestEntry("g"));
			list.add(new TestEntry("f", "before: g"));
			list.add(new TestEntry("h"));

			if (i == 0)
			{
				System.out.println(Orderer.describe(list));
			}
			Orderer.order(list);

			Assert.assertEquals("[a, b, c, d, e, f, g, h, i]", list.toString());
		}

		System.out.println(String.format("Time: %,.6f", (double) (System.nanoTime() - nanos) / 1000000000l));
		System.out.println(list);
	}

	@Test
	public void testWorstCase()
	{
		System.out.println("Worst Case:");
		
		final long nanos = System.nanoTime();
		final List<TestEntry> list = new ArrayList<TestEntry>();

		for (int i = 0; i < ITERATIONS; i += 1)
		{
			list.clear();

			list.add(new TestEntry("i", "after: *"));
			list.add(new TestEntry("h", "after: *", "before: i"));
			list.add(new TestEntry("g", "after: *", "before: h"));
			list.add(new TestEntry("f", "after: *", "before: g"));
			list.add(new TestEntry("e", "after: *", "before: f"));
			list.add(new TestEntry("d", "after: *", "before: e"));
			list.add(new TestEntry("c", "after: *", "before: d"));
			list.add(new TestEntry("b", "after: *", "before: c"));
			list.add(new TestEntry("a", "after: *", "before: b"));

			if (i == 0)
			{
				System.out.println(Orderer.describe(list));
			}
			Orderer.order(list);

			Assert.assertEquals("[a, b, c, d, e, f, g, h, i]", list.toString());
		}

		System.out.println(String.format("Time: %,.6f", (double) (System.nanoTime() - nanos) / 1000000000l));
		System.out.println(list);
	}

}
// CHECKSTYLE:ON
