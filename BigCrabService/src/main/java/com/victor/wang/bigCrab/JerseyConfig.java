package com.victor.wang.bigCrab;

import com.victor.wang.bigCrab.filter.AuthorizationInterceptor;
import com.victor.wang.bigCrab.filter.CORSResponseFilter;
import com.victor.wang.bigCrab.resource.CardResource;
import com.victor.wang.bigCrab.util.exception.ApplicationExceptionMapper;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.wadl.internal.WadlResource;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class JerseyConfig
		extends ResourceConfig
{

	@Value("${spring.jersey.application-path:/}")
	private String apiPath;

	@PostConstruct
	public void init()
	{
		// Register components where DI is needed
	}

	public JerseyConfig()
	{

		register(CORSResponseFilter.class);
		register(AuthorizationInterceptor.class);

		this.registerEndpoints();

		//register exception
		register(ApplicationExceptionMapper.class);
		register(MultiPartFeature.class);

		property(ServletProperties.FILTER_FORWARD_ON_404, true);

	}

	private void registerEndpoints()
	{
		//register resource
		packages("com.victor.wang.bigCrab.resource");
//		register(CardResource.class);
		// Available at /<Jersey's servlet path>/application.wadl
		this.register(WadlResource.class);
	}
}
