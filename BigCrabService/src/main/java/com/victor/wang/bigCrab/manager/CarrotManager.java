package com.victor.wang.bigCrab.manager;

import com.victor.wang.bigCrab.dao.CarrotDao;
import com.victor.wang.bigCrab.exception.CarrotNotFoundException;
import com.victor.wang.bigCrab.model.Carrot;
import com.victor.wang.bigCrab.sharedObject.CarrotCreate;
import com.victor.wang.bigCrab.sharedObject.CarrotUpdate;
import com.victor.wang.bigCrab.util.UniqueString;
import com.victor.wang.bigCrab.util.dao.DaoHelper;
import ma.glasnost.orika.MapperFacade;
import net.sf.oval.constraint.AssertValid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarrotManager
{
	private static final Logger LOGGER = LoggerFactory.getLogger(CarrotManager.class);

	@Autowired
	CarrotDao carrotDao;

	@Autowired
	private MapperFacade mapper;

	public Carrot getCarrot(String id)
	{
		LOGGER.debug("CarrotManager, getCarrot; id: {}", id);

		Carrot carrot = carrotDao.get(id);
		if (carrot == null)
		{
			throw new CarrotNotFoundException(id);
		}
		return carrot;
	}

	public Carrot createCarrot(@AssertValid CarrotCreate carrotCreate)
	{
		LOGGER.info("CarrotManager, createCarrot; carrotCreate: {}", carrotCreate);
		Carrot carrot = mapper.map(carrotCreate, Carrot.class);
		carrot.setId(UniqueString.uuidUniqueString());
		DaoHelper.insert(carrotDao, carrot);
		return carrot;
	}

	public Carrot updateCarrot(String id, @AssertValid CarrotUpdate carrotUpdate)
	{
		LOGGER.info("CarrotManager, updateCarrot; id: {}, carrotUpdate: {}", id, carrotUpdate);
		Carrot carrot = getCarrot(id);
		DaoHelper.updateFromSource(carrotDao, carrotUpdate, carrot);
		return carrot;
	}

	public List<Carrot> findCarrots(String name, int page, int size)
	{
		LOGGER.debug("CarrotManager, updateCarrot; name: {}, page: {}, size: {}", name, page, size);
		CarrotDao.CarrotQueryBuild queryBuild = CarrotDao.CarrotQueryBuild.build().filterByName(name)
				.pagination(size, page);
		return carrotDao.getList(queryBuild.toParameter());
	}

	public void deleteCarrot(String id)
	{
		LOGGER.info("CarrotManager, deleteCarrot; id: {}", id);
		getCarrot(id);
		carrotDao.delete(id);
	}
}
