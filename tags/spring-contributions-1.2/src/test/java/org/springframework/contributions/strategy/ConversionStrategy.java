package org.springframework.contributions.strategy;

import org.springframework.contributions.strategy.entities.Euro;

public interface ConversionStrategy<T extends Currency>
{
	Euro convert(T currency);
}
