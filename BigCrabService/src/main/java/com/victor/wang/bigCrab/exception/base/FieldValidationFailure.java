/*
 * Victor Wang. All Rights Reserved.
 */

package com.victor.wang.bigCrab.exception.base;

import com.victor.wang.bigCrab.model.base.BaseEntity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Class used to represent a validation that failed on a particular field of an object.
 * <p>
 * NOTE: Information stored in this class will be publicly visible to end users.
 * </p>
 */
public class FieldValidationFailure
		extends BaseEntity
		implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String fieldName;
	private String errorMessage;
	private String errorCode;
	private String invalidValue;
	private Map<String, String> messageVars;

	public FieldValidationFailure(String fldName, String errMsg, String errCode, String invalidVal, Map<String, String> msgVars)
	{
		this.fieldName = fldName;
		this.errorMessage = errMsg;
		this.errorCode = errCode;
		this.invalidValue = invalidVal;
		this.messageVars = msgVars;
		if (messageVars == null)
		{
			this.messageVars = new HashMap<>();
		}
	}

	private FieldValidationFailure()
	{
	}

	/**
	 * The code for the error. This is intended for display to end users when the
	 * error message has been translated so that a CSR can send devs error information.
	 *
	 * @return The error code
	 */
	public String getErrorCode()
	{
		return errorCode;
	}

	/**
	 * The name of the field that produced the validation error.
	 *
	 * @return The name of the validation failing field.
	 */
	public String getFieldName()
	{
		return fieldName;
	}

	/**
	 * The validation error message for the failing fields.
	 *
	 * @return The validation error message.
	 */
	public String getErrorMessage()
	{
		return errorMessage;
	}

	/**
	 * The value of the field that caused the validation error
	 *
	 * @return The invalid field value.
	 */
	public String getInvalidValue()
	{
		return invalidValue;
	}

	/**
	 * The map of message variables used to populate the error message.
	 *
	 * @return The map of message variables.
	 */
	public Map<String, String> getMessageVars()
	{
		return messageVars;
	}
}
