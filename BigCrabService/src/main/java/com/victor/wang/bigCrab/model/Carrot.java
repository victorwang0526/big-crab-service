package com.victor.wang.bigCrab.model;

import com.victor.wang.bigCrab.model.base.AuditedMysqlEntity;

public class Carrot
	extends AuditedMysqlEntity
{
	private String id;

	private String name;

	private int price;

	private int weight;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public int getPrice()
	{
		return price;
	}

	public void setPrice(int price)
	{
		this.price = price;
	}

	public int getWeight()
	{
		return weight;
	}

	public void setWeight(int weight)
	{
		this.weight = weight;
	}
}
