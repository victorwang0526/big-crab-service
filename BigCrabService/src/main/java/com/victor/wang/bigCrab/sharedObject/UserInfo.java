package com.victor.wang.bigCrab.sharedObject;

import com.victor.wang.bigCrab.model.base.BaseEntity;

import java.util.Date;

public class UserInfo
		extends BaseEntity
{
	/**
	 * the id
	 */
	private String id;

	/**
	 * the loginName
	 */
	private String loginName;

	/**
	 * the password
	 */
	private String password;

	/**
	 * the createdAt
	 */
	private Date createdAt;

	/**
	 * the lastModifiedAt
	 */
	private Date lastModifiedAt;

	/**
	 * the lastLoginAt
	 */
	private Date lastLoginAt;

	/**
	 * the lastLoginIp
	 */
	private Date lastLoginIp;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

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

	public Date getCreatedAt()
	{
		return createdAt;
	}

	public void setCreatedAt(Date createdAt)
	{
		this.createdAt = createdAt;
	}

	public Date getLastModifiedAt()
	{
		return lastModifiedAt;
	}

	public void setLastModifiedAt(Date lastModifiedAt)
	{
		this.lastModifiedAt = lastModifiedAt;
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
