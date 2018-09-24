package com.victor.wang.bigCrab.sharedObject;

import com.victor.wang.bigCrab.model.base.BaseEntity;

public class SfRoute
	extends BaseEntity
{
	private String acceptTime;

	private String acceptAddress;

	private String remark;

	private String opcode;

	public String getAcceptTime()
	{
		return acceptTime;
	}

	public void setAcceptTime(String acceptTime)
	{
		this.acceptTime = acceptTime;
	}

	public String getAcceptAddress()
	{
		return acceptAddress;
	}

	public void setAcceptAddress(String acceptAddress)
	{
		this.acceptAddress = acceptAddress;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public String getOpcode()
	{
		return opcode;
	}

	public void setOpcode(String opcode)
	{
		this.opcode = opcode;
	}
}
