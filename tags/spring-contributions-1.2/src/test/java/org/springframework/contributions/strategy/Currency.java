package org.springframework.contributions.strategy;

import java.math.BigDecimal;

public interface Currency
{
	public BigDecimal getValue();

	public void setValue(BigDecimal value);

}
