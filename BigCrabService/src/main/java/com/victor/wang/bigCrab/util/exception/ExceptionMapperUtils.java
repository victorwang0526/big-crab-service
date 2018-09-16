/*
 * Victor Wang. All Rights Reserved.
 */

package com.victor.wang.bigCrab.util.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;

/**
 * <p>Utility class providing methods and constants to build aware {@link javax.ws.rs.ext.ExceptionMapper} implementations that
 * pass back a JSON-encoded error entity and decorate response with <code>Error-*</code> custom headers:</p><ul>
 *     <li><code>Error-Code</code> - to error entity {@link ErrorEntity#getNumericErrorCode() numeric error code},</li>
 *     <li><code>Error-Name</code> - to error entity {@link ErrorEntity#getErrorCode() alpha error code}.</li>
 * </ul>
 * <p>These headers then logged into application http access logs and used to distinguish between various HTTP errors
 * having same HTTP error status code.</p>
 */
public final class ExceptionMapperUtils
{
	/**
	 * Custom header name to hold numeric error code - maps to error entity "numericErrorCode" field
	 */
	public static final String ERROR_CODE_HEADER = "Error-Code";

	/**
	 * Custom header name to hold error name - maps to error entity "errorCode" field
	 */
	public static final String ERROR_NAME_HEADER = "Error-Name";

	/**
	 * Hiding util-class constructor.
	 */
	private ExceptionMapperUtils()
	{
	}

	/**
	 * <p>Applies error entity to response builder, namely:</p><ul>
	 *     <li>sets response headers:<ul>
	 *         <li><code>Content-Type: application/json; charset=UTF-8</code>,</li>
	 *         <li><code>Error-Code: &lt;value of {@link ErrorEntity#getNumericErrorCode()}&gt;</code>,</li>
	 *         <li><code>Error-Name: &lt;value of {@link ErrorEntity#getErrorCode()}&gt;</code>.</li>
	 *     </ul></li>
	 *     <li>sets response entity.</li>
	 * </ul>
	 * <p>Doesn't check if passed builder has HTTP response status code set. Implementations are expected to pass a
	 * builder with error HTTP status code set.</p>
	 * <p>This is a utility method to be used by {@link javax.ws.rs.ext.ExceptionMapper} implementations.</p>
	 *
	 * @param builder    response builder. Must not be <code>null</code>
	 * @param entity     error entity
	 * @param <E>        error entity type
	 * @return passed {@link javax.ws.rs.core.Response.ResponseBuilder response builder} with applied error entity.
	 */
	public static <E extends ErrorEntity> Response.ResponseBuilder withErrorEntity(Response.ResponseBuilder builder, E entity)
	{
		return builder
				.header(ERROR_CODE_HEADER, entity.getNumericErrorCode())
				.header(ERROR_NAME_HEADER, entity.getErrorCode())
				.type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name()))
				.entity(entity);
	}
}
