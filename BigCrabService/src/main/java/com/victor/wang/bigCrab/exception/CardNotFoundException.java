package com.victor.wang.bigCrab.exception;

import com.victor.wang.bigCrab.exception.base.NotFoundException;

public class CardNotFoundException
	extends NotFoundException
{
	public static final int NUMERIC_ERROR_CODE = 404;
	public static final String ERROR_CODE = "errors.com.victor.wang.bigCrab.card_not_found";

	public CardNotFoundException(String id)
	{
		super(NUMERIC_ERROR_CODE, ERROR_CODE, "The card [{}] can not be found.", id);
	}
}
