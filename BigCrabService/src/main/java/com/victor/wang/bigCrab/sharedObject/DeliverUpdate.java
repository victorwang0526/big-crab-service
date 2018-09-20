package com.victor.wang.bigCrab.sharedObject;

import com.victor.wang.bigCrab.model.base.BaseEntity;
import org.joda.time.DateTime;

public class DeliverUpdate
		extends BaseEntity
{
	/**
	 * the cardNumber
	 */
	private String cardNumber = UNSET_STRING;

	/**
	 * the orderId
	 */
	private String orderId = UNSET_STRING;

	/**
	 * the mailno
	 */
	private String mailno = UNSET_STRING;

	/**
	 * the dCompany
	 */
	private String dCompany = UNSET_STRING;

	/**
	 * the dContact
	 */
	private String dContact = UNSET_STRING;

	/**
	 * the dTel
	 */
	private String dTel = UNSET_STRING;

	/**
	 * the dProvince
	 */
	private String dProvince = UNSET_STRING;

	/**
	 * the dCity
	 */
	private String dCity = UNSET_STRING;

	/**
	 * the dCounty
	 */
	private String dCounty = UNSET_STRING;

	/**
	 * the dAddress
	 */
	private String dAddress = UNSET_STRING;

	/**
	 * the status
	 */
	private String status = UNSET_STRING;

	/**
	 * the deliverAt
	 */
	private Date deliverAt = UNSET_STRING;

	/**
	 * the sendstarttime
	 */
	private Date sendstarttime = UNSET_STRING;

	/**
	 * the realDeliverAt
	 */
	private Date realDeliverAt = UNSET_STRING;

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

	public String getDCompany()
	{
		return dCompany;
	}

	public void setDCompany(String dCompany)
	{
		this.dCompany = dCompany;
	}

	public String getDContact()
	{
		return dContact;
	}

	public void setDContact(String dContact)
	{
		this.dContact = dContact;
	}

	public String getDTel()
	{
		return dTel;
	}

	public void setDTel(String dTel)
	{
		this.dTel = dTel;
	}

	public String getDProvince()
	{
		return dProvince;
	}

	public void setDProvince(String dProvince)
	{
		this.dProvince = dProvince;
	}

	public String getDCity()
	{
		return dCity;
	}

	public void setDCity(String dCity)
	{
		this.dCity = dCity;
	}

	public String getDCounty()
	{
		return dCounty;
	}

	public void setDCounty(String dCounty)
	{
		this.dCounty = dCounty;
	}

	public String getDAddress()
	{
		return dAddress;
	}

	public void setDAddress(String dAddress)
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

}
