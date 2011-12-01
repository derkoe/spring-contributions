package org.springframework.contributions.strategy.strategies;

import org.springframework.contributions.strategy.TemperStrategyService;
import org.springframework.contributions.strategy.entities.Blue;
import org.springframework.contributions.strategy.entities.Green;
import org.springframework.contributions.strategy.entities.Yellow;

public class GreenTemperStrategy implements TemperStrategyService<Green>
{
	public static String TEMPERED_COLOR = Blue.COLOR + " plus " + Yellow.COLOR;

	public String getColor(Green color)
	{
		return TEMPERED_COLOR;
	}

}
