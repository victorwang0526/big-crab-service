package com.victor.wang.bigCrab.dao;

import com.victor.wang.bigCrab.model.Card;
import org.springframework.stereotype.Repository;

@Repository
public interface CardDao
		extends GenericDao<String, Card>
{

	class CardQueryBuild
			extends QueryBuilder
	{

		public static CardQueryBuild build()
		{
			return new CardQueryBuild();
		}
	}

}
