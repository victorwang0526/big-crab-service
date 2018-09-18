package com.victor.wang.bigCrab.manager;

import com.victor.wang.bigCrab.dao.CardDao;
import com.victor.wang.bigCrab.exception.CardNotFoundException;
import com.victor.wang.bigCrab.model.Card;
import com.victor.wang.bigCrab.sharedObject.CardCreate;
import com.victor.wang.bigCrab.sharedObject.CardStatus;
import com.victor.wang.bigCrab.sharedObject.CardUpdate;
import com.victor.wang.bigCrab.util.UniqueString;
import com.victor.wang.bigCrab.util.dao.DaoHelper;
import ma.glasnost.orika.MapperFacade;
import net.sf.oval.constraint.AssertValid;
import net.sf.oval.guard.Guarded;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Guarded
public class CardManager
{
	private static final Logger LOGGER = LoggerFactory.getLogger(CardManager.class);

	@Autowired
	CardDao cardDao;

	@Autowired
	private MapperFacade mapper;

	public void createCards(List<Card> cards)
	{
		for (Card card : cards)
		{
			if (card == null || StringUtils.isEmpty(card.getCardNumber()))
			{
				continue;
			}
			Card cardAlready = cardDao.getByCardNumber(card.getCardNumber());
			if (cardAlready != null)
			{
				continue;
			}
			DaoHelper.insert(cardDao, card);
		}
	}

	public Card getCard(String cardNumber)
	{
		LOGGER.debug("CardManager, getCard; cardNumber: {}", cardNumber);

		Card card = cardDao.getByCardNumber(cardNumber);
		if (card == null)
		{
			throw new CardNotFoundException(cardNumber);
		}
		return card;
	}
//
//	public Card createCard(@AssertValid CardCreate cardCreate)
//	{
//		LOGGER.info("CardManager, createCard; cardCreate: {}", cardCreate);
//		Card card = mapper.map(cardCreate, Card.class);
//		card.setId(UniqueString.uuidUniqueString());
//		DaoHelper.insert(cardDao, card);
//		return card;
//	}

//	public Card updateCard(String id, @AssertValid CardUpdate cardUpdate)
//	{
//		LOGGER.info("CardManager, updateCard; id: {}, cardUpdate: {}", id, cardUpdate);
//		Card card = getCard(id);
//		DaoHelper.updateFromSource(cardDao, cardUpdate, card);
//		return card;
//	}

	public List<Card> findCards(
			String cardNumber,
			String status,
			int page,
			int size)
	{
		LOGGER.debug("CardManager, findCard; page: {}, size: {}", page, size);
		CardDao.CardQueryBuild queryBuild = CardDao.CardQueryBuild.build()
				.filterByCardNumber(cardNumber)
				.filterByStatus(status)
				.pagination(page, size);
		return cardDao.getList(queryBuild.toParameter());
	}

	public int countCards(String cardNumber,
						  String status)
	{
		LOGGER.debug("CardManager, countCard; ");
		CardDao.CardQueryBuild queryBuild = CardDao.CardQueryBuild.build()
				.filterByCardNumber(cardNumber)
				.filterByStatus(status);
		return cardDao.getCount(queryBuild.toParameter());
	}

	public Card markPhone(String cardNumber)
	{
		Card card = this.getCard(cardNumber);
		card.setRedeemAt(DateTime.now().toDate());
		card.setStatus(CardStatus.PHONED.name());
		DaoHelper.doUpdate(cardDao, card);
		return card;
	}

	public Card unfrozen(String cardNumber)
	{
		Card card = this.getCard(cardNumber);
		card.setStatus(CardStatus.UNUSED.name());
		DaoHelper.doUpdate(cardDao, card);
		return card;
	}

//	public void deleteCard(String id)
//	{
//		LOGGER.info("CardManager, deleteCard; id: {}", id);
//		getCard(id);
//		cardDao.delete(id);
//	}
}
