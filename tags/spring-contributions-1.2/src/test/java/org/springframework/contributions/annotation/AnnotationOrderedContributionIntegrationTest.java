package org.springframework.contributions.annotation;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.contributions.CallService;
import org.springframework.contributions.Callable;
import org.springframework.contributions.CallableHolderService;
import org.springframework.contributions.OrderedContributionIntegrationTest;
import org.springframework.contributions.ValueHolder;
import org.springframework.contributions.impl.Hen;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * This test is an equivalent of the {@link OrderedContributionIntegrationTest} testing the annotation contribution
 * mechanism.
 * 
 * @author Ortwin Probst
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AnnotationContributionConfig.class,
    OrderedContributionIntegrationTestConfiguration.class}, loader = AnnotationConfigContextLoader.class)
public class AnnotationOrderedContributionIntegrationTest
{
    @Inject
    private ApplicationContext ctx;

    @Inject
    @Named("testService")
    private CallService testService;

    @Inject
    @Named("callable")
    private CallableHolderService secondService;

    @Inject
    @Named("logBuffer")
    private StringBuilder logBuffer;

    @Inject
    @Named("valueHolder")
    private ValueHolder stringHolder;

    @Inject
    @Named("hen")
    private Hen hen;

    @Test
    public void testAppConfig()
    {
        Assert.assertNotNull("CallableThree Bean not found", ctx.getBean("callableThree"));
    }

    @Test
    public void testOrderedContribution()
    {
        testService.callAll();
        assertThat(logBuffer.toString(), is("OneTwoThree"));

        List<Callable> callables = secondService.getCallables();
        assertThat(callables.size(), is(3));
    }

    @Test
    public void testStringContribution()
    {
        assertThat(stringHolder.getValues(), is(Arrays.asList("String 1", "String 2", "String 3")));
    }

    @Test
    public void testHenAndEggs()
    {
        System.out.println(hen);
    }
}
