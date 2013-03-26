package org.springframework.contributions.strategy.strategies;

import java.math.BigDecimal;

import org.springframework.contributions.strategy.ConversionStrategy;
import org.springframework.contributions.strategy.entities.Euro;
import org.springframework.contributions.strategy.entities.UsDollar;

public class UsDollar2EuroStrategy implements ConversionStrategy<UsDollar>
{

	public static final BigDecimal EXCHANGE_RATE = new BigDecimal(0.82264);

	public Euro convert(UsDollar currency)
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
