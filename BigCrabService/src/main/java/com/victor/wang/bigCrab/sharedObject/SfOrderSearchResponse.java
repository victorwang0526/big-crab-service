package com.victor.wang.bigCrab.sharedObject;

import com.victor.wang.bigCrab.model.base.BaseEntity;

import java.util.ArrayList;
import java.util.List;

public class SfOrderSearchResponse
		extends BaseEntity
{
	DeliverInfo deliverInfo;

	List<SfRoute> routes = new ArrayList<>();

	public DeliverInfo getDeliverInfo()
	{
		return deliverInfo;
	}

	public void setDeliverInfo(DeliverInfo deliverInfo)
	{
		this.deliverInfo = deliverInfo;
	}

	public List<SfRoute> getRoutes()
	{
		return routes;
	}

	public void setRoutes(List<SfRoute> routes)
	{
		this.routes = routes;
	}
}
