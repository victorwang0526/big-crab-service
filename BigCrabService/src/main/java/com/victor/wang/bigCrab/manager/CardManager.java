package com.victor.wang.bigCrab.manager;

import com.victor.wang.bigCrab.dao.CardDao;
import com.victor.wang.bigCrab.exception.CardNotFoundException;
import com.victor.wang.bigCrab.model.Card;
import com.victor.wang.bigCrab.sharedObject.CardCreate;
import com.victor.wang.bigCrab.sharedObject.CardUpdate;
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
public class CardManager
{
	private static final Logger LOGGER = LoggerFactory.getLogger(CardManager.class);

	@Autowired
	CardDao cardDao;

	@Autowired
	private MapperFacade mapper;

	public Card getCard(String id)
	{
		LOGGER.debug("CardManager, getCard; id: {}", id);

		Card card = cardDao.get(id);
		if (card == null)
		{
			throw new CardNotFoundException(id);
		}
		return card;
	}

	public Card createCard(@AssertValid CardCreate cardCreate)
	{
		LOGGER.info("CardManager, createCard; cardCreate: {}", cardCreate);
		Card card = mapper.map(cardCreate, Card.class);
		card.setId(UniqueString.uuidUniqueString());
		DaoHelper.insert(cardDao, card);
		return card;
	}

	public Card updateCard(String id, @AssertValid CardUpdate cardUpdate)
	{
		LOGGER.info("CardManager, updateCard; id: {}, cardUpdate: {}", id, cardUpdate);
		Card card = getCard(id);
		DaoHelper.updateFromSource(cardDao, cardUpdate, card);
		return card;
	}

	public List<Card> findCards(
			int page,
			int size)
	{
		LOGGER.debug("CardManager, findCard; page: {}, size: {}",  page, size);
		CardDao.CardQueryBuild queryBuild = CardDao.CardQueryBuild.build()
				.pagination(page, size);
		return cardDao.getList(queryBuild.toParameter());
	}

	public int countCards()
	{
		LOGGER.debug("CardManager, countCard; ");
		CardDao.CardQueryBuild queryBuild = CardDao.CardQueryBuild.build();
		return cardDao.getCount(queryBuild.toParameter());
	}

	public void deleteCard(String id)
	{
		LOGGER.info("CardManager, deleteCard; id: {}", id);
		getCard(id);
		cardDao.delete(id);
	}
}
