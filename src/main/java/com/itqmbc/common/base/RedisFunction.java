package com.itqmbc.common.base;

public interface RedisFunction<E,T> {
	public T callback(E e);
}
