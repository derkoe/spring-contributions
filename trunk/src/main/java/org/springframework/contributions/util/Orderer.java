package org.springframework.contributions.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * <p>
 * Orders lists by using "before" and "after" constraints. The elements to be ordered must implement {@link Orderable}.
 * The before and after definitions may contain the explicit id, wildcards like "*" and "?" or regular expressions. The
 * regular expressions must be surrounded by "/.../". The algorithm considers explicitly named constraints more
 * important than those using wildcards or regular expressions. The ordering of elements without constraints is
 * preserved.
 * </p>
 * <p>
 * A note on performance: The complexity of the algorithm is O(n^2). But the algorithm highly depends on regular
 * expressions, if those are specified within the constraints (the wildcards are also transformed into regular
 * expressions). Compiling the expressions is quite slow. Thus the runtime of the algorithm highly depends on the number
 * of regular expressions used in the constraints and not so much on the number of elements in the list. The algorithm
 * compiles each regular expression only once and contains an optimization for "*"-constraints, since these are quite
 * common.
 * </p>
 * 
 * @author Manfred Hantschel
 */
public final class Orderer
{

	private static final String BEFORE_TYPE = "before";
	private static final String AFTER_TYPE = "after";

	private static final char JOKER_CHARACTER = '*';
	private static final char ANY_CHARACTER = '?';

	private static final String JOKER = String.valueOf(JOKER_CHARACTER);
	private static final String ANY = String.valueOf(ANY_CHARACTER);

	private static final String JOKER_REPLACEMENT = ".*";
	private static final String ANY_REPLACEMENT = ".";

	private static final Pattern JOKER_PATTERN = Pattern.compile(".*");

	private static final String REGULAR_EXPRESSION_DELIMITER = "/";

	private static final Pattern CONSTRAINT_DELIMITER_PATTERN = Pattern.compile("\\s*\\,\\s*");
	private static final Pattern WILDCARDS_TO_REGULAR_EXPRESSION_PATTERN = Pattern.compile("([^\\d\\w\\*\\?])");
	private static final String WILDCARDS_TO_REGULAR_EXPRESSION_REPLACE = "\\\\$0";

	/**
	 * A node of the net of {@link Orderable} entries
	 * 
	 * @author Manfred HANTSCHEL
	 * @param <TYPE> the type of the entry
	 */
	private static class OrderableNode<TYPE extends Orderable>
	{

		private final TYPE value;
		private final Set<OrderableNode<TYPE>> after;
		private final Set<OrderableNode<TYPE>> before;

		/**
		 * Creates a new node holding the specified value
		 * 
		 * @param value the value, may be null
		 */
		public OrderableNode(final TYPE value)
		{
			super();

			this.value = value;

			after = new LinkedHashSet<OrderableNode<TYPE>>();
			before = new LinkedHashSet<OrderableNode<TYPE>>();
		}

		/**
		 * Returns the id of the entry. Calls the {@link Orderable}.getOrderId() method of the value.
		 * 
		 * @return the id of the entry
		 */

		public String getId()
		{
			return value.getOrderableId();
		}

		/**
		 * Adds an "after" dependency to the node, if the dependency is not yet present
		 * 
		 * @param dependency the dependency
		 */
		public void after(final OrderableNode<TYPE> dependency)
		{
			after.add(dependency);

			dependency.before.add(this);
		}

		/**
		 * Returns true if this node contains the specified dependency
		 * 
		 * @param dependency the dependency
		 * @return true if this node contains the specified dependency
		 */
		public boolean contains(final OrderableNode<TYPE> dependency)
		{
			if (after.contains(dependency))
			{
				return true;
			}

			for (final OrderableNode<TYPE> current : after)
			{
				if (current.contains(dependency))
				{
					return true;
				}
			}

			return false;
		}

		/**
		 * Describes a dependency cycle
		 * 
		 * @param dependency the dependency
		 * @return the description of the cycle
		 */
		public String describe(final OrderableNode<TYPE> dependency)
		{
			if (after.contains(dependency))
			{
				return " should succeed " + dependency.getId();
			}

			for (final OrderableNode<TYPE> current : after)
			{
				if (current.contains(dependency))
				{
					return " should succeed " + current.getId() + ", which" + current.describe(dependency);
				}
			}

			return "";
		}

