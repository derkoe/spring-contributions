package org.springframework.contributions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.springframework.contributions.ContributionsNamespaceHandler.MAPPED_CONTRIBUTION_PREFIX;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.contributions.annotation.KeyEnum;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@ContextConfiguration("mapped/spring-contributions-mapped.xml")
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class MappedContributionIntegrationTest
{
	@Inject
	private ApplicationContext ctx;

	@Inject
	@Named("StrategyCaller")
	private StrategyCaller strategyCaller;
	
	@Inject
	@Named("EmptyStrategyCaller")
	private StrategyCaller emptyCaller;

	@Test
	public void testPositives()
	{
		assertThat(strategyCaller.call(Integer.valueOf(5)), is("5"));
		assertThat(strategyCaller.call("Hello"), is("Hello"));
	}
	
	@Test(expected = NullPointerException.class)
	public void testEmpty()
	{
		assertNotNull(emptyCaller);
		
		emptyCaller.call("String");
	}

	@Test
	public void testStringKey()
	{
		Map<String, Object> map = (Map<String, Object>)ctx.getBean(MAPPED_CONTRIBUTION_PREFIX + "mixedKeyMap");

		assertThat(map.containsKey("stringKey"), is(Boolean.TRUE));
		assertThat(map.containsKey(KeyEnum.keyOne), is(Boolean.TRUE));
		assertThat(map.containsKey(KeyEnum.class), is(Boolean.TRUE));
	}
}
