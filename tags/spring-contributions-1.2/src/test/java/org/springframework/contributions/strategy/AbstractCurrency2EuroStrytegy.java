package org.springframework.contributions.strategy;

import java.math.BigDecimal;

import org.springframework.contributions.strategy.entities.Euro;

public abstract class AbstractCurrency2EuroStrytegy<T extends Currency> implements ConversionStrategy<T>
{

	public Euro convert(T currency)
	{
		Euro result = new Euro();
		result.setValue(getExchangeRate().multiply(currency.getValue()));
		return result;
	}

	public abstract BigDecimal getExchangeRate();

}
