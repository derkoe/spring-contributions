package org.springframework.contributions.strategy;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.contributions.strategy.entities.BlankSeparated;
import org.springframework.contributions.strategy.entities.Euro;
import org.springframework.contributions.strategy.entities.GreekDrachma;
import org.springframework.contributions.strategy.entities.Reversed;
import org.springframework.contributions.strategy.entities.SwissFranc;
import org.springframework.contributions.strategy.entities.UpperCase;
import org.springframework.contributions.strategy.entities.UsDollar;
import org.springframework.contributions.strategy.strategies.GreekDrachma2EuroStrategy;
import org.springframework.contributions.strategy.strategies.SwissFranc2EuroStrategy;
import org.springframework.contributions.strategy.strategies.UsDollar2EuroStrategy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@ContextConfiguration("strategy-builder.xml")
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class StrategyFactoryBeanIntegrationTest
{

	@Inject
	@Named("currency2EureConversionStrategy")
	private ConversionStrategy<Currency> currency2EureConversionStrategy;

	@Inject
	@Named("stringFormatStrategy")
	private StringFormatStrategy<StringContainer> stringFormatStrategy;

	@Test
	public void testCurrency2EuroConversionStrategy()
	{
		Currency drachma = new GreekDrachma(10.0);
		BigDecimal assertionValue = GreekDrachma2EuroStrategy.EXCHANGE_RATE.multiply(drachma.getValue());
		assertThat(currency2EureConversionStrategy.convert(drachma).getValue(), is(new Euro(assertionValue).getValue()));

		Currency dollar = new UsDollar(10.0);
		assertionValue = UsDollar2EuroStrategy.EXCHANGE_RATE.multiply(dollar.getValue());
		assertThat(currency2EureConversionStrategy.convert(dollar).getValue(), is(new Euro(assertionValue).getValue()));

		Currency franc = new SwissFranc(10.0);
		assertionValue = SwissFranc2EuroStrategy.EXCHANGE_RATE.multiply(franc.getValue());
		assertThat(currency2EureConversionStrategy.convert(franc).getValue(), is(new Euro(assertionValue).getValue()));
	}

	@Test
	public void testStringFormatStrategy()
	{
		final String value = "foobar";

		StringContainer upperCase = new UpperCase(value);
		assertThat(stringFormatStrategy.format(upperCase), is("FOOBAR"));

		StringContainer reversed = new Reversed(value);
		assertThat(stringFormatStrategy.format(reversed), is("raboof"));

		StringContainer blankSeparated = new BlankSeparated(value);
		assertThat(stringFormatStrategy.format(blankSeparated), is("f o o b a r"));

		assertThat(stringFormatStrategy.format(new BlankSeparated(stringFormatStrategy
			.format(new Reversed(stringFormatStrategy.format(new UpperCase(value)))))), is("R A B O O F"));
	}

}
