/*
 * Victor Wang. All Rights Reserved.
 */
package com.victor.wang.bigCrab.util.mapper;

import ma.glasnost.orika.MapperFactory;

/**
 * Interface to be implemented by spring beans, that will be called to apply
 * customizations to mapper factory during configuration.
 */
public interface MappingConfigurer
{
	void configure(MapperFactory factory);
}
