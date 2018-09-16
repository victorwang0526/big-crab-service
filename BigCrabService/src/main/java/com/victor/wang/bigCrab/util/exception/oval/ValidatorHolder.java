/*
 * Victor Wang. All Rights Reserved.
 */
package com.victor.wang.bigCrab.util.exception.oval;

import net.sf.oval.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Singleton validator holder to hold our validator instance. Used by the interceptors.
 */
public final class ValidatorHolder
{
	private static final Logger LOGGER = LoggerFactory.getLogger(ValidatorHolder.class);

	private static ValidatorHolder instance;
	private static AtomicReference<Validator> validatorRef = new AtomicReference<>(null);

	private ValidatorHolder()
	{

	}

	/**
	 * Package protected reset method. DO NOT call this during normal operation
	 */
	public static synchronized void reset()
	{
		validatorRef.set(null);
	}

	/**
	 * Set the validator. Subsequent calls to this set the validator
	 * will throw RuntimeException.
	 */
	public static synchronized void setValidator(Validator val)
	{
		Validator validator = validatorRef.get();
		if (validator != null)
		{
			LOGGER.error("Validator set twice old value {} new value {}",
					validator, val);
		}
		validatorRef.set(val);
	}

	/**
	 * Returns the registered validator instance. Null if not set.
	 */
	public static Validator getValidator()
	{
		return validatorRef.get();
	}
}
