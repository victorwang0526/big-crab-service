package com.victor.wang.bigCrab.sharedObject;


import com.victor.wang.bigCrab.model.base.BaseEntity;

public class UserToken
		extends BaseEntity
{
	private String token;

	private UserInfo userInfo;

	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}

	public UserInfo getUserInfo()
	{
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo)
	{
		this.userInfo = userInfo;
	}
}
