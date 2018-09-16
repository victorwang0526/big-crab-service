package com.victor.wang.bigCrab.dao;

import java.util.List;
import java.util.Map;

/**
 * The generic dao for postgresql dao
 *
 * @param <K> the primary key type
 * @param <T> the entity type
 */
public interface GenericDao<K, T>
{
	int insert(T entity);

	T get(K id);

	int update(T entity);

	int delete(K id);

	List<T> getAll();

	List<T> getList(Map<String, Object> condition);

	int getCount(Map<String, Object> condition);

	void purge();
}
