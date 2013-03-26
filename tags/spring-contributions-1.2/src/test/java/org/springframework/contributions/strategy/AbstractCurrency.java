package org.springframework.contributions.strategy;

import java.math.BigDecimal;

public abstract class AbstractCurrency implements Currency
{

	private BigDecimal value;

	public AbstractCurrency(){}

	public AbstractCurrency(double value)
	{
		this(new BigDecimal(value));
	}

	public AbstractCurrency(BigDecimal value)
	{
		this.value = value;
	}

	public BigDecimal getValue()
	{
		return value;
	}

	public void setValue(BigDecimal value)
	{
		this.value = value;
	}

}
