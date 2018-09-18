package com.victor.wang.bigCrab.sharedObject;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * Created by victor.wang on 2018/9/18.
 */
public class CardRedeemRequest
{
	@NotNull
	@NotBlank
	private String password;

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}
}
