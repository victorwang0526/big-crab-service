/*
 * Victor Wang. All Rights Reserved.
 */
package com.victor.wang.bigCrab.exception.base;

import org.slf4j.helpers.MessageFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * An exception representing a object validation that failed.
 */
public class ValidationException
		extends ApplicationException
{
	public static final String VALIDATION_EXCEPTION_ERROR_CODE = "errors.com.victor.wang.validation.validation_failed";
	public static final String ERROR_MESSAGE = "Validation Failed. Invalid fields were {}";
	public static final int DEFAULT_NUMERIC_ERROR_CODE = 1040;
	public static final int STATUS_CODE = HttpStatusCodeConstants.BAD_REQUEST;

	protected HashMap<String, FieldValidationFailure> validationFailures = new HashMap<>();

	/**
	 * Create a new ValidationException from a Map of ValidationFailures
	 *
	 * @param fieldValidationFailures The invalid fields triggering this exception.
	 */
	public ValidationException(Map<String, FieldValidationFailure> fieldValidationFailures)
	{
		super(STATUS_CODE, VALIDATION_EXCEPTION_ERROR_CODE, DEFAULT_NUMERIC_ERROR_CODE, ERROR_MESSAGE,
				fieldValidationFailures == null ? null : fieldValidationFailures.keySet());
		if (fieldValidationFailures != null)
		{
			this.validationFailures = new HashMap<>(fieldValidationFailures);
		}
	}

	/**
	 * DO NOT USE THIS CTOR, as we want to standardize our error message to include the invalid field names
	 *
	 * @param fieldValidationFailures The invalid fields triggering this exception.
	 * @param pattern                 The error message pattern for this exception.
	 * @param args                    The error message var substitutions.
	 */
	@Deprecated
	public ValidationException(Map<String, FieldValidationFailure> fieldValidationFailures,
							   String pattern, Object... args)
	{
		super(STATUS_CODE, VALIDATION_EXCEPTION_ERROR_CODE, DEFAULT_NUMERIC_ERROR_CODE, pattern, args);
		this.validationFailures = new HashMap<>(fieldValidationFailures);
	}

	public HashMap<String, FieldValidationFailure> getValidationFailures()
	{
		return validationFailures;
	}

	public String toString()
	{
		String message = super.toString();
		ArrayList<String> list = new ArrayList<>(this.validationFailures.size());
		for (FieldValidationFailure failure : validationFailures.values())
		{
			list.add(failure.getErrorMessage());
		}
		message = MessageFormatter.arrayFormat(message + " {}", new Object[]{list}).getMessage();
		return message;
	}
}
