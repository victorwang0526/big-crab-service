package com.victor.wang.bigCrab.model;
import com.victor.wang.bigCrab.model.base.AuditedMysqlEntity;
import com.victor.wang.bigCrab.sharedObject.lov.CardStatus;

import java.util.Date;

public class Card
	extends AuditedMysqlEntity
{

	/**
	 * 
	 */
	private String id;

	/**
	 * 购买人
	 */
	private String buyer;

	/**
	 * 购买日期
	 */
	private String boughtDate;

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
	private String cardType;

	/**
	 * 
	 */
	private CardStatus status;

	/**
	 * 
	 */
	private String description;

	/**
	 * 备注
	 */
	private String remark;

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

	public String getCardType()
	{
		return cardType;
	}

	public void setCardType(String cardType)
	{
		this.cardType = cardType;
	}

	public CardStatus getStatus()
	{
		return status;
	}

	public void setStatus(CardStatus status)
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

	public String getBuyer()
	{
		return buyer;
	}

	public void setBuyer(String buyer)
	{
		this.buyer = buyer;
	}

	public String getBoughtDate()
	{
		return boughtDate;
	}

	public void setBoughtDate(String boughtDate)
	{
		this.boughtDate = boughtDate;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
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