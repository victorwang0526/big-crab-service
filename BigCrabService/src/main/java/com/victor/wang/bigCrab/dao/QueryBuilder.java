/*
 * Copyright (C) 2015 Epic Games, Inc. All Rights Reserved.
 */
package com.victor.wang.bigCrab.dao;


import java.util.HashMap;
import java.util.Map;

public abstract class QueryBuilder
{
	private Map<String, Object> parameters = new HashMap<String, Object>();

	public Map<String, Object> toParameter()
	{
		return parameters;
	}

	@SuppressWarnings("unchecked")
	protected <T extends QueryBuilder> T add(String key, Object value)
	{
		this.parameters.put(key, value);
		return (T) this;
	}

	/**
	 * add a order by. the order by should be whole order by statements, for example: created_at DESC, account_name ASC
	 *
	 * @param orderBy the order by should be whole order by statements, for example: created_at DESC, account_name ASC
	 * @param <T>     the query build
	 * @return the query build
	 */
	public <T extends QueryBuilder> T orderBy(String orderBy)
	{
		return add("orderBy", orderBy);
	}

	public <T extends QueryBuilder> T pagination(Integer limit, Integer offset)
	{
		return add("limit", limit).add("offset", offset);
	}
}
