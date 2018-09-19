package com.victor.wang.bigCrab.dao;

import com.victor.wang.bigCrab.model.Card;
import org.apache.commons.lang3.StringUtils;
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
			if(StringUtils.isBlank(cardNumber)){
				return this;
			}
			return add("cardNumber", cardNumber);
		}

		public CardQueryBuild filterByStatus(String status)
		{
			if(StringUtils.isBlank(status)){
				return this;
			}
			return add("status", status);
		}

		public static CardQueryBuild build()
		{
			return new CardQueryBuild();
		}
	}

}
