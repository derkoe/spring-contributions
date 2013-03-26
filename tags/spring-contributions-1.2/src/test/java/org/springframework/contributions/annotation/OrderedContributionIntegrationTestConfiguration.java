package org.springframework.contributions.annotation;

import javax.inject.Named;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Lazy;
import org.springframework.contributions.CallService;
import org.springframework.contributions.Callable;
import org.springframework.contributions.impl.CallServiceImpl;
import org.springframework.contributions.impl.CallableHolderServiceImpl;
import org.springframework.contributions.impl.CallableOne;
import org.springframework.contributions.impl.CallableTwo;
import org.springframework.contributions.impl.Egg;
import org.springframework.contributions.impl.GenericCallable;
import org.springframework.contributions.impl.Hen;

/**
 * This is the spring annotation configuration class for the unit test {@link AnnotationOrderedContributionIntegrationTest}
 * 
 * @author Ortwin Probst
 *
 */
@Configuration
@EnableAspectJAutoProxy
@ImportResource("classpath:org/springframework/contributions/javaconfig/spring-java-contributions-ordered-services.xml")
public abstract class OrderedContributionIntegrationTestConfiguration
{

	////////////////////////////////////////////////////////////////////////
	//	Java config part of String contribution test setup.				  //
	//	The rest of the test config is defined in xml config			  //
	////////////////////////////////////////////////////////////////////////
	
	@Contribution(to="value-list", constraints="after:string1,before:string3")
	@Bean(name="string2")
	public String string2()
	{
		return "String 2";
	}

	////////////////////////////////////////////////////////////////////////
	//	Hen/egg test setup												  //
	//	A circular contribution to service dependency can only be 		  //
	//  achieved by using lazy initialization (@Lazy) on both, the	 	  //
	//	contributions and the service.									  //
	////////////////////////////////////////////////////////////////////////

	@Bean(name="hen") @Lazy
	public Hen hen(OrderedContributionResolver<Egg> eggs)
	{
		return new Hen(eggs.resolve("eggs"), "Foghorn Leghorn");
	}

	// NB! nameconvention: either the method name of the bean must match the bean name
	// or the name attribute has to be set correctly in the @Contribution annotation
	@Contribution(to="eggs", name="one")
	@Bean(name="one") @Lazy
	public Egg oneEgg(@Named("hen") Hen hen)
	{
		return new Egg(hen);
	}

	@Contribution(to="eggs")
	@Bean(name="two") @Lazy
	public Egg two(@Named("hen") Hen hen)
	{
		return new Egg(hen);
	}

	@Contribution(to="eggs")
	@Bean(name="three") @Lazy
	public Egg three(@Named("hen") Hen hen)
	{
		return new Egg(hen);
	}

	////////////////////////////////////////////////////////////////////////
	//	Callable test setup												  //
	////////////////////////////////////////////////////////////////////////

	@Bean(name="logBuffer")
	public StringBuilder logBuffer()
	{
		return new StringBuilder();
	}

	@Bean(name="testService")
	public CallService callService(OrderedContributionResolver<Callable> callables)
	{
		return new CallServiceImpl(callables.resolve("callables"));
	}

	@Bean(name="callable")
	public CallableHolderServiceImpl callableHolderService(OrderedContributionResolver<Callable> callables)
	{
		return new CallableHolderServiceImpl(callables.resolve("callables"));
	}

//	@Bean(name="callable") @Lazy
//	public CallableHolderServiceImpl callableHolderService(@Named("callables") List<Callable> callables)
//	{
//		return new CallableHolderServiceImpl(callables);
//	}

	@Contribution(to="callables")
	@Bean(name="callableOne")
	public GenericCallable callableOne(StringBuilder logBuffer)
	{
		return new CallableOne(logBuffer);
	}

	@Contribution(to="callables", constraints="after:callableOne")
	@Bean(name="callableTwo")
	public GenericCallable callableTwo(StringBuilder logBuffer)
	{
		return new CallableTwo(logBuffer, "Two");
	}

	@Contribution(to="callables", constraints="after:*")
	@Bean(name="callableThree")
	public GenericCallable callableThree(StringBuilder logBuffer)
	{
		return new CallableThree(logBuffer);
	}

	class CallableThree extends GenericCallable
	{
		CallableThree(StringBuilder logBuffer)
		{
			super(logBuffer, "Three");
		}
	}

}
