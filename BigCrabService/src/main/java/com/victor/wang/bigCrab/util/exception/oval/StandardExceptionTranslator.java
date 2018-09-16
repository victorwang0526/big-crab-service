/*
 * Victor Wang. All Rights Reserved.
 */
package com.victor.wang.bigCrab.util.exception.oval;

import com.victor.wang.bigCrab.exception.base.FieldValidationFailure;
import com.victor.wang.bigCrab.exception.base.ValidationException;
import com.victor.wang.bigCrab.model.base.BaseEntity;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.context.ClassContext;
import net.sf.oval.context.ConstructorParameterContext;
import net.sf.oval.context.FieldContext;
import net.sf.oval.context.MethodEntryContext;
import net.sf.oval.context.MethodParameterContext;
import net.sf.oval.context.MethodReturnValueContext;
import net.sf.oval.context.OValContext;
import net.sf.oval.exception.ConstraintsViolatedException;
import net.sf.oval.exception.ExceptionTranslator;
import net.sf.oval.exception.OValException;
import net.sf.oval.internal.MessageRenderer;
import net.sf.oval.internal.util.StringUtils;
import net.sf.oval.localization.value.ToStringMessageValueFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StandardExceptionTranslator
		implements ExceptionTranslator
{

	private static final Logger LOGGER = LoggerFactory.getLogger(StandardExceptionTranslator.class);
	private List<String> excludeInvalidValueNames;

	public StandardExceptionTranslator()
	{
		LOGGER.info("Creating StandardExceptionTranslator");
		String[] excludedValues = BaseEntity.defaultToStringExcludeFieldNames;

		if (excludedValues.length == 0)
		{
			excludedValues = new String[]{"password", "secret"};
		}

		LOGGER.info("Excluded invalid value field names is: {}", Arrays.asList(excludedValues));
		excludeInvalidValueNames = new ArrayList<>(excludedValues.length);
		for (String val : excludedValues)
		{
			excludeInvalidValueNames.add(val.trim().toLowerCase());
		}
	}

	@Override
	public RuntimeException translateException(OValException ex)
	{
		if (!(ex instanceof ConstraintsViolatedException))
		{
			throw ex;
		}

		ConstraintsViolatedException cex = (ConstraintsViolatedException) ex;
		Map<String, FieldValidationFailure> fieldMap = new HashMap<>();
		for (ConstraintViolation violation : cex.getConstraintViolations())
		{
			populateFieldMap(fieldMap, violation, null);
		}

		ValidationException vex = new ValidationException(fieldMap);
		vex.setStackTrace(cex.getStackTrace());
		return vex;
	}

	private void populateFieldMap(Map<String, FieldValidationFailure> fieldMap,
								  ConstraintViolation violation, String parent)
	{
		String current = getName(violation.getContext());
		if (parent != null)
		{
			current = parent + "." + current;
		}

		ConstraintViolation[] causes = violation.getCauses();
		if (causes != null && causes.length > 0)
		{
			for (ConstraintViolation innerViolation : causes)
			{
				populateFieldMap(fieldMap, innerViolation, current);
			}
		}
		else
		{
			String message = MessageRenderer.renderMessage(violation.getMessageTemplate(), violation.getMessageVariables());
			// For ClassContext, don't return an invalidValue so we don't expose the class level toString()
			String invalidValue = "";
			if (!(violation.getContext() instanceof ClassContext))
			{
				invalidValue = ToStringMessageValueFormatter.INSTANCE.format(violation.getInvalidValue());
			}
			message = StringUtils.replaceAll(message, "{context}", current);
			message = StringUtils.replaceAll(message, "{invalidValue}", invalidValue);
			String errorCode = violation.getErrorCode();
			if (errorCode == null)
			{
				errorCode = "validation_failure";
				LOGGER.warn("Found validated field {} on class {} that does not have a validation errorCode",
						current, violation.getValidatedObject().getClass().getName());
			}
			Map<String, ? extends Serializable> messageVars = violation.getMessageVariables();
			Map<String, String> cleanMessageVars = new HashMap<>();
			if (messageVars != null)
			{
				for (Map.Entry<String, ? extends Serializable> entry : messageVars.entrySet())
				{
					cleanMessageVars.put(entry.getKey(), entry.getValue().toString());
				}
			}

			// remove invalidValues that we cannot send
			for (String excludeFieldName : excludeInvalidValueNames)
			{
				if (current.toLowerCase().contains(excludeFieldName))
				{
					LOGGER.debug("Setting invalidValue to field {} to empty because it matches the exclude value {}",
							current, excludeFieldName);
					invalidValue = "";
				}
			}

			fieldMap.put(current, new FieldValidationFailure(current, message, errorCode, invalidValue, cleanMessageVars));
		}
	}

	private String getName(OValContext context)
	{
		if (context instanceof MethodParameterContext)
		{
			return ((MethodParameterContext) context).getParameterName();
		}
		if (context instanceof ConstructorParameterContext)
		{
			return ((ConstructorParameterContext) context).getParameterName();
		}
		if (context instanceof MethodEntryContext)
		{
			return ((MethodEntryContext) context).getMethod().toString();
		}
		if (context instanceof FieldContext)
		{
			return ((FieldContext) context).getField().getName();
		}
		if (context instanceof MethodReturnValueContext)
		{
			return ((MethodReturnValueContext) context).getMethod() + ".<return>";
		}
		if (context instanceof ClassContext)
		{
			return ((ClassContext) context).getClazz().getSimpleName();
		}
		throw new IllegalArgumentException("don't know how to handle " + context.getClass().getSimpleName());
	}
}
