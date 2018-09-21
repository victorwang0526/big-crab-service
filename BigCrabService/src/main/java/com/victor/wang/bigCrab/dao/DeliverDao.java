package com.victor.wang.bigCrab.dao;

import com.victor.wang.bigCrab.model.Deliver;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliverDao
		extends GenericDao<String, Deliver>
{

	public Deliver getByCardNumber(@Param("cardNumber") String cardNumber);

	class DeliverQueryBuild
			extends QueryBuilder
	{

		public static DeliverQueryBuild build()
		{
			return new DeliverQueryBuild();
		}
	}

}