		/**
		 * Returns true if all after constraints of the node are resolved
		 * 
		 * @return true if all after constraints of the node are resolved
		 */
		public boolean isResolved()
		{
			return after.isEmpty();
		}

		/**
		 * Marks the node as resolved, thus removing it from all nodes it should be placed before
		 */
		public void resolved()
		{
			for (final OrderableNode<TYPE> current : before)
			{
				current.after.remove(this);
			}
		}

		/**
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString()
		{
			final StringBuilder builder = new StringBuilder(getId());

			if (!after.isEmpty())
			{
				builder.append(" after");

				for (final OrderableNode<TYPE> current : after)
				{
					builder.append(" ").append(current.getId());
				}
			}

			if (!before.isEmpty())
			{
				builder.append(" before");

				for (final OrderableNode<TYPE> current : before)
				{
					builder.append(" ").append(current.getId());
				}
			}

			return builder.toString();
		}
	}

	/**
	 * Hides the constructor
	 */
	private Orderer()
	{
		super();
	}

	/**
	 * Creates a new ordered list
	 * 
	 * @param <TYPE> the type to be ordered, must extend {@link Orderable}
	 * @param collection the collection to be ordered
	 * @return the ordered list
	 */
	public static <TYPE extends Orderable> List<TYPE> build(final Collection<TYPE> collection)
	{
		final List<TYPE> list = new ArrayList<TYPE>(collection);

		order(list);

		return list;
	}

	/**
	 * Reorders the list. Tries to preserve the original ordering as much as possible. Considers explicitly named
	 * constraints more important than those using wildcards or regular expressions.
	 * 
	 * @param <TYPE> the type to be ordered, must extend {@link Orderable}
	 * @param list the list
	 */
	public static <TYPE extends Orderable> void order(final List<TYPE> list)
	{
		final Map<String, OrderableNode<TYPE>> map = buildMap(list);

		for (int i = 0; i < list.size(); i += 1)
		{
			OrderableNode<TYPE> node = map.get(list.get(i).getOrderableId());

			if (!node.isResolved())
			{
				for (int j = i + 1; j < list.size(); j += 1)
				{
					node = map.get(list.get(j).getOrderableId());

					if (node.isResolved())
					{
						list.add(i, list.remove(j)); // implemented this way, it preserves the original ordering as much as possible
						break;
					}
				}
			}

			node.resolved();
		}
	}

	/**
	 * Describes the constraints in the list
	 * 
	 * @param <TYPE> the type to be ordered, must extend {@link Orderable}
	 * @param list the list
	 * @return the description
	 */
	public static <TYPE extends Orderable> String describe(final List<TYPE> list)
	{
		final Map<String, OrderableNode<TYPE>> map = buildMap(list);

		final StringBuilder builder = new StringBuilder();
		final Iterator<TYPE> it = list.iterator();

		while (it.hasNext())
		{
			final TYPE entry = it.next();

			builder.append(map.get(entry.getOrderableId()));

			if (it.hasNext())
			{
				builder.append("\n");
			}
		}

		return builder.toString();
	}

	/**
	 * Builds the map of {@link OrderableNode}s.
	 * 
	 * @param <TYPE> the type of the entries
	 * @param list the list containing the entries
	 * @return the map
	 * @throws IllegalArgumentException if multiple entries share the same key
	 */
	private static <TYPE extends Orderable> Map<String, OrderableNode<TYPE>> buildMap(final List<TYPE> list)
		throws IllegalArgumentException
	{
		final Map<String, OrderableNode<TYPE>> map = new LinkedHashMap<String, OrderableNode<TYPE>>(list.size());

		for (final TYPE entry : list)
		{
			final String id = entry.getOrderableId();

			if (map.containsKey(id))
			{
				throw new IllegalArgumentException("Multiple entries share the same constraint id: " + id);
			}

			map.put(id, new OrderableNode<TYPE>(entry));
		}

		buildConstraints(map, list, false);
		buildConstraints(map, list, true);

		return map;
	}

