package com.victor.wang.bigCrab.model;
import com.victor.wang.bigCrab.model.base.AuditedMysqlEntity;
import com.victor.wang.bigCrab.sharedObject.lov.DeliverStatus;

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
	private DeliverStatus status;

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

	/**
	 * 
	 */
	private Date receivedAt;

	/**
	 * 二维码
	 */
	private String twoDimensionCode;

	/**
	 * 下单返回的response
	 */
	private String sfResponse;

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

	public String getSfResponse() {
		return sfResponse;
	}

	public void setSfResponse(String sfResponse) {
		this.sfResponse = sfResponse;
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

	public DeliverStatus getStatus()
	{
		return status;
	}

	public String getTwoDimensionCode() {
		return twoDimensionCode;
	}

	public void setTwoDimensionCode(String twoDimensionCode) {
		this.twoDimensionCode = twoDimensionCode;
	}

	public void setStatus(DeliverStatus status)
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
}