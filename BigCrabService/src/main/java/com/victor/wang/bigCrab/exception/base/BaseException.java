/*
 * Victor Wang. All Rights Reserved.
 */
package com.victor.wang.bigCrab.exception.base;

import org.slf4j.helpers.MessageFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * A base exception class for application errors that will not be returned to end users.
 */
public abstract class BaseException
		extends RuntimeException
{
	protected String message = "";
	protected String messageTemplate = "";
	protected List<String> messageVars = new ArrayList<>(0);

	protected BaseException()
	{
		super();
	}

	protected BaseException(String pattern, Object... args)
	{
		super();
		this.messageTemplate = pattern;
		this.message = MessageFormatter.arrayFormat(pattern, args).getMessage();
		this.populateMessageVars(args);
	}

	protected BaseException(Exception e)
	{
		super(e);
		this.message = super.getMessage();
	}

	protected BaseException(Exception e, String pattern, Object... args)
	{
		super(e);
		this.messageTemplate = pattern;
		this.message = MessageFormatter.arrayFormat(pattern, args).getMessage();
		this.populateMessageVars(args);
	}

	@Override
	public String getMessage()
	{
		return message;
	}

	public void setMessage(String newMessage)
	{
		this.message = newMessage;
	}

	public List<String> getMessageVars()
	{
		return messageVars;
	}

	public void setMessageVars(List<String> newMessageVars)
	{
		this.messageVars = newMessageVars;
	}

	protected final void populateMessageVars(Object... args)
	{
		if (args.length > 0)
		{
			this.messageVars = new ArrayList<>(args.length);
			for (Object arg : args)
			{
				messageVars.add(MessageFormatter.format("{}", arg).getMessage());
			}
		}
	}

	public String getMessageTemplate()
	{
		return messageTemplate;
	}

	public void setMessageTemplate(String newMessageTemplate)
	{
		this.messageTemplate = newMessageTemplate;
	}
}
