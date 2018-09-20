package com.victor.wang.bigCrab.sharedObject;

import com.victor.wang.bigCrab.model.base.BaseEntity;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import java.util.Date;

/**
 * Created by victor.wang on 2018/9/18.
 */
public class CardRedeemRequest
	extends BaseEntity
{
	@NotNull
	@NotBlank
	private String password;

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
	@NotNull
	@NotBlank
	private String dContact;

	/**
	 * the dTel
	 */
	@NotNull
	@NotBlank
	private String dTel;

	/**
	 * the dProvince
	 */
	@NotNull
	@NotBlank
	private String dProvince;

	/**
	 * the dCity
	 */
	@NotNull
	@NotBlank
	private String dCity;

	/**
	 * the dCounty
	 */
	@NotNull
	@NotBlank
	private String dCounty;

	/**
	 * the dAddress
	 */
	@NotNull
	@NotBlank
	private String dAddress;

	/**
	 * the deliverAt
	 */
	@NotNull
	@NotBlank
	private Date deliverAt;

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
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

	public Date getDeliverAt()
	{
		return deliverAt;
	}

	public void setDeliverAt(Date deliverAt)
	{
		this.deliverAt = deliverAt;
	}
}
