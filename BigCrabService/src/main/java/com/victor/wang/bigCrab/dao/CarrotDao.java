package com.victor.wang.bigCrab.dao;

import com.victor.wang.bigCrab.model.Carrot;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarrotDao
		extends GenericDao<String, Carrot>
{

	/**
	 * search carrots
	 *
	 */
	List<Carrot> searchCarrots(@Param("name") String name,
							   @Param("offset") int start,
							   @Param("limit") int count);

	class CarrotQueryBuild
			extends QueryBuilder
	{

		public static CarrotQueryBuild build()
		{
			return new CarrotQueryBuild();
		}
		public CarrotQueryBuild filterByName(String name)
		{
			return add("name", name);
		}
	}


}
