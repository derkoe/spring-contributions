package org.springframework.contributions;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@ContextConfiguration("mapped/spring-contributions-mapped.xml")
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class MappedContributionIntegrationTest
{
	@Autowired
	@Qualifier("StrategyCaller")
	private StrategyCaller strategyCaller;
	
	@Autowired
	@Qualifier("EmptyStrategyCaller")
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
}
