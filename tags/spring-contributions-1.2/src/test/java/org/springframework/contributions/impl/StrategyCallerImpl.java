package org.springframework.contributions.impl;

import java.util.Map;

import org.springframework.contributions.Strategy;
import org.springframework.contributions.StrategyCaller;

public class StrategyCallerImpl implements StrategyCaller
{
	private final Map<Class, Strategy> strategies;

	public StrategyCallerImpl(Map<Class, Strategy> strategies)
	{
		this.strategies = strategies;
	}

	public String call(Object value)
	{
		Class valueClass = value.getClass();
		Strategy strategy = strategies.get(valueClass);
		if (strategy == null)
			throw new NullPointerException("No strategy for class: " + valueClass);
		return strategy.call(value);
	}
}
