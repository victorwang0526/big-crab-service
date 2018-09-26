package com.victor.wang.bigCrab.filter;

import com.victor.wang.bigCrab.dao.LoginSessionDao;
import com.victor.wang.bigCrab.dao.UserDao;
import com.victor.wang.bigCrab.exception.base.BadRequestException;
import com.victor.wang.bigCrab.model.LoginSession;
import com.victor.wang.bigCrab.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import java.io.IOException;
import java.util.List;

public class AuthorizationInterceptor

		implements ContainerRequestFilter
{
	public static final String TOKEN = "token";

	@Autowired
	private LoginSessionDao loginSessionDao;

	@Autowired
	private UserDao userDao;

	@Override
	public void filter(ContainerRequestContext requestContext)
			throws IOException
	{
		String path = requestContext.getUriInfo().getAbsolutePath().getPath();

		if (requestContext.getMethod().toUpperCase().equals("OPTIONS")
				||!path.contains("/api/")
				|| path.contains("/login")
				|| path.contains("/register")
				|| path.contains("/findPassword")
				|| path.contains("/export")
				|| path.contains("/print")
				|| path.contains("excel")
				|| path.contains("ckeditor")
				|| path.contains("validate")
				|| path.contains("redeem"))
		{
			return;
		} else {

			String token = requestContext.getHeaderString(TOKEN);
			if(token == null || StringUtils.isEmpty(token)){
				throw new BadRequestException(401, "authorization_needed", "请输入token");
			}

			List<LoginSession> loginSessions = loginSessionDao.getByToken(token);

			if(loginSessions == null || loginSessions.size() == 0){
				throw new BadRequestException(403, "authorization_error", "无效token");
			}
			LoginSession loginSession = loginSessions.get(0);

			User user = userDao.get(loginSession.getUserId());
			if(user == null){
				throw new BadRequestException(403, "authrization_error", "无效的用户");
			}

			AuthorizationManager.logonUser.set(user);
			AuthorizationManager.loginSession.set(loginSession);

		}
	}
}
