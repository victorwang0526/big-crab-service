/*
 * Victor Wang. All Rights Reserved.
 */

package com.victor.wang.bigCrab.exception.base;


public class DatabaseOperationConflictException
		extends ConflictException
{

	public static final String ERROR_CODE = "errors.com.victor.wang.bigCrab.database_conflict_error";
	public static final int NUMERIC_ERROR_CODE = NumericErrorCodes.DATABASE_CONFLICT_ERROR;

	public DatabaseOperationConflictException(String pattern, Object... args)
	{
		super(NUMERIC_ERROR_CODE, ERROR_CODE, pattern, args);
	}
}