	/**
	 * Builds the constraints in the {@link OrderableNode}s.
	 * 
	 * @param <TYPE> the type of the entries
	 * @param map the map
	 * @param list the list containing the entries
	 * @param useWildcards true to use wildcards and regular expression with constraints
	 * @throws IllegalArgumentException if a constraint uses an invalid formatting
	 */
	private static <TYPE extends Orderable> void buildConstraints(final Map<String, OrderableNode<TYPE>> map,
		final List<TYPE> list, final boolean useWildcards) throws IllegalArgumentException
	{
		for (final TYPE orderable : list)
		{
			final OrderableNode<TYPE> node = map.get(orderable.getOrderableId());
			final String[] constraints = orderable.getOrderableConstraints();

			if ((constraints != null) && (constraints.length > 0))
			{
				for (final String constraint : constraints)
				{
					buildConstraint(map, node, constraint, useWildcards);
				}
			}
		}
	}

	/**
	 * Builds one constraint in the {@link OrderableNode}s.
	 * 
	 * @param <TYPE> the type of the entries
	 * @param map the map
	 * @param node the node to be built
	 * @param constraint the current constraint
	 * @param useWildcards true to use wildcards and regular expression with constraints
	 * @throws IllegalArgumentException if a constraint uses an invalid formatting
	 */
	private static <TYPE extends Orderable> void buildConstraint(final Map<String, OrderableNode<TYPE>> map,
		final OrderableNode<TYPE> node, final String constraint, final boolean useWildcards)
		throws IllegalArgumentException
	{
		final String[] references = extractReferences(constraint);
		String type = null;

		for (final String reference : references)
		{
			String current;
			final int delimiterIndex = reference.indexOf(':');

			if (delimiterIndex > 0)
			{
				type = reference.substring(0, delimiterIndex).trim();
				current = reference.substring(delimiterIndex + 1).trim();
			}
			else
			{
				current = reference.trim();
			}

			if (type == null)
			{
				throw new IllegalArgumentException("Missing constraint type: " + constraint);
			}
			else if (type.equalsIgnoreCase(BEFORE_TYPE))
			{
				if (!useWildcards)
				{
					buildConstraintsByEquality(node, map, true, current);
				}
				else
				{
					buildConstraintsByMatch(node, map, true, current);
				}
			}
			else if (type.equalsIgnoreCase(AFTER_TYPE))
			{
				if (!useWildcards)
				{
					buildConstraintsByEquality(node, map, false, current);
				}
				else
				{
					buildConstraintsByMatch(node, map, false, current);
				}
			}
			else
			{
				throw new IllegalArgumentException("Invalid constraint type: " + constraint);
			}
		}
	}

	/**
	 * Extracts all references of a constraint. The references may be split by ','. The results will get trimmed.
	 * 
	 * @param constraint the constraint
	 * @return the references
	 */
	private static String[] extractReferences(final String constraint)
	{
		return CONSTRAINT_DELIMITER_PATTERN.split(constraint);
	}

	/**
	 * Builds all constraints by equality.
	 * 
	 * @param <TYPE> the type of the entries
	 * @param node the node containing the references that should be matched
	 * @param map the map containing all nodes
	 * @param before true if before, false if after
	 * @param references all references
	 */
	private static <TYPE extends Orderable> void buildConstraintsByEquality(final OrderableNode<TYPE> node,
		final Map<String, OrderableNode<TYPE>> map, final boolean before, final String reference)
	{
		for (final OrderableNode<TYPE> current : map.values())
		{
			if (current != node)
			{
				if (before)
				{
					buildConstraintsByEquality(node, current, node.getId(), current.getId(), reference);
				}
				else
				{
					buildConstraintsByEquality(current, node, node.getId(), current.getId(), reference);
				}
			}
		}
	}

