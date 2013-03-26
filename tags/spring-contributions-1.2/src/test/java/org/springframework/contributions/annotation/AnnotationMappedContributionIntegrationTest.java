package org.springframework.contributions.annotation;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.contributions.MappedContributionIntegrationTest;
import org.springframework.contributions.StrategyCaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * This test is an equivalent of the {@link MappedContributionIntegrationTest}
 * testing the annotation contribution mechanism.
 * 
 * @author Ortwin Probst
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AnnotationContributionConfig.class, MappedContributionIntegrationTestConfiguration.class}, loader=AnnotationConfigContextLoader.class)
public class AnnotationMappedContributionIntegrationTest
{

	@Inject
	@Named("strategyCaller")
	private StrategyCaller strategyCaller;
	
	@Inject
	@Named("emptyStrategyCaller")
	private StrategyCaller emptyCaller;

	@Inject
	private MappedContributionResolver<Map<String, Object>> mixedKeyMapResolver;

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
		Map<String, Object> map = mixedKeyMapResolver.resolve("mixedKeyMap");

		assertThat(map.containsKey("stringKey"), is(Boolean.TRUE));
		assertThat(map.containsKey(KeyEnum.keyOne), is(Boolean.TRUE));
		assertThat(map.containsKey(KeyEnum.class), is(Boolean.TRUE));
	}

}
