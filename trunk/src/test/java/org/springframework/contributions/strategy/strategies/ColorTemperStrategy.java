package org.springframework.contributions.strategy.strategies;

import org.springframework.contributions.strategy.Color;
import org.springframework.contributions.strategy.TemperStrategyService;

public class ColorTemperStrategy implements TemperStrategyService<Color>
{
	public String getColor(Color color)
	{
		return color.getColor();
	}
}
