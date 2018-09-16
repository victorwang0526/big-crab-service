package com.victor.wang.bigCrab.manager;

import com.victor.wang.bigCrab.dao.LoginSessionDao;
import com.victor.wang.bigCrab.dao.UserDao;
import com.victor.wang.bigCrab.exception.UserNotFoundException;
import com.victor.wang.bigCrab.exception.base.BadRequestException;
import com.victor.wang.bigCrab.model.LoginSession;
import com.victor.wang.bigCrab.model.User;
import com.victor.wang.bigCrab.sharedObject.UserCreate;
import com.victor.wang.bigCrab.sharedObject.UserInfo;
import com.victor.wang.bigCrab.sharedObject.UserToken;
import com.victor.wang.bigCrab.sharedObject.UserUpdate;
import com.victor.wang.bigCrab.util.MD5Util;
import com.victor.wang.bigCrab.util.UniqueString;
import com.victor.wang.bigCrab.util.dao.DaoHelper;
import ma.glasnost.orika.MapperFacade;
import net.sf.oval.constraint.AssertValid;
import net.sf.oval.guard.Guarded;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
@Guarded
public class UserManager
{
	private static final Logger LOGGER = LoggerFactory.getLogger(UserManager.class);

	@Autowired
	UserDao userDao;

	@Autowired
	private MapperFacade mapper;

	@Autowired
	private LoginSessionDao loginSessionDao;

	public UserToken loginUser(String authorization)
	{

		String up = authorization.replace("Basic ", "");
		String encoding[] = new String(Base64.getDecoder().decode(up)).split(":");
		String username = encoding[0];
		String password = encoding[1];

		List<User> users = userDao.getUserLogin(username);

		if (users == null || users.size() == 0)
		{
			throw new BadRequestException(400, "username_not_found", "用户名不存在！");
		}

		User user = users.get(0);

		if (!user.getPassword().equals(MD5Util.encoderByMd5(password)))
		{
			throw new BadRequestException(400, "password_incorrect", "密码错误！");
		}

		return getToken(user);
	}

	private UserToken getToken(User user)
	{
		//delete old session
		loginSessionDao.deleteByUserId(user.getId());
		//save user session
		LoginSession loginSession = new LoginSession();
		loginSession.setId(UniqueString.uuidUniqueString());
		loginSession.setUserId(user.getId());
		loginSession.setToken(loginSession.getId());
		DaoHelper.insert(loginSessionDao, loginSession);

		UserToken token = new UserToken();
		token.setToken(loginSession.getToken());

		token.setUserInfo(mapper.map(user, UserInfo.class));
		return token;
	}

	public User getUser(String id)
	{
		LOGGER.debug("UserManager, getUser; id: {}", id);

		User user = userDao.get(id);
		if (user == null)
		{
			throw new UserNotFoundException(id);
		}
		return user;
	}

	public User createUser(@AssertValid UserCreate userCreate)
	{
		LOGGER.info("UserManager, createUser; userCreate: {}", userCreate);
		User user = mapper.map(userCreate, User.class);
		user.setId(UniqueString.uuidUniqueString());
		DaoHelper.insert(userDao, user);
		return user;
	}

	public User updateUser(String id, @AssertValid UserUpdate userUpdate)
	{
		LOGGER.info("UserManager, updateUser; id: {}, userUpdate: {}", id, userUpdate);
		User user = getUser(id);
		DaoHelper.updateFromSource(userDao, userUpdate, user);
		return user;
	}

	public List<User> findUsers(
			int page,
			int size)
	{
		LOGGER.debug("UserManager, findUser; page: {}, size: {}",  page, size);
		UserDao.UserQueryBuild queryBuild = UserDao.UserQueryBuild.build()
				.pagination(page, size);
		return userDao.getList(queryBuild.toParameter());
	}

	public int countUsers()
	{
		LOGGER.debug("UserManager, countUser; ");
		UserDao.UserQueryBuild queryBuild = UserDao.UserQueryBuild.build();
		return userDao.getCount(queryBuild.toParameter());
	}

	public void deleteUser(String id)
	{
		LOGGER.info("UserManager, deleteUser; id: {}", id);
		getUser(id);
		userDao.delete(id);
	}
}
