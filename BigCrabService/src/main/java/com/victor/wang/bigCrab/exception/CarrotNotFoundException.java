package com.victor.wang.bigCrab.exception;

import com.victor.wang.bigCrab.exception.base.NotFoundException;
import com.victor.wang.bigCrab.exception.base.NumericErrorCodes;

public class CarrotNotFoundException
	extends NotFoundException
{
	public static final int NUMERIC_ERROR_CODE = NumericErrorCodes.CARROT_NOT_FOUND;
	public static final String ERROR_CODE = "errors.com.victor.wang.bigCrab.carrot_not_found";

	public CarrotNotFoundException(String id)
	{
		super(NUMERIC_ERROR_CODE, ERROR_CODE, "The carrot [{}] can not be found.", id);
	}
}
