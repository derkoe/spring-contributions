package org.springframework.contributions.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.springframework.contributions.Strategy;

public class StringStrategy implements Strategy<String>
{
	public String call(String object)
	{
		assertThat(object, is(String.class));

		return object;
	}
}
