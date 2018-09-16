package com.victor.wang.bigCrab.sharedObject;

import com.victor.wang.bigCrab.model.base.BaseEntity;
import java.util.Date;

public class UserCreate
		extends BaseEntity
{
	/**
	 * the loginName
	 */
	private String loginName;

	/**
	 * the password
	 */
	private String password;

	/**
	 * the lastLoginAt
	 */
	private Date lastLoginAt;

	/**
	 * the lastLoginIp
	 */
	private Date lastLoginIp;

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

	public Date getLastLoginAt()
	{
		return lastLoginAt;
	}

	public void setLastLoginAt(Date lastLoginAt)
	{
		this.lastLoginAt = lastLoginAt;
	}

	public Date getLastLoginIp()
	{
		return lastLoginIp;
	}

	public void setLastLoginIp(Date lastLoginIp)
	{
		this.lastLoginIp = lastLoginIp;
	}

}
