package org.springframework.contributions.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

//CHECKSTYLE:OFF
public class DictionaryComparatorTest
{

	@Test
	public void test()
	{
		final List<Orderable> list = new ArrayList<Orderable>();

		list.add(new TestEntry("u"));
		list.add(new TestEntry("\u00f6"));
		list.add(new TestEntry("I"));
		list.add(new TestEntry("e"));
		list.add(new TestEntry("\u00c4"));

		Collections.sort(list, new DictionaryComparator());
		
		assert ("[\u00c4, e, I, \u00f6, u]".equals(list.toString()));
	}
}
//CHECKSTYLE:ON
