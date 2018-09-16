package com.victor.wang.bigCrab.sharedObject;

import com.victor.wang.bigCrab.model.base.BaseEntity;
import java.util.Date;

public class CardCreate
		extends BaseEntity
{
	/**
	 * the cardNumber
	 */
	private String cardNumber;

	/**
	 * the description
	 */
	private String description;


	public String getCardNumber()
	{
		return cardNumber;
	}

	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

}
