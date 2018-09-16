package com.victor.wang.bigCrab.dao;

import com.victor.wang.bigCrab.model.Card;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardDao
		extends GenericDao<String, Card>
{

	public List<Card> getByCardNumber(@Param("cardNumber") String cardNumber);

	class CardQueryBuild
			extends QueryBuilder
	{

		public static CardQueryBuild build()
		{
			return new CardQueryBuild();
		}
	}

}
