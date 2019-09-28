package com.victor.wang.bigCrab.sharedObject;

import com.victor.wang.bigCrab.model.base.BaseEntity;

import java.util.Date;

public class CardInfo
		extends BaseEntity
{
	/**
	 * the id
	 */
	private String id;

	/**
	 * the cardNumber
	 */
	private String cardNumber;

	/**
	 * 购买人
	 */
	private String buyer;

	/**
	 * 购买日期
	 */
	private String boughtDate;

	/**
	 * card type
	 */
	private String cardType;

	/**
	 * the status
	 */
	private String status;

	/**
	 * the description
	 */
	private String description;

	private String remark;

	/**
	 * the lastErrorAt
	 */
	private Date lastErrorAt;

	/**
	 * the errorTimes
	 */
	private Integer errorTimes;

	/**
	 * the redeemAt
	 */
	private Date redeemAt;

	/**
	 * the deliverAt
	 */
	private Date deliverAt;

	/**
	 * the createdAt
	 */
	private Date createdAt;

	/**
	 * the lastModifiedAt
	 */
	private Date lastModifiedAt;

	private DeliverInfo deliverInfo;

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

	public String getStatus()
	{
		return status;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
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

	public Integer getErrorTimes()
	{
		return errorTimes;
	}

	public void setErrorTimes(Integer errorTimes)
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

	public DeliverInfo getDeliverInfo()
	{
		return deliverInfo;
	}

	public void setDeliverInfo(DeliverInfo deliverInfo)
	{
		this.deliverInfo = deliverInfo;
	}

	public String getCardType()
	{
		return cardType;
	}

	public void setCardType(String cardType)
	{
		this.cardType = cardType;
	}
}
