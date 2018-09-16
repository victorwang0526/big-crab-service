/*
 * Victor Wang. All Rights Reserved.
 */

package com.victor.wang.bigCrab.model.base;

import javax.persistence.Column;
import java.util.Date;

public class AuditedMysqlEntity
		extends BaseEntity
{
	private Date createdAt;
	private Date lastModifiedAt;
	private int rvn;

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

	public int getRvn()
	{
		return rvn;
	}

	public void setRvn(int rvn)
	{
		this.rvn = rvn;
	}
}
