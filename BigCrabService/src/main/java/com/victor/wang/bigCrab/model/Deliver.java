package com.victor.wang.bigCrab.model;
import com.victor.wang.bigCrab.model.base.AuditedMysqlEntity;
import java.util.Date;

public class Deliver
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
	private String orderId;

	/**
	 * 
	 */
	private String mailno;

	/**
	 * 
	 */
	private String dCompany;

	/**
	 * 
	 */
	private String dContact;

	/**
	 * 
	 */
	private String dTel;

	/**
	 * 
	 */
	private String dProvince;

	/**
	 * 
	 */
	private String dCity;

	/**
	 * 
	 */
	private String dCounty;

	/**
	 * 
	 */
	private String dAddress;

	/**
	 * 
	 */
	private String status;

	/**
	 * 
	 */
	private Date deliverAt;

	/**
	 * 
	 */
	private Date sendstarttime;

	/**
	 * 
	 */
	private Date realDeliverAt;

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