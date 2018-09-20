package com.victor.wang.bigCrab.exception;

import com.victor.wang.bigCrab.exception.base.NotFoundException;

public class DeliverNotFoundException
	extends NotFoundException
{
	public static final int NUMERIC_ERROR_CODE = 404;
	public static final String ERROR_CODE = "errors.com.victor.wang.bigCrab.deliver_not_found";

	public DeliverNotFoundException(String id)
	{
		super(NUMERIC_ERROR_CODE, ERROR_CODE, "The deliver [{}] can not be found.", id);
	}
}
