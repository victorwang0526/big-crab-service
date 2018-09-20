package com.victor.wang.bigCrab.sharedObject;

import com.victor.wang.bigCrab.model.base.BaseEntity;
import java.util.Date;

public class DeliverCreate
		extends BaseEntity
{

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

	public Date getDeliverAt()
	{
		return deliverAt;
	}

	public void setDeliverAt(Date deliverAt)
	{
		this.deliverAt = deliverAt;
	}

}
