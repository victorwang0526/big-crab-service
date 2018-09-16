package com.victor.wang.bigCrab.model;
import com.victor.wang.bigCrab.model.base.AuditedMysqlEntity;
import java.util.Date;

public class LoginSession
	extends AuditedMysqlEntity
{

	/**
	 * 
	 */
	private String id;

	/**
	 * 
	 */
	private String userId;

	/**
	 * 
	 */
	private String token;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

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