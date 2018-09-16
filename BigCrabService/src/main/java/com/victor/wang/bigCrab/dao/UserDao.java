package com.victor.wang.bigCrab.dao;

import com.victor.wang.bigCrab.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao
		extends GenericDao<String, User>
{


	public List<User> getUserLogin(@Param("username") String username);

	class UserQueryBuild
			extends QueryBuilder
	{

		public static UserQueryBuild build()
		{
			return new UserQueryBuild();
		}
	}

}
