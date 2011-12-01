package org.springframework.contributions.impl;

import java.util.Map;

import org.springframework.contributions.Strategy;
import org.springframework.contributions.StrategyCaller;

public class EmptyStrategyCallerImpl extends StrategyCallerImpl implements StrategyCaller
{

	public EmptyStrategyCallerImpl(Map<Class, Strategy> strategies)
	{
		super(strategies);
	}

}
