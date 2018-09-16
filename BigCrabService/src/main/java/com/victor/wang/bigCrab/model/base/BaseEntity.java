/*
 * Victor Wang. All Rights Reserved.
 */
package com.victor.wang.bigCrab.model.base;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

/**
 * A base class for all DB and request entities provides generic toString functionality, and
 * a set of UNSET values that can be used to represent the UNSET state for update objects.
 */
public abstract class BaseEntity
{
	public static final String UNSET_STRING = "52b4ba9b5e30433d8dcc82091ece2520";
	public static final DateTime UNSET_DATETIME = DateTime.now().withDate(0, 1, 1);
	public static final LocalDate UNSET_LOCALDATE = LocalDate.now().withYear(0).withMonthOfYear(1).withDayOfMonth(1);
	public static final Long UNSET_LONG = Long.MAX_VALUE;
	public static final Integer UNSET_INT = Integer.MAX_VALUE;

	public static final String[] defaultToStringExcludeFieldNames = {"password", "secret", "encodedPassword"};

	@Override
	public String toString()
	{
		return ReflectionToStringBuilder.toStringExclude(this, this.getToStringExcludeFieldNames());
	}

	protected String[] getToStringExcludeFieldNames()
	{
		return defaultToStringExcludeFieldNames;
	}
}
