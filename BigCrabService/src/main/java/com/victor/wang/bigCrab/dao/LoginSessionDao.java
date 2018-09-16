package com.victor.wang.bigCrab.dao;

import com.victor.wang.bigCrab.model.LoginSession;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginSessionDao
		extends GenericDao<String, LoginSession>
{


	void deleteByUserId(@Param("userId") String userId);

	List<LoginSession> getByToken(@Param("token") String token);

	class LoginSessionQueryBuild
			extends QueryBuilder
	{

		public static LoginSessionQueryBuild build()
		{
			return new LoginSessionQueryBuild();
		}
	}

}
