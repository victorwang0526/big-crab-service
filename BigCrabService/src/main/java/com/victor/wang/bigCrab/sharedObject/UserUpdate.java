package com.victor.wang.bigCrab.sharedObject;

import com.victor.wang.bigCrab.model.base.BaseEntity;
import org.joda.time.DateTime;

public class UserUpdate
		extends BaseEntity
{
	/**
	 * the loginName
	 */
	private String loginName = UNSET_STRING;

	/**
	 * the password
	 */
	private String password = UNSET_STRING;

	public String getLoginName()
	{
		return loginName;
	}

	public void setLoginName(String loginName)
	{
		this.loginName = loginName;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}


}
