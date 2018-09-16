package com.victor.wang.bigCrab.manager;

import com.victor.wang.bigCrab.dao.LoginSessionDao;
import com.victor.wang.bigCrab.exception.LoginSessionNotFoundException;
import com.victor.wang.bigCrab.model.LoginSession;
import com.victor.wang.bigCrab.sharedObject.LoginSessionCreate;
import com.victor.wang.bigCrab.sharedObject.LoginSessionUpdate;
import com.victor.wang.bigCrab.util.UniqueString;
import com.victor.wang.bigCrab.util.dao.DaoHelper;
import ma.glasnost.orika.MapperFacade;
import net.sf.oval.constraint.AssertValid;
import net.sf.oval.guard.Guarded;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Guarded
public class LoginSessionManager
{
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginSessionManager.class);

	@Autowired
	LoginSessionDao loginSessionDao;

	@Autowired
	private MapperFacade mapper;

	public LoginSession getLoginSession(String id)
	{
		LOGGER.debug("LoginSessionManager, getLoginSession; id: {}", id);

		LoginSession loginSession = loginSessionDao.get(id);
		if (loginSession == null)
		{
			throw new LoginSessionNotFoundException(id);
		}
		return loginSession;
	}

	public LoginSession createLoginSession(@AssertValid LoginSessionCreate loginSessionCreate)
	{
		LOGGER.info("LoginSessionManager, createLoginSession; loginSessionCreate: {}", loginSessionCreate);
		LoginSession loginSession = mapper.map(loginSessionCreate, LoginSession.class);
		loginSession.setId(UniqueString.uuidUniqueString());
		DaoHelper.insert(loginSessionDao, loginSession);
		return loginSession;
	}

	public LoginSession updateLoginSession(String id, @AssertValid LoginSessionUpdate loginSessionUpdate)
	{
		LOGGER.info("LoginSessionManager, updateLoginSession; id: {}, loginSessionUpdate: {}", id, loginSessionUpdate);
		LoginSession loginSession = getLoginSession(id);
		DaoHelper.updateFromSource(loginSessionDao, loginSessionUpdate, loginSession);
		return loginSession;
	}

	public List<LoginSession> findLoginSessions(
			int page,
			int size)
	{
		LOGGER.debug("LoginSessionManager, findLoginSession; page: {}, size: {}",  page, size);
		LoginSessionDao.LoginSessionQueryBuild queryBuild = LoginSessionDao.LoginSessionQueryBuild.build()
				.pagination(page, size);
		return loginSessionDao.getList(queryBuild.toParameter());
	}

	public int countLoginSessions()
	{
		LOGGER.debug("LoginSessionManager, countLoginSession; ");
		LoginSessionDao.LoginSessionQueryBuild queryBuild = LoginSessionDao.LoginSessionQueryBuild.build();
		return loginSessionDao.getCount(queryBuild.toParameter());
	}

	public void deleteLoginSession(String id)
	{
		LOGGER.info("LoginSessionManager, deleteLoginSession; id: {}", id);
		getLoginSession(id);
		loginSessionDao.delete(id);
	}
}
