package org.springframework.contributions.util;

//CHECKSTYLE:OFF
public class TestEntry implements Orderable
{

	private final String id;
	private final String[] dependencies;

	public TestEntry(final String id, final String... dependencies)
	{
		super();

		this.id = id;
		this.dependencies = dependencies;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getOrderableId()
	{
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getOrderableConstraints()
	{
		return dependencies;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return id;
	}
}
//CHECKSTYLE:ON
