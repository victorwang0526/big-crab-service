package com.victor.wang.bigCrab.model;
import com.victor.wang.bigCrab.model.base.AuditedMysqlEntity;
import java.util.Date;

public class User
	extends AuditedMysqlEntity
{

	/**
	 * 
	 */
	private String id;

	/**
	 * 
	 */
	private String loginName;

	/**
	 * 
	 */
	private String password;

	/**
	 * 
	 */
	private Date lastLoginAt;

	/**
	 * 
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