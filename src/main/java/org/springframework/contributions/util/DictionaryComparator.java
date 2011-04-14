package org.springframework.contributions.util;

import java.text.Collator;
import java.util.Comparator;

/**
 * A comparator for {@link Orderable} objects to sort them alphabetical (the case is ignored)
 * 
 * @author Manfred Hantschel
 */
public class DictionaryComparator implements Comparator<Orderable>
{

	/**
	 * A collator set to primary strength, which means 'a', 'A' and '&auml;' is the same
	 */
	public static final Collator DICTIONARY_COLLATOR;

	static
	{
		DICTIONARY_COLLATOR = Collator.getInstance();

		DICTIONARY_COLLATOR.setStrength(Collator.PRIMARY);
		DICTIONARY_COLLATOR.setDecomposition(Collator.CANONICAL_DECOMPOSITION);
	}

	/**
	 * {@inheritDoc}
	 */
	public int compare(final Orderable left, final Orderable right)
	{
		return DICTIONARY_COLLATOR.compare(left.getOrderableId(), right.getOrderableId());
	}

}
