package org.springframework.contributions.strategy;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.contributions.strategy.entities.Green;
import org.springframework.contributions.strategy.entities.Red;
import org.springframework.contributions.strategy.entities.Yellow;
import org.springframework.contributions.strategy.strategies.GreenTemperStrategy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@ContextConfiguration("strategy-builder.xml")
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class StrategyFactoryBeanIntegrationTest
{
	@Autowired
	@Qualifier("TemperStrategy")
	private TemperStrategyService<Color> strategyService;
	
	@Test
	public void testTemperStrategy()
	{
		assertThat(strategyService.getColor(new Green()), is(GreenTemperStrategy.TEMPERED_COLOR));
		assertThat(strategyService.getColor(new Red()), is(Red.COLOR));
	}
	
	@Test
	public void testParentStrategy()
	{
		assertThat(strategyService.getColor(new Yellow()), is(Yellow.COLOR));
	}
}
