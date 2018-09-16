
package com.victor.wang.bigCrab.util.exception;


import com.victor.wang.bigCrab.exception.base.ApplicationException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.status;

/**
 * <p>A JAX-RS exception mapper that maps an {@link ApplicationException} into an {@link ErrorEntity} which will be
 * output using JSON, e.g.:</p>
 * <pre>
 *  {
 *      "errorCode"         : "errors.com.victor.wang.bigCrab.not_found",
 *      "errorMessage"      : "The resource not found",
 *      "messageVars"       : [],
 *      "numericErrorCode"  : 1004,
 *      "originatingService": "my.service"
 *  }
 * </pre>
 * <p>It will also expose numeric and alpha error codes as headers:</p><ul>
 * <li><code>Error-Code</code> - numeric error code, contains value of "numericErrorCode" field,</li>
 * <li><code>Error-Name</code> - alpha error code, contains value of "errorCode" field</li>
 * </ul>
 * <p>Besides that if an application is caught by this mapper, it will also be placed into the ContainerRequestContext
 * under the property <code>APPLICATION_EXCEPTION_MAPPER_EXCEPTION</code> so that filters can access it.</p>
 */
@Provider
public class ApplicationExceptionMapper
		implements ExceptionMapper<ApplicationException>
{
	public static final String REQUEST_CONTEXT_APPLICATION_EXCEPTION_PROPERTY_NAME =
			"APPLICATION_EXCEPTION_MAPPER_EXCEPTION";

	// this is a hack around the fact that ContainerRequestContext is not a proxy object
	// and cannot be injected
	@Context
	protected javax.inject.Provider<ContainerRequestContext> containerRequestContextProvider;

	public ApplicationExceptionMapper()
	{

	}

	@Override
	public Response toResponse(ApplicationException exception)
	{
		/**
		 * Storing exception in request context
		 */
		if (containerRequestContextProvider != null)
		{
			ContainerRequestContext requestContext = containerRequestContextProvider.get();
			requestContext.setProperty(REQUEST_CONTEXT_APPLICATION_EXCEPTION_PROPERTY_NAME, exception);
		}

		/**
		 * Converting exception into an error entity and building response
		 */
		return ExceptionMapperUtils.withErrorEntity(status(exception.getStatusCode()), new ErrorEntity(exception)).build();
	}

}