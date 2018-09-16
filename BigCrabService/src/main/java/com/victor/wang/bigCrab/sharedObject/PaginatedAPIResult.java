/*
 * Copyright (C) 2015 Epic Games, Inc. All Rights Reserved.
 */

package com.victor.wang.bigCrab.sharedObject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.victor.wang.bigCrab.model.base.BaseEntity;

import java.util.List;

/**
 * <p>
 * A paginated API result that contains a list of results, and pagingContext. Contains the results
 * for an API call that returns a page of results.
 * </p>
 * The JSON generated for this object is:
 * <pre>
 * { "elements":[&lt;list of result items&gt;],
 * "paging":{ "start": &lt;start index of returned page&gt;,
 *            "count":&lt;page size&gt;,
 *            "total": &lt;the total number of items available (not set by all APIs)&gt;}
 * }
 * </pre>
 */
@JsonIgnoreProperties("toStringExcludeFieldNames")
public class PaginatedAPIResult<T>
		extends BaseEntity
{
	private List<T> elements;
	private PaginationContext paging = new PaginationContext();

	private PaginatedAPIResult()
	{

	}

	public PaginatedAPIResult(List<T> pageElements, int start, int count)
	{
		setElements(pageElements);
		setStart(start);
		setCount(count);
	}

	public PaginatedAPIResult(List<T> pageElements, int start, int count, int total)
	{
		setElements(pageElements);
		setStart(start);
		setCount(count);
		setTotal(total);
	}

	/**
	 * The list of results for the requested page
	 */
	public List<T> getElements()
	{
		return elements;
	}

	/**
	 * The list of results for the requested page
	 */
	public void setElements(List<T> pageElements)
	{
		this.elements = pageElements;
	}

	/**
	 * The pagination context, contains the page size, count...etc.
	 */
	@JsonProperty(value = "paging")
	public PaginationContext getPaging()
	{
		return paging;
	}

	/**
	 * The page size.
	 */
	@JsonIgnore
	public void setCount(int count)
	{
		this.paging.setCount(count);
	}

	/**
	 * The start index of the result page. 0 based
	 */
	@JsonIgnore
	public void setStart(int start)
	{
		this.paging.setStart(start);
	}


	/**
	 * The total number of available records available from the API from which this page was obtained.
	 * May not be set by all APIs
	 */
	@JsonIgnore
	public void setTotal(Integer total)
	{
		this.paging.setTotal(total);
	}

}
