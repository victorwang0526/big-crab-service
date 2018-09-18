package com.victor.wang.bigCrab.dao;

import com.victor.wang.bigCrab.model.Card;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardDao
		extends GenericDao<String, Card>
{

	public Card getByCardNumber(@Param("cardNumber") String cardNumber);

	class CardQueryBuild
			extends QueryBuilder
	{
		public CardQueryBuild filterByCardNumber(String cardNumber)
		{
			return add("cardNumber", cardNumber);
		}

		public CardQueryBuild filterByStatus(String status)
		{
			return add("status", status);
		}

		public static CardQueryBuild build()
		{
			return new CardQueryBuild();
		}
	}

}
