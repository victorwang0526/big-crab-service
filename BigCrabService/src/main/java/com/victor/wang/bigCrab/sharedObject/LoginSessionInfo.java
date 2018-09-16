package com.victor.wang.bigCrab.sharedObject;

import com.victor.wang.bigCrab.model.base.BaseEntity;

import java.util.Date;

public class LoginSessionInfo
		extends BaseEntity
{
	/**
	 * the id
	 */
	private String id;

	/**
	 * the userId
	 */
	private String userId;

	/**
	 * the token
	 */
	private String token;

	/**
	 * the createdAt
	 */
	private Date createdAt;

	/**
	 * the lastModifiedAt
	 */
	private Date lastModifiedAt;

	/**
	 * the rvn
	 */
	private Integer rvn;

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

	public Integer getRvn()
	{
		return rvn;
	}

	public void setRvn(Integer rvn)
	{
		this.rvn = rvn;
	}

}
