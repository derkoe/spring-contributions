package org.springframework.contributions;

import java.util.List;

public class ValueHolder
{
	private final List<String> values;

	public ValueHolder(final List<String> values)
	{
		this.values = values;
	}

	public List<String> getValues()
	{
		return values;
	}
	
}
