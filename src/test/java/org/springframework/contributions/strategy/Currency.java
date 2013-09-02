package org.springframework.contributions.strategy;

import java.math.BigDecimal;

public interface Currency
{
	BigDecimal getValue();

	void setValue(BigDecimal value);

}
