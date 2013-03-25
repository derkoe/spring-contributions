package org.springframework.contributions.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.springframework.contributions.Strategy;

public class IntegerStrategy implements Strategy<Integer>
{
    public String call(Integer object)
    {
        assertThat(object, is(Integer.class));

        return object.toString();
    }
}
