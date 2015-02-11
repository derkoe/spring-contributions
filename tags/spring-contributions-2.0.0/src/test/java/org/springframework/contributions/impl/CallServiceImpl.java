package org.springframework.contributions.impl;

import java.util.List;

import org.springframework.contributions.Callable;
import org.springframework.contributions.CallService;


public class CallServiceImpl implements CallService
{
	private List<Callable> callables;

	public CallServiceImpl(List<Callable> callables)
	{
		this.callables = callables;
	}

	public void callAll()
	{
		for(Callable callable : callables)
		{
			callable.call();
		}
	}

}
