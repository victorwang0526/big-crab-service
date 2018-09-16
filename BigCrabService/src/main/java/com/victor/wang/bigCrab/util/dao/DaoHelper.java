/*
 * Victor Wang, Inc. All Rights Reserved.
 */

package com.victor.wang.bigCrab.util.dao;

import com.victor.wang.bigCrab.dao.GenericDao;
import com.victor.wang.bigCrab.exception.base.DatabaseOperationConflictException;
import com.victor.wang.bigCrab.model.base.AuditedMysqlEntity;
import com.victor.wang.bigCrab.model.base.BaseEntity;
import org.apache.commons.lang3.ObjectUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DaoHelper
{
	private static final Logger LOGGER = LoggerFactory.getLogger(DaoHelper.class);

	public static final String RVN_FIELD_NAME = "rvn";
	public static final String CREATED_FIELD_NAME = "createdAt";
	public static final String LAST_UPDATED_FIELD_NAME = "lastModifiedAt";
	public static final String UPDATED_FIELDS_MAP_OLD_FIELD_VALUE_KEY = "oldValue";
	public static final String UPDATED_FIELDS_MAP_NEW_FIELD_VALUE_KEY = "newValue";

	private static final Set<String> UPDATE_EXCLUDED_FIELDS = new HashSet<>(Arrays.asList(RVN_FIELD_NAME, CREATED_FIELD_NAME, LAST_UPDATED_FIELD_NAME));

	private static final Map<Class, Class> PRIMITIVE_TYPE_MAP;

	static
	{
		PRIMITIVE_TYPE_MAP = new HashMap<>(8);

		PRIMITIVE_TYPE_MAP.put(Boolean.TYPE, Boolean.class);
		PRIMITIVE_TYPE_MAP.put(Character.TYPE, Character.class);
		PRIMITIVE_TYPE_MAP.put(Byte.TYPE, Byte.class);
		PRIMITIVE_TYPE_MAP.put(Short.TYPE, Short.class);
		PRIMITIVE_TYPE_MAP.put(Integer.TYPE, Integer.class);
		PRIMITIVE_TYPE_MAP.put(Long.TYPE, Long.class);
		PRIMITIVE_TYPE_MAP.put(Float.TYPE, Float.class);
		PRIMITIVE_TYPE_MAP.put(Double.TYPE, Double.class);
	}

	/**
	 * This helper method is only a proxy to call the dao.insert method.
	 * When the object is an instance of AuditedPostgreEntity, createAt and rvn will automatically be set.
	 * You should set the value of your data identification.
	 *
	 * @param dao               the data access object.
	 * @param destinationObject the object to insert.
	 */
	public static <K, T> void insert(GenericDao<K, T> dao, T destinationObject)
	{
		if (destinationObject instanceof AuditedMysqlEntity)
		{
			AuditedMysqlEntity auditedObject = (AuditedMysqlEntity) destinationObject;
			if (auditedObject.getCreatedAt() == null)
			{//if the createdAt has been set, not override it.
				auditedObject.setCreatedAt(new Date());
			}
			auditedObject.setRvn(1);
		}
		dao.insert(destinationObject);
	}

	/**
	 * To update object with source object.
	 * If boolean field of the source object has null value, it will be ignored.
	 * Only fields of following types will be handled, other fields you should process by yourself.
	 *
	 * @param dao               the data access object
	 * @param sourceObject      the source object with update details
	 * @param destinationObject the object to be updated
	 * @param excludeFields     exclude to update these fields
	 * @return updated details.
	 * @see Integer
	 * @see Integer#TYPE
	 * @see Long
	 * @see Long#TYPE
	 * @see String
	 * @see org.joda.time.DateTime
	 * @see org.joda.time.LocalDate
	 * @see Enum
	 */
	public static <K, T> Map<String, Map<String, Object>> updateFromSource(GenericDao<K, T> dao, Object sourceObject, T destinationObject, String... excludeFields)
	{
		Map<String, Map<String, Object>> updates = updateDestinationObject(sourceObject, destinationObject, excludeFields);
		doUpdate(dao, destinationObject);
		return updates;
	}

	public static Map<String, Map<String, Object>> updateDestinationObject(Object sourceObject, Object destinationObject, String... excludeFields)
	{
		Map<String, Map<String, Object>> updatedFields = new HashMap<>();

		Map<String, Field> sourceFieldMap = getInstanceFields(sourceObject.getClass(), excludeFields);
		Map<String, Field> destinationFieldMap = getInstanceFields(destinationObject.getClass());


		for (final Field sourceField : sourceFieldMap.values())
		{
			String fieldName = sourceField.getName();
			LOGGER.debug("Attempting to copy source field {}", fieldName);

			// The fields(_id, rvn, created and updated) should be excluded, since these fields will be set automatically.
			if (destinationObject instanceof AuditedMysqlEntity && UPDATE_EXCLUDED_FIELDS.contains(fieldName))
			{
				LOGGER.debug("Skipping field {}, it is the {} field", fieldName, RVN_FIELD_NAME);
				continue;
			}

			final Field destinationField = destinationFieldMap.get(fieldName);

			if (destinationField == null)
			{
				LOGGER.debug("Could not copy field {}, there is no field on the destination with that name", fieldName);
				continue;
			}

			// ignore final
			int sourceModifiers = sourceField.getModifiers();
			int destinationModifiers = destinationField.getModifiers();

			if (Modifier.isFinal(sourceModifiers))
			{
				LOGGER.debug("Could not copy source field {}, it is final", fieldName);
				continue;
			}

			if (Modifier.isFinal(destinationModifiers))
			{
				LOGGER.debug("Could not copy to destination field {}, it is final", fieldName);
				continue;
			}

			try
			{
				updateDestinationObjectField(sourceObject, sourceField, destinationObject, destinationField, updatedFields);
			}
			catch (IllegalAccessException e)
			{
				LOGGER.debug("Could not update field {} because of illegal access exception. Error message: {}", fieldName, e.getMessage());
			}
		}

		return updatedFields;
	}

	public static <K, T> void doUpdate(GenericDao<K, T> dao, T destinationObject)
	{
		AuditedMysqlEntity auditedObject = null;
		if (destinationObject instanceof AuditedMysqlEntity)
		{
			auditedObject = (AuditedMysqlEntity) destinationObject;
			auditedObject.setLastModifiedAt(new Date());
		}
		if (dao.update(destinationObject) < 1)
		{
			throw new DatabaseOperationConflictException("Update data of {} to db failed.", destinationObject.getClass().getSimpleName());
		}

		if (auditedObject != null)
		{
			auditedObject.setRvn(auditedObject.getRvn() + 1);
		}
	}

	public static Map<String, Object> buildUpdatedValueMap(final Object oldValue, final Object newValue)
	{
		final Map<String, Object> result = new HashMap<>(2);
		if (oldValue != null)
		{
			result.put(UPDATED_FIELDS_MAP_OLD_FIELD_VALUE_KEY, oldValue);
		}
		if (newValue != null)
		{
			result.put(UPDATED_FIELDS_MAP_NEW_FIELD_VALUE_KEY, newValue);
		}
		return result;
	}

	private static void updateDestinationObjectField(final Object sourceObject, final Field sourceField,
													 final Object destinationObject, final Field destinationField,
													 final Map<String, Map<String, Object>> updatedFields)
			throws IllegalAccessException
	{
		Class<?> sourceType = sourceField.getType();
		Class<?> destinationType = destinationField.getType();

		if (!isTypeConvertible(sourceType, destinationType))
		{
			LOGGER.debug("Could not copy field {} of type {} the destination is of a different type {}",
					sourceField.getName(), sourceType, destinationType);
			return;
		}

		AccessController.doPrivileged(new PrivilegedAction<Void>()
		{
			@Override
			public Void run()
			{
				sourceField.setAccessible(true);
				destinationField.setAccessible(true);
				return null;
			}
		});

		Object sourceValue = sourceField.get(sourceObject);
		if (destinationType.isPrimitive() && sourceValue == null)
		{
			LOGGER.debug("Could not update field {} to null, because destination field is of primitive type {}.",
					destinationField.getName(), destinationField.getType());
			return;
		}

		Object destValue = destinationField.get(destinationObject);
		if (ObjectUtils.equals(sourceValue, destValue))
		{
			//value not changed.
			return;
		}

		if (sourceType.equals(Integer.TYPE) || sourceType.equals(Integer.class))
		{
			if (!BaseEntity.UNSET_INT.equals(sourceValue))
			{
				destinationField.set(destinationObject, sourceValue);
				updatedFields.put(sourceField.getName(), buildUpdatedValueMap(destValue, sourceValue));
			}
		}
		else if (sourceType.equals(Long.TYPE) || sourceType.equals(Long.class))
		{
			if (!BaseEntity.UNSET_LONG.equals(sourceValue))
			{
				destinationField.set(destinationObject, sourceValue);
				updatedFields.put(sourceField.getName(), buildUpdatedValueMap(destValue, sourceValue));
			}
		}
		else if (sourceType.equals(Boolean.TYPE) || sourceType.equals(Boolean.class))
		{
			if (sourceValue != null)
			{
				destinationField.set(destinationObject, sourceValue);
				updatedFields.put(sourceField.getName(), buildUpdatedValueMap(destValue, sourceValue));
			}
		}
		else if (sourceType.equals(String.class))
		{
			if (!BaseEntity.UNSET_STRING.equals(sourceValue))
			{
				destinationField.set(destinationObject, sourceValue);
				updatedFields.put(sourceField.getName(), buildUpdatedValueMap(destValue, sourceValue));
			}
		}
		else if (sourceType.equals(DateTime.class))
		{
			if (!BaseEntity.UNSET_DATETIME.equals(sourceValue))
			{
				destinationField.set(destinationObject, sourceValue);
				updatedFields.put(sourceField.getName(), buildUpdatedValueMap(destValue, sourceValue));
			}
		}
		else if (sourceType.equals(LocalDate.class))
		{
			if (!BaseEntity.UNSET_LOCALDATE.equals(sourceValue))
			{
				destinationField.set(destinationObject, sourceValue);
				updatedFields.put(sourceField.getName(), buildUpdatedValueMap(destValue, sourceValue));
			}
		}
		else if (sourceType.isEnum())
		{
			destinationField.set(destinationObject, sourceValue);
			updatedFields.put(sourceField.getName(), buildUpdatedValueMap(destValue, sourceValue));
		}
		else
		{
			LOGGER.debug("Skipping field {} it is not of a handled type {}", sourceField.getName(), sourceType);
		}
	}

	private static Map<String, Field> getInstanceFields(Class<?> clazz)
	{
		Map<String, Field> name_field = new HashMap<>();
		while (clazz != null)
		{
			Field[] declaredFields = clazz.getDeclaredFields();
			if (declaredFields != null)
			{
				for (Field field : declaredFields)
				{
					int fieldModifiers = field.getModifiers();
					if (!Modifier.isStatic(fieldModifiers))
					{
						if (!name_field.containsKey(field.getName()))
						{
							name_field.put(field.getName(), field);
						}
						else
						{
							Field overrideField = name_field.get(field.getName());
							LOGGER.debug("Field {} of class {} is overridden by sub class {}.",
									field.getName(), field.getDeclaringClass().getSimpleName(),
									overrideField.getDeclaringClass().getSimpleName());
						}
					}
				}
			}
			clazz = clazz.getSuperclass();
		}
		return name_field;
	}

	private static Map<String, Field> getInstanceFields(Class<?> clazz, String... excludeFields)
	{
		Map<String, Field> fields = getInstanceFields(clazz);
		if (excludeFields != null)
		{
			for (String excluded : excludeFields)
			{
				if (fields.remove(excluded) != null)
				{
					LOGGER.debug("Field has been excluded by excludeFields list {}", excluded);
				}
			}
		}
		return fields;
	}

	private static boolean isTypeConvertible(Class<?> sourceType, Class<?> destinationType)
	{
		if (destinationType.isAssignableFrom(sourceType))
		{
			return true;
		}
		else
		{
			if (sourceType.isPrimitive() && destinationType.equals(PRIMITIVE_TYPE_MAP.get(sourceType)))
			{
				return true;
			}
			else if (destinationType.isPrimitive() && sourceType.equals(PRIMITIVE_TYPE_MAP.get(destinationType)))
			{
				return true;
			}
		}
		return false;
	}
}
