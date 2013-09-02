package org.springframework.contributions.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.contributions.Strategy;
import org.springframework.contributions.ioc.services.strategy.StrategyFactoryBean;
import org.springframework.contributions.strategy.ConversionStrategy;
import org.springframework.contributions.strategy.StringFormatStrategy;
import org.springframework.contributions.strategy.entities.BlankSeparated;
import org.springframework.contributions.strategy.entities.GreekDrachma;
import org.springframework.contributions.strategy.entities.Reversed;
import org.springframework.contributions.strategy.entities.SwissFranc;
import org.springframework.contributions.strategy.entities.UpperCase;
import org.springframework.contributions.strategy.entities.UsDollar;
import org.springframework.contributions.strategy.strategies.BlankSeparatedStringFormatStrategy;
import org.springframework.contributions.strategy.strategies.GreekDrachma2EuroStrategy;
import org.springframework.contributions.strategy.strategies.ReversedStringFormatStrategy;
import org.springframework.contributions.strategy.strategies.SwissFranc2EuroStrategy;
import org.springframework.contributions.strategy.strategies.UpperCaseStringFormatStrategy;
import org.springframework.contributions.strategy.strategies.UsDollar2EuroStrategy;

/**
 * This is the spring annotation configuration class for the unit test
 * {@link AnnotationStrategyFactoryBeanIntegrationTest}
 * 
 * @author Ortwin Probst
 */
@Configuration
@EnableAspectJAutoProxy
public abstract class StrategyFactoryBeanIntegrationTestConfiguration
{

	// /////////////////////////////////////////////////////////////////////
	// String format strategy test
	// //////////////////////////////////////////////////////////////////////
	@Bean(name = "stringFormatStrategy")
	public StrategyFactoryBean strategyCaller(MappedContributionResolver<Class, Strategy> strategies)
	{
		return new StrategyFactoryBean(StringFormatStrategy.class, strategies.resolve("stringFormatStrategies"));
	}

	@ContributionMapped(to = "stringFormatStrategies", keyClass = UpperCase.class)
	@Bean(name = "upperCase")
	public StringFormatStrategy<UpperCase> upperCase()
	{
		return new UpperCaseStringFormatStrategy();
	}

	@ContributionMapped(to = "stringFormatStrategies", keyClass = BlankSeparated.class)
	@Bean(name = "blankSeparated")
	public StringFormatStrategy<BlankSeparated> blankSeparated()
	{
		return new BlankSeparatedStringFormatStrategy();
	}

	@ContributionMapped(to = "stringFormatStrategies", keyClass = Reversed.class, name = "reversed")
	@Bean(name = "reversed")
	public StringFormatStrategy<Reversed> reversed()
	{
		return new ReversedStringFormatStrategy();
	}

	// //////////////////////////////////////////////////////////////////////
	// Currency conversion strategy test
	// //////////////////////////////////////////////////////////////////////

	@Bean(name = "currency2EureConversionStrategy")
	public StrategyFactoryBean emptyStrategyCaller(MappedContributionResolver<Class, Strategy> strategies)
	{
		return new StrategyFactoryBean(ConversionStrategy.class, strategies.resolve("conversionStrategies"));
	}

	@ContributionMapped(to = "conversionStrategies", keyClass = UsDollar.class)
	@Bean(name = "usDollar")
	public ConversionStrategy<UsDollar> usDollar()
	{
		return new UsDollar2EuroStrategy();
	}

	@ContributionMapped(to = "conversionStrategies", keyClass = SwissFranc.class)
	@Bean(name = "swissFranc")
	public ConversionStrategy<SwissFranc> swissFranc()
	{
		return new SwissFranc2EuroStrategy();
	}

	@ContributionMapped(to = "conversionStrategies", keyClass = GreekDrachma.class, name = "greekDrachma")
	@Bean(name = "greekDrachma")
	public ConversionStrategy<GreekDrachma> greekDrachma()
	{
		return new GreekDrachma2EuroStrategy();
	}
}
