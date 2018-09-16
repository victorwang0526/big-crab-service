package com.victor.wang.bigCrab.sharedObject;

import com.victor.wang.bigCrab.model.base.BaseEntity;
import java.util.Date;

public class LoginSessionCreate
		extends BaseEntity
{
	/**
	 * the userId
	 */
	private String userId;

	/**
	 * the token
	 */
	private String token;

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
