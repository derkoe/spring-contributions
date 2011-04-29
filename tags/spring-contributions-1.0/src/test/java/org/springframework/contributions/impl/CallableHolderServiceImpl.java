package org.springframework.contributions.impl;

import java.util.List;

import org.springframework.contributions.CallableHolderService;
import org.springframework.contributions.Callable;

public class CallableHolderServiceImpl implements CallableHolderService
{
	private List<Callable> callables;

	public CallableHolderServiceImpl(List<Callable> callables)
	{
		this.callables = callables;
	}

	public List<Callable> getCallables()
	{
		return callables;
	}
}
