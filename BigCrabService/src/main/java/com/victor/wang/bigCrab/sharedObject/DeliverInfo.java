package com.victor.wang.bigCrab.sharedObject;

import com.victor.wang.bigCrab.model.base.BaseEntity;

import java.util.Date;

public class DeliverInfo
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
	 * the orderId
	 */
	private String orderId;

	/**
	 * the mailno
	 */
	private String mailno;

	/**
	 * the dCompany
	 */
	private String dCompany;

	/**
	 * the dContact
	 */
	private String dContact;

	/**
	 * the dTel
	 */
	private String dTel;

	/**
	 * the dProvince
	 */
	private String dProvince;

	/**
	 * the dCity
	 */
	private String dCity;

	/**
	 * the dCounty
	 */
	private String dCounty;

	/**
	 * the dAddress
	 */
	private String dAddress;

	/**
	 * the status
	 */
	private String status;

	/**
	 * the deliverAt
	 */
	private Date deliverAt;

	/**
	 * the sendstarttime
	 */
	private Date sendstarttime;

	/**
	 * the realDeliverAt
	 */
	private Date realDeliverAt;

	/**
	 * the receivedAt
	 */
	private Date receivedAt;

	/**
	 * the createdAt
	 */
	private Date createdAt;

	/**
	 * the lastModifiedAt
	 */
	private Date lastModifiedAt;

	/**
	 * the rvn
	 */
	private Integer rvn;

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

	public String getOrderId()
	{
		return orderId;
	}

	public void setOrderId(String orderId)
	{
		this.orderId = orderId;
	}

	public String getMailno()
	{
		return mailno;
	}

	public void setMailno(String mailno)
	{
		this.mailno = mailno;
	}

	public String getdCompany()
	{
		return dCompany;
	}

	public void setdCompany(String dCompany)
	{
		this.dCompany = dCompany;
	}

	public String getdContact()
	{
		return dContact;
	}

	public void setdContact(String dContact)
	{
		this.dContact = dContact;
	}

	public String getdTel()
	{
		return dTel;
	}

	public void setdTel(String dTel)
	{
		this.dTel = dTel;
	}

	public String getdProvince()
	{
		return dProvince;
	}

	public void setdProvince(String dProvince)
	{
		this.dProvince = dProvince;
	}

	public String getdCity()
	{
		return dCity;
	}

	public void setdCity(String dCity)
	{
		this.dCity = dCity;
	}

	public String getdCounty()
	{
		return dCounty;
	}

	public void setdCounty(String dCounty)
	{
		this.dCounty = dCounty;
	}

	public String getdAddress()
	{
		return dAddress;
	}

	public void setdAddress(String dAddress)
	{
		this.dAddress = dAddress;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public Date getDeliverAt()
	{
		return deliverAt;
	}

	public void setDeliverAt(Date deliverAt)
	{
		this.deliverAt = deliverAt;
	}

	public Date getSendstarttime()
	{
		return sendstarttime;
	}

	public void setSendstarttime(Date sendstarttime)
	{
		this.sendstarttime = sendstarttime;
	}

	public Date getRealDeliverAt()
	{
		return realDeliverAt;
	}

	public void setRealDeliverAt(Date realDeliverAt)
	{
		this.realDeliverAt = realDeliverAt;
	}

	public Date getReceivedAt()
	{
		return receivedAt;
	}

	public void setReceivedAt(Date receivedAt)
	{
		this.receivedAt = receivedAt;
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

	public Integer getRvn()
	{
		return rvn;
	}

	public void setRvn(Integer rvn)
	{
		this.rvn = rvn;
	}
}
