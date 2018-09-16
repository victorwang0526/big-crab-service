package com.victor.wang.bigCrab;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Repository;

@SpringBootApplication
@ComponentScan(basePackages = {"com.victor.wang.bigCrab.manager",
		"com.victor.wang.bigCrab.util",
		"com.victor.wang.bigCrab.resource",
		"com.victor.wang.bigCrab",
		"net.sf.oval.integration.spring"})
@MapperScan(value = "com.victor.wang.bigCrab.dao", annotationClass = Repository.class)
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class Application
		extends SpringBootServletInitializer
{
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder)
	{
		return builder.sources(Application.class);
	}

	public static void main(String[] args)
	{
		SpringApplication.run(Application.class, args);
	}
}
