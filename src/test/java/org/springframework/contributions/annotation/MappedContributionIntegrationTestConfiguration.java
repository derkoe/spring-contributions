package org.springframework.contributions.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.contributions.Strategy;
import org.springframework.contributions.impl.EmptyStrategyCallerImpl;
import org.springframework.contributions.impl.IntegerStrategy;
import org.springframework.contributions.impl.StrategyCallerImpl;
import org.springframework.contributions.impl.StringStrategy;

/**
 * This is the spring annotation configuration class for the unit test
 * {@link AnnotationMappedContributionIntegrationTest}
 * 
 * @author Ortwin Probst
 */
@Configuration
@EnableContributions
@EnableAspectJAutoProxy
public abstract class MappedContributionIntegrationTestConfiguration
{

	@Bean(name = "strategyCaller")
	public StrategyCallerImpl strategyCaller(MappedContributionResolver<Class, Strategy> strategies)
	{
		return new StrategyCallerImpl(strategies.resolve("strategies"));
	}

	@Bean(name = "emptyStrategyCaller")
	public EmptyStrategyCallerImpl emptyStrategyCaller(MappedContributionResolver<Class, Strategy> strategies)
	{
		return new EmptyStrategyCallerImpl(strategies.resolve("emptyStrategies"));
	}

	@ContributionMapped(to = "strategies", keyClass = java.lang.String.class)
	@Bean(name = "stringStrategy")
	public StringStrategy stringStrategy()
	{
		return new StringStrategy();
	}

	@ContributionMapped(name = "integerStrategy", to = "strategies", keyClass = java.lang.Integer.class)
	@Bean(name = "integerStrategy")
	public IntegerStrategy integerStrategy()
	{
		return new IntegerStrategy();
	}

	@ContributionMapped(to = "mixedKeyMap", key = "stringKey")
	@Bean(name = "stringKeyObject")
	public Object stringKeyObject()
	{
		return new Object();
	}

	@ContributionMapped(to = "mixedKeyMap", keyEnumClass = KeyEnum.class, keyEnumValue = "keyOne")
	@Bean(name = "enumOneKeyObject")
	public Object enumOneKeyObject()
	{
		return new Object();
	}

	@ContributionMapped(to = "mixedKeyMap", keyClass = KeyEnum.class)
	@Bean(name = "classKeyObject")
	public Object classKeyObject()
	{
		return new Object();
	}

}
