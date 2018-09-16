/*
 * Victor Wang. All Rights Reserved.
 */
package com.victor.wang.bigCrab.util.mapper;

import ma.glasnost.orika.Converter;
import ma.glasnost.orika.Mapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Orika mapper facade factory bean to create instances of Orika mappers. Auto-discovers and registers with facade
 * factory all instances of {@link ma.glasnost.orika.Converter}, {@link ma.glasnost.orika.Mapper} available in spring
 * context. Also discovers all instances of {@link MappingConfigurer} and runs
 * them against facade factory.
 */
@Component("orikaMapperFacade")
public class MapperFacadeFactoryBean
		extends AbstractFactoryBean<MapperFacade>
		implements ApplicationContextAware
{
	public static final Logger LOGGER = LoggerFactory.getLogger(MapperFacadeFactoryBean.class);

	/**
	 * Current Spring application context.
	 */
	private ApplicationContext applicationContext;

	public MapperFacadeFactoryBean()
	{
		super();
	}

	@Override
	public void setApplicationContext(ApplicationContext appContext)
			throws BeansException
	{
		this.applicationContext = appContext;
	}

	/**
	 * Returns mapper factory configured with converters, mappers and configurers available in spring context.
	 *
	 * @return configured mapper factory.
	 */
	@SuppressWarnings("unchecked")
	private MapperFactory getConfiguredFactory()
	{
		final MapperFactory factory = new DefaultMapperFactory.Builder().build();

		/**
		 * Registering converters.
		 */
		for (Map.Entry<String, Converter> entry : this.applicationContext.getBeansOfType(Converter.class).entrySet())
		{
			registerConverter(entry.getKey(), entry.getValue(), factory);
		}

		/**
		 * Registering mappers
		 */
		for (Map.Entry<String, Mapper> entry : this.applicationContext.getBeansOfType(Mapper.class).entrySet())
		{
			registerMapper(entry.getKey(), entry.getValue(), factory);
		}

		/**
		 * Passing factory to configurators to apply customizations.
		 */
		for (Map.Entry<String, MappingConfigurer> entry : this.applicationContext.getBeansOfType(MappingConfigurer.class).entrySet())
		{
			applyConfigurer(entry.getKey(), entry.getValue(), factory);
		}

		return factory;
	}

	/**
	 * Registers converter with factory.
	 *
	 * @param <S>       converter source type.
	 * @param <D>       converter destination type.
	 * @param name      converter bean name.
	 * @param converter converter to register.
	 * @param factory   factory to configure.
	 */
	private <S, D> void registerConverter(String name, Converter<S, D> converter, final MapperFactory factory)
	{
		LOGGER.debug("Registering converter [{}]: {}", name, converter);

		factory.getConverterFactory().registerConverter(converter);
	}

	/**
	 * Registers mapper with factory.
	 *
	 * @param <A>     first mapper supported type.
	 * @param <B>     second mapper supported type.
	 * @param name    mapper bean name.
	 * @param mapper  mapper to register.
	 * @param factory factory to configure.
	 */
	private <A, B> void registerMapper(String name, Mapper<A, B> mapper, final MapperFactory factory)
	{
		LOGGER.debug("Registering mapper [{}]: {}", name, mapper);

		factory.classMap(mapper.getAType(), mapper.getBType()).byDefault().customize(mapper).register();
	}

	/**
	 * Applies passed configurer to factory.
	 *
	 * @param name       configurer bean name.
	 * @param configurer configurer to apply.
	 * @param factory    factory to configure.
	 */
	private void applyConfigurer(String name, MappingConfigurer configurer, MapperFactory factory)
	{
		LOGGER.debug("Applying configurer [{}]: {}", name, configurer);

		configurer.configure(factory);
	}

	@Override
	public Class<?> getObjectType()
	{
		return MapperFacade.class;
	}

	@Override
	protected MapperFacade createInstance()
	throws Exception
	{
		return getConfiguredFactory().getMapperFacade();
	}
}
