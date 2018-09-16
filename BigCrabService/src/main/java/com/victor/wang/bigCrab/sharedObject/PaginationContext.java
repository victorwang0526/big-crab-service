/*
 * Copyright (C) 2015 Epic Games, Inc. All Rights Reserved.
 */
package com.victor.wang.bigCrab.sharedObject;


/**
 * Information about a page of data returned by an API endpoint
 */
public class PaginationContext
{
	private int count = 0;
	private int start = 0;
	private Integer total;

	public PaginationContext()
	{

	}

	/**
	 * The start index of the page. 0 based
	 */
	public int getStart()
	{
		return start;
	}

	/**
	 * The start index of the page. 0 based
	 */
	public void setStart(int pageStart)
	{
		this.start = pageStart;
	}

	/**
	 * The page size.
	 */
	public int getCount()
	{
		return count;
	}

	/**
	 * The page size.
	 */
	public void setCount(int numPerPage)
	{
		this.count = numPerPage;
	}

	/**
	 * The total number of available records available from the API from which this page was obtained.
	 * May not be set by all APIs
	 */
	public Integer getTotal()
	{
		return total;
	}

	/**
	 * The total number of available records available from the API from which this page was obtained.
	 * May not be set by all APIs
	 */
	public void setTotal(Integer totalResultCount)
	{
		this.total = totalResultCount;
	}
}
