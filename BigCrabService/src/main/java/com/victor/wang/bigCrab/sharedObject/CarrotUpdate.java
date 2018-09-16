package com.victor.wang.bigCrab.sharedObject;

import com.victor.wang.bigCrab.model.base.BaseEntity;
import net.sf.oval.constraint.NotNull;

public class CarrotUpdate
		extends BaseEntity
{
	/**
	 * the carrot price
	 */
	@NotNull
	private int price;

	/**
	 * the carrot weight
	 */
	@NotNull
	private int weight;

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
