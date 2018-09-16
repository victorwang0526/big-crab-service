package com.victor.wang.bigCrab.sharedObject;

import com.victor.wang.bigCrab.model.base.BaseEntity;
import net.sf.oval.constraint.NotNull;

public class CarrotCreate
		extends BaseEntity
{
	/**
	 * the carrot price
	 */
	@NotNull
	private Integer price;

	/**
	 * the carrot weight
	 */
	@NotNull
	private Integer weight;

	public Integer getPrice()
	{
		return price;
	}

	public void setPrice(Integer price)
	{
		this.price = price;
	}

	public Integer getWeight()
	{
		return weight;
	}

	public void setWeight(Integer weight)
	{
		this.weight = weight;
	}
}
