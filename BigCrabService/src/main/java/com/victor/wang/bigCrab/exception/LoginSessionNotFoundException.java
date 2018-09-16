package com.victor.wang.bigCrab.exception;

import com.victor.wang.bigCrab.exception.base.NotFoundException;

public class LoginSessionNotFoundException
	extends NotFoundException
{
	public static final int NUMERIC_ERROR_CODE = 404;
	public static final String ERROR_CODE = "errors.com.victor.wang.bigCrab.loginSession_not_found";

	public LoginSessionNotFoundException(String id)
	{
		super(NUMERIC_ERROR_CODE, ERROR_CODE, "The loginSession [{}] can not be found.", id);
	}
}
