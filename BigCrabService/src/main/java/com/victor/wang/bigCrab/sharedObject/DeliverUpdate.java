package com.victor.wang.bigCrab.sharedObject;

import com.victor.wang.bigCrab.model.base.BaseEntity;
import org.joda.time.DateTime;

import java.util.Date;

public class DeliverUpdate
		extends BaseEntity
{

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
	 * the deliverAt
	 */
	private Date deliverAt;

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

	public Date getDeliverAt()
	{
		return deliverAt;
	}

	public void setDeliverAt(Date deliverAt)
	{
		this.deliverAt = deliverAt;
	}
}
