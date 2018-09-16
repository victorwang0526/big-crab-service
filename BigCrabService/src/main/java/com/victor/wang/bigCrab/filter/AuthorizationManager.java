package com.victor.wang.bigCrab.filter;


import com.victor.wang.bigCrab.model.LoginSession;
import com.victor.wang.bigCrab.model.User;

public class AuthorizationManager
{
	public static ThreadLocal<LoginSession> loginSession = new ThreadLocal<>();
	public static ThreadLocal<User> logonUser = new ThreadLocal<>();

}
