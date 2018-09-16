package com.victor.wang.bigCrab.sharedObject;

import com.victor.wang.bigCrab.model.base.BaseEntity;
import org.joda.time.DateTime;

public class CardUpdate
		extends BaseEntity
{
	/**
	 * the cardNumber
	 */
	private String cardNumber = UNSET_STRING;

	/**
	 * the description
	 */
	private String description = UNSET_STRING;


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
