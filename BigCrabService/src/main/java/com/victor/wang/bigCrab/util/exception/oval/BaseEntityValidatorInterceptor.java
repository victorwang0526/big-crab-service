/*
 * Victor Wang. All Rights Reserved.
 */
package com.victor.wang.bigCrab.util.exception.oval;

import com.victor.wang.bigCrab.exception.base.BadRequestException;
import com.victor.wang.bigCrab.model.base.BaseEntity;
import net.sf.oval.Validator;
import net.sf.oval.configuration.annotation.AnnotationsConfigurer;
import net.sf.oval.constraint.AssertValid;
import net.sf.oval.integration.spring.BeanInjectingCheckInitializationListener;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 */
public class BaseEntityValidatorInterceptor
		implements MethodInterceptor
{
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseEntityValidatorInterceptor.class);

	protected final Map<Method, Boolean> methodsRequiringValidation = new ConcurrentHashMap<>();
	protected final Map<Method, Integer> paramsRequiringValidation = new ConcurrentHashMap<>();
	protected final Validator validator;


	public BaseEntityValidatorInterceptor()
	{
		// let spring inject dependencies to checkers
		AnnotationsConfigurer configurer = new AnnotationsConfigurer();
		configurer.addCheckInitializationListener(BeanInjectingCheckInitializationListener.INSTANCE);
		validator = new Validator(configurer);
		validator.setExceptionTranslator(new StandardExceptionTranslator());
		ValidatorHolder.setValidator(validator);
	}

	@Override
	public Object invoke(MethodInvocation invocation)
			throws Throwable
	{
		Method method = invocation.getMethod();
		if (!methodsRequiringValidation.containsKey(method))
		{
			determineIfMethodNeedsValidation(method);
		}

		if (!methodsRequiringValidation.get(method))
		{
			return invocation.proceed();
		}

		// do our param validation
		int paramNum = paramsRequiringValidation.get(method);
		LOGGER.debug("Validating param {} for method {}.{} ", paramNum, method.getDeclaringClass().getName(), method.getName());
		if (invocation.getArguments()[paramNum] == null)
		{
			throw new BadRequestException(-1, "errors.com.victor.wang.common.missing_param", "One or more required parameters is null");
		}
		validator.assertValid(invocation.getArguments()[paramNum]);

		return invocation.proceed();
	}

	private void determineIfMethodNeedsValidation(Method method)
	{
		LOGGER.debug("Determining if method parameters for {}.{} need base validation", method.getDeclaringClass().getName(), method.getName());

		Annotation[][] paramAnnotations = method.getParameterAnnotations();
		Class[] parameterTypes = method.getParameterTypes();
		int argRequiringValidation = -1;
		for (int paramNum = 0; paramNum < paramAnnotations.length; paramNum++)
		{
			for (int annoNum = 0; annoNum < paramAnnotations[paramNum].length; annoNum++)
			{
				Annotation anno = paramAnnotations[paramNum][annoNum];
				if (AssertValid.class.isAssignableFrom(anno.getClass()) && BaseEntity.class.isAssignableFrom(parameterTypes[paramNum]))
				{
					LOGGER.debug("Method {}.{} needs parameter {} validated", method.getDeclaringClass().getName(), method.getName(), paramNum);
					argRequiringValidation = paramNum;
					break;
				}
			}
		}

		if (argRequiringValidation > -1)
		{
			methodsRequiringValidation.put(method, true);
			paramsRequiringValidation.put(method, argRequiringValidation);
		}
		else
		{
			methodsRequiringValidation.put(method, false);
		}
	}
}
