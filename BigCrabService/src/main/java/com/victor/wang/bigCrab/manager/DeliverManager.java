package com.victor.wang.bigCrab.manager;

import com.victor.wang.bigCrab.dao.DeliverDao;
import com.victor.wang.bigCrab.exception.DeliverNotFoundException;
import com.victor.wang.bigCrab.model.Deliver;
import com.victor.wang.bigCrab.sharedObject.DeliverCreate;
import com.victor.wang.bigCrab.sharedObject.DeliverUpdate;
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
public class DeliverManager
{
	private static final Logger LOGGER = LoggerFactory.getLogger(DeliverManager.class);

	@Autowired
	DeliverDao deliverDao;

	@Autowired
	private MapperFacade mapper;

	public Deliver getDeliver(String id)
	{
		LOGGER.debug("DeliverManager, getDeliver; id: {}", id);

		Deliver deliver = deliverDao.get(id);
		if (deliver == null)
		{
			throw new DeliverNotFoundException(id);
		}
		return deliver;
	}

	public Deliver createDeliver(@AssertValid DeliverCreate deliverCreate)
	{
		LOGGER.info("DeliverManager, createDeliver; deliverCreate: {}", deliverCreate);
		Deliver deliver = mapper.map(deliverCreate, Deliver.class);
		deliver.setId(UniqueString.uuidUniqueString());
		DaoHelper.insert(deliverDao, deliver);
		return deliver;
	}

	public Deliver updateDeliver(String id, @AssertValid DeliverUpdate deliverUpdate)
	{
		LOGGER.info("DeliverManager, updateDeliver; id: {}, deliverUpdate: {}", id, deliverUpdate);
		Deliver deliver = getDeliver(id);
		DaoHelper.updateFromSource(deliverDao, deliverUpdate, deliver);
		return deliver;
	}

	public List<Deliver> findDelivers(
			int page,
			int size)
	{
		LOGGER.debug("DeliverManager, findDeliver; page: {}, size: {}",  page, size);
		DeliverDao.DeliverQueryBuild queryBuild = DeliverDao.DeliverQueryBuild.build()
				.pagination(page, size);
		return deliverDao.getList(queryBuild.toParameter());
	}

	public int countDelivers()
	{
		LOGGER.debug("DeliverManager, countDeliver; ");
		DeliverDao.DeliverQueryBuild queryBuild = DeliverDao.DeliverQueryBuild.build();
		return deliverDao.getCount(queryBuild.toParameter());
	}

	public void deleteDeliver(String id)
	{
		LOGGER.info("DeliverManager, deleteDeliver; id: {}", id);
		getDeliver(id);
		deliverDao.delete(id);
	}
}
