package com.victor.wang.bigCrab.sharedObject;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import java.util.Date;

/**
 * Created by victor.wang on 2018/9/18.
 */
public class CardRedeemRequest
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

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}
}
