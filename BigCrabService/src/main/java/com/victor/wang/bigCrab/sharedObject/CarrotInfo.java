package com.victor.wang.bigCrab.sharedObject;

import com.victor.wang.bigCrab.model.base.BaseEntity;
import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by victor.wang on 2018/3/22.
 */
public class CarrotInfo
	extends BaseEntity
{
	/**
	 * the carrot id
	 */
	private String id;

	/**
	 * the carrot name
	 */
	private String name;

	/**
	 * the carrot price
	 */
	private int price;

	/**
	 * the carrot weight
	 */
	private int weight;

	/**
	 * created time
	 */
	private Date createdAt;

	/**
	 * last modified time
	 */
	private Date lastModifiedAt;

	/**
	 * current rvn
	 */
	private int rvn;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
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

	public Date getCreatedAt()
	{
		return createdAt;
	}

	public void setCreatedAt(Date createdAt)
	{
		this.createdAt = createdAt;
	}

	public Date getLastModifiedAt()
	{
		return lastModifiedAt;
	}

	public void setLastModifiedAt(Date lastModifiedAt)
	{
		this.lastModifiedAt = lastModifiedAt;
	}

	public int getRvn()
	{
		return rvn;
	}

	public void setRvn(int rvn)
	{
		this.rvn = rvn;
	}
}
