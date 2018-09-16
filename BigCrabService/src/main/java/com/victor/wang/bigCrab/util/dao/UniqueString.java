/*
 * Copyright (C) 2015 Epic Games, Inc. All Rights Reserved.
 */
package com.victor.wang.bigCrab.util.dao;

import java.util.UUID;

/**
 * A class that contains methods to generate unique strings
 */
public abstract class UniqueString
{

	/**
	 * Return a unique string generated from a UUID.
	 *
	 * @return The UUID based unique string.
	 */
	public static String uuidUniqueString()
	{
		return UUID.randomUUID().toString().replace("-", "");
	}
}