	/**
	 * Builds the constraints by checking the reference for equality
	 * 
	 * @param <TYPE> the type of the entries
	 * @param ancestor the node which should come before the descendant
	 * @param descendant the node which should follow the ancestor
	 * @param declarator the id of the node which declares the reference
	 * @param id the id of the node which may be referenced
	 * @param reference the reference
	 */
	private static <TYPE extends Orderable> void buildConstraintsByEquality(final OrderableNode<TYPE> ancestor,
		final OrderableNode<TYPE> descendant, final String declarator, final String id, final String reference)
	{
		if (reference.equals(id))
		{
			if (!ancestor.contains(descendant))
			{
				descendant.after(ancestor);
			}
			else
			{
				throw new IllegalArgumentException("Circular constraints: " + ancestor.getId()
					+ ancestor.describe(descendant) + ", which should succeed " + ancestor.getId());
			}
		}
	}

	/**
	 * Builds all constraints if the reference is a wildcard
	 * 
	 * @param <TYPE> the type of the entries
	 * @param node the node containing the references that should be matched
	 * @param map the map containing all nodes
	 * @param before true if before, false if after
	 * @param reference the reference
	 */
	private static <TYPE extends Orderable> void buildConstraintsByMatch(final OrderableNode<TYPE> node,
		final Map<String, OrderableNode<TYPE>> map, final boolean before, final String reference)
	{
		final Pattern pattern = extractPattern(node.getId(), reference);

		if (pattern != null)
		{
			for (final OrderableNode<TYPE> current : map.values())
			{
				if (current != node)
				{
					if (before)
					{
						buildConstraintsByMatch(node, current, node.getId(), current.getId(), pattern);
					}
					else
					{
						buildConstraintsByMatch(current, node, node.getId(), current.getId(), pattern);
					}
				}
			}
		}
	}

	/**
	 * Builds the constraints by using the specified pattern
	 * 
	 * @param <TYPE> the type of the entries
	 * @param ancestor the node which should come before the descendant
	 * @param descendant the node which should follow the ancestor
	 * @param declarator the id of the node which declares the reference
	 * @param id the id of the node which may be referenced
	 * @param reference the pattern for the reference
	 */
	private static <TYPE extends Orderable> void buildConstraintsByMatch(final OrderableNode<TYPE> ancestor,
		final OrderableNode<TYPE> descendant, final String declarator, final String id, final Pattern reference)
	{
		if ((reference.matcher(id).matches()) && (!ancestor.contains(descendant)))
		{
			descendant.after(ancestor);
		}
	}

	/**
	 * Extracts a pattern, if there is one specified
	 * 
	 * @param declarator the id of the node which declarates the reference
	 * @param reference the reference
	 * @return the pattern or null if the reference is no pattern
	 * @throws IllegalArgumentException if the pattern is invalid
	 */
	private static Pattern extractPattern(final String declarator, final String reference)
		throws IllegalArgumentException
	{
		// optimization for the most common pattern
		if (JOKER.equals(reference))
		{
			return JOKER_PATTERN;
		}

		if (isRegularExpression(reference))
		{
			try
			{
				return Pattern.compile(reference.substring(1, reference.length() - 1));
			}
			catch (final PatternSyntaxException e)
			{
				throw new IllegalArgumentException("Invalid regular expression reference " + reference
					+ " in constraints of orderable " + declarator, e);
			}
		}

		if (containsWildcards(reference))
		{
			return Pattern.compile(WILDCARDS_TO_REGULAR_EXPRESSION_PATTERN.matcher(reference)
				.replaceAll(WILDCARDS_TO_REGULAR_EXPRESSION_REPLACE).replace(JOKER, JOKER_REPLACEMENT)
				.replace(ANY, ANY_REPLACEMENT));
		}

		return null;
	}

	/**
	 * Returns true if the reference is a regular expression
	 * 
	 * @param reference the reference
	 * @return true if the reference is a regular expression
	 */
	private static boolean isRegularExpression(final String reference)
	{
		return (reference.startsWith(REGULAR_EXPRESSION_DELIMITER))
			&& (reference.endsWith(REGULAR_EXPRESSION_DELIMITER));
	}

	/**
	 * Returns true if the reference contains wildcards
	 * 
	 * @param reference the reference
	 * @return true if the reference contains wildcards
	 */
	private static boolean containsWildcards(final String reference)
	{
		return (reference.indexOf(JOKER_CHARACTER) >= 0) || (reference.indexOf(ANY_CHARACTER) >= 0);
	}

}