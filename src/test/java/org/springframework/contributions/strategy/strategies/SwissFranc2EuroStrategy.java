package org.springframework.contributions.strategy.strategies;

import java.math.BigDecimal;

import org.springframework.contributions.strategy.ConversionStrategy;
import org.springframework.contributions.strategy.entities.Euro;
import org.springframework.contributions.strategy.entities.SwissFranc;

public class SwissFranc2EuroStrategy implements ConversionStrategy<SwissFranc>
{

	public static final BigDecimal EXCHANGE_RATE = new BigDecimal(0.83301);

	public Euro convert(SwissFranc currency)
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
