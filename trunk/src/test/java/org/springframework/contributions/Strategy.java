package org.springframework.contributions;

public interface Strategy<T>
{
	String call(T object);
}
