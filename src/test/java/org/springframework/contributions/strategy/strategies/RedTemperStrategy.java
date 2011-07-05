package org.springframework.contributions.strategy.strategies;

import org.springframework.contributions.strategy.TemperStrategyService;
import org.springframework.contributions.strategy.entities.Red;

public class RedTemperStrategy implements TemperStrategyService<Red>
{

	public String getColor(Red color)
	{
		return color.COLOR;
	}

}
