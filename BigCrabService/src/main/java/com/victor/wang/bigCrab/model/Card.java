package com.victor.wang.bigCrab.model;
import com.victor.wang.bigCrab.model.base.AuditedMysqlEntity;
import java.util.Date;

public class Card
	extends AuditedMysqlEntity
{

	/**
	 * 
	 */
	private String id;

	/**
	 * 
	 */
	private String cardNumber;

	/**
	 * 
	 */
	private String password;

	/**
	 * 
	 */
	private String status;

	/**
	 * 
	 */
	private String description;

	/**
	 * 
	 */
	private Date lastErrorAt;

	/**
	 * 
	 */
	private int errorTimes;

	/**
	 * 
	 */
	private Date redeemAt;

	/**
	 * 
	 */
	private Date deliverAt;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getCardNumber()
	{
		return cardNumber;
	}

	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Date getLastErrorAt()
	{
		return lastErrorAt;
	}

	public void setLastErrorAt(Date lastErrorAt)
	{
		this.lastErrorAt = lastErrorAt;
	}

	public int getErrorTimes()
	{
		return errorTimes;
	}

	public void setErrorTimes(int errorTimes)
	{
		this.errorTimes = errorTimes;
	}

	public Date getRedeemAt()
	{
		return redeemAt;
	}

	public void setRedeemAt(Date redeemAt)
	{
		this.redeemAt = redeemAt;
	}

	public Date getDeliverAt()
	{
		return deliverAt;
	}

	public void setDeliverAt(Date deliverAt)
	{
		this.deliverAt = deliverAt;
	}

}