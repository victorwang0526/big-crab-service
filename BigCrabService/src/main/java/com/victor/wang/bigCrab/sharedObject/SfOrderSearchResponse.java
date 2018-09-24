package com.victor.wang.bigCrab.sharedObject;

import com.victor.wang.bigCrab.model.base.BaseEntity;

import java.util.ArrayList;
import java.util.List;

public class SfOrderSearchResponse
		extends BaseEntity
{
	List<SfRoute> routes = new ArrayList<>();

	public List<SfRoute> getRoutes()
	{
		return routes;
	}

	public void setRoutes(List<SfRoute> routes)
	{
		this.routes = routes;
	}
}
