package com.victor.wang.bigCrab.sharedObject;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import java.util.List;

/**
 * Created by victor.wang on 2018/9/18.
 */
public class CardRequest
{
	@NotNull
	@NotBlank
	private List<String> cardNumbers;

	public List<String> getCardNumbers()
	{
		return cardNumbers;
	}

	public void setCardNumbers(List<String> cardNumbers)
	{
		this.cardNumbers = cardNumbers;
	}
}
