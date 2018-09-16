package com.victor.wang.bigCrab;

import com.victor.wang.bigCrab.util.exception.oval.BaseEntityValidatorInterceptor;
import org.aopalliance.aop.Advice;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig
{
	//创建Advice或Advisor
	@Bean
	public Advice ovalGuardInterceptor()
	{
		return new BaseEntityValidatorInterceptor();
	}

	@Bean
	public BeanNameAutoProxyCreator beanNameAutoProxyCreator()
	{
		BeanNameAutoProxyCreator beanNameAutoProxyCreator = new BeanNameAutoProxyCreator();
		beanNameAutoProxyCreator.setBeanNames("carrotManager", "userManager", "cardManager");
		beanNameAutoProxyCreator.setProxyTargetClass(true);
		beanNameAutoProxyCreator.setInterceptorNames("ovalGuardInterceptor");
		return beanNameAutoProxyCreator;
	}
}
