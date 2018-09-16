/*
 * Victor Wang. All Rights Reserved.
 */
package com.victor.wang.bigCrab.exception.base;


/**
 * An exception thrown to indicate that an attempt has been made to access an object that does not exist. Status code 404.
 * Default numeric error code 1004.
 */
public class NotFoundException
		extends ApplicationException
{
	public static final String DEFAULT_ERROR_CODE = "errors.com.victor.wang.not_found";
	public static final int STATUS_CODE = HttpStatusCodeConstants.NOT_FOUND;
	public static final int DEFAULT_NUMERIC_ERROR_CODE = 1004;

	/**
	 * Create a new exception with an errorCode numericErrorCode message pattern, and an optional array of substitution
	 * variables for the message pattern.
	 *
	 * @param numericErrorCode The unique numeric error code for this exception. Refer to the wiki.
	 * @param errorCode        The error code for this exception should have the prefix errors.com.victor.wang  If the error code supplied
	 *                         is null, then the value of DEFAULT_ERROR_CODE will be used.
	 * @param pattern          The message for the exception possibly containing substitution markers with the format {}
	 * @param args             An array of Objects on which toString() will be used so that they can be substituted into the pattern.
	 */
	public NotFoundException(int numericErrorCode, String errorCode, String pattern, Object... args)
	{
		super(STATUS_CODE, errorCode != null ? errorCode : DEFAULT_ERROR_CODE, numericErrorCode, pattern, args);
	}
}
