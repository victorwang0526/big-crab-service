package com.victor.wang.bigCrab.sharedObject;

import com.victor.wang.bigCrab.model.base.BaseEntity;
import org.joda.time.DateTime;

public class LoginSessionUpdate
		extends BaseEntity
{
	/**
	 * the userId
	 */
	private String userId = UNSET_STRING;

	/**
	 * the token
	 */
	private String token = UNSET_STRING;

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}

}
