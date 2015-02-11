package org.springframework.contributions.strategy.strategies;

import java.math.BigDecimal;

import org.springframework.contributions.strategy.ConversionStrategy;
import org.springframework.contributions.strategy.entities.Euro;
import org.springframework.contributions.strategy.entities.GreekDrachma;

public class GreekDrachma2EuroStrategy implements ConversionStrategy<GreekDrachma>
{

	public static final BigDecimal EXCHANGE_RATE = new BigDecimal(0.002935);

	public Euro convert(GreekDrachma currency)
	{
		Euro result = new Euro();
		result.setValue(getExchangeRate().multiply(currency.getValue()));
		return result;
	}

	public BigDecimal getExchangeRate()
	{
		return EXCHANGE_RATE;
	}

}
