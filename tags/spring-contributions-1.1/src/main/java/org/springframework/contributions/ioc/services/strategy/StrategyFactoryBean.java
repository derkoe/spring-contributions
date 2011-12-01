package org.springframework.contributions.ioc.services.strategy;

import java.lang.reflect.Method;
import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.contributions.ioc.exceptions.MissingArgumentException;
import org.springframework.contributions.ioc.util.StrategyRegistry;

/**
 * This FactoryBean is inspired by the {@link org.apache.tapestry5.ioc.services.StrategyBuilder} Service from Tapestry in version 5.2.5
 * 
 * The implementation operates around a {@link StrategyRegistry}, implementing a version of the Gang
 * of Four Strategy pattern.
 * <p/>
 * The constructed service is configured with a number of adapters (that implement the same service interface). Method
 * invocations on the service are routed to one of the adapters.
 * <p/>
 * The first parameter of each method is used to select the appropriate adapter.
 * <p/>
 * 
 * @author Ortwin Probst
 *
 * @param <T>
 */
public class StrategyFactoryBean<T> extends AbstractFactoryBean<T>
{
	private final StrategyRegistry<T> registry;
	private final Class<T> adapterType;
	
	public StrategyFactoryBean(StrategyRegistry<T> registry)
	{
		this.adapterType = registry.getAdapterType();
		this.registry = registry;
	}
	
	public StrategyFactoryBean(Class<T> adapterType, Map<Class, T> registrations)
	{
		this.adapterType = adapterType;
		this.registry = StrategyRegistry.newInstance(adapterType, registrations);
	}

	@Override
	protected T createInstance() throws Exception
	{
		return build();
	}

	@Override
	public Class getObjectType()
	{
		return adapterType;
	}

	private T build()
    {
        // TODO: Could cache the implClass by interfaceClass ...
		
		// Create proxy
		ProxyFactory result = new ProxyFactory();
		result.setInterfaces(new Class[]{adapterType});
		result.addAdvice(new RegistryAdvise(registry));
		
		return (T) result.getProxy();
    }

	private class RegistryAdvise implements MethodInterceptor
	{
		private final StrategyRegistry<T> registry; 

		public RegistryAdvise(StrategyRegistry<T> registry)
		{
			this.registry = registry;
		}

		public Object invoke(MethodInvocation invocation) throws Throwable
		{
			String methodName = invocation.getMethod().getName();
			if (invocation.getArguments() != null && invocation.getArguments().length > 0)
			{
				Object object = invocation.getArguments()[0];
				T service = registry.get(object.getClass());
				Class type = registry.getTypeForStrategy(service);
				Method method = service.getClass().getMethod(methodName, type);
				return method.invoke(service, object);
			}

			throw new MissingArgumentException("Missing first parameter for method: " + methodName + " No type information was provided to select a strategy"); 
		}
		
	}
	
}
