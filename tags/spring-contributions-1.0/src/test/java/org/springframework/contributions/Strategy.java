package org.springframework.contributions;

public interface Strategy<T>
{
	public String call(T object);
}
