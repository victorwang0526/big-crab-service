/*
 * Victor Wang. All Rights Reserved.
 */
package com.victor.wang.bigCrab.exception.base;


/**
 * <p>
 * A base exception class for all application errors that will be returned to end users. Contains an errorCode and a
 * numericErrorCode that can be sent to a CSR by the end user, a statusCode that controls an HTTP response code if
 * this exception is turned into a response, and a human readable message.
 * </p>
 * <p>
 * Because these errors are intended for end user consumption, their stack traces aren't normally logged.
 * </p>
 * Status code 500. Default numeric error code -1.
 */
public abstract class ApplicationException
		extends BaseException
{
	public static final String DEFAULT_ERROR_CODE = "errors.com.victor.wang.unknown_error";
	public static final int DEFAULT_NUMERIC_ERROR_CODE = -1;
	public static final int DEFAULT_STATUS_CODE = HttpStatusCodeConstants.INTERNAL_SERVER_ERROR;
	protected String errorCode = DEFAULT_ERROR_CODE;
	protected int statusCode = DEFAULT_STATUS_CODE;
	protected int numericErrorCode = DEFAULT_NUMERIC_ERROR_CODE;
	protected String originatingService;

	protected ApplicationException()
	{
		super();
		this.originatingService = "carrot";
	}

	/**
	 * Create a new exception with a status code an errorCode message pattern, and an optional array of substitution
	 * variables for the message pattern.
	 *
	 * @param status         The status code for this exception.
	 * @param errCode        The error code for this exception should have the prefix errors.com.victor.wang
	 *                       If the error code supplied is null, then the value of DEFAULT_ERROR_CODE will be used.
	 * @param numericErrCode Numeric code assigned to the error.
	 * @param pattern        The message for the exception possibly containing substitution markers with the format {}
	 * @param args           An array of Objects on which toString() will be used so that they can be substituted into
	 *                       the pattern.
	 */
	protected ApplicationException(int status, String errCode, int numericErrCode, String pattern, Object... args)
	{
		super(pattern, args);
		this.errorCode = errCode != null ? errCode : DEFAULT_ERROR_CODE;
		this.statusCode = status;
		this.numericErrorCode = numericErrCode;
		this.originatingService = "0.1";
	}

	public String getErrorCode()
	{
		return errorCode;
	}

	public void setErrorCode(String newErrorCode)
	{
		this.errorCode = newErrorCode;
	}

	public int getStatusCode()
	{
		return statusCode;
	}

	public void setStatusCode(int newStatusCode)
	{
		this.statusCode = newStatusCode;
	}

	public int getNumericErrorCode()
	{
		return numericErrorCode;
	}

	public void setNumericErrorCode(int newNumericErrorCode)
	{
		this.numericErrorCode = newNumericErrorCode;
	}

	public String getOriginatingService()
	{
		return originatingService;
	}

	public void setOriginatingService(String newOriginatingService)
	{
		this.originatingService = newOriginatingService;
	}

}
