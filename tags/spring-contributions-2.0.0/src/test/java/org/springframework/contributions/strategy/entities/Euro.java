package org.springframework.contributions.strategy.entities;

import java.math.BigDecimal;

import org.springframework.contributions.strategy.AbstractCurrency;

public class Euro extends AbstractCurrency
{

	public Euro(){}

	public Euro(BigDecimal value)
	{
		super(value);
	}

	public Euro(double value)
	{
		super(value);
	}
	
}
