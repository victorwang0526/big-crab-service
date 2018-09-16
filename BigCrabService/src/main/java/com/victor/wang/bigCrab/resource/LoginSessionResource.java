package com.victor.wang.bigCrab.resource;

import com.victor.wang.bigCrab.manager.LoginSessionManager;
import com.victor.wang.bigCrab.sharedObject.LoginSessionCreate;
import com.victor.wang.bigCrab.sharedObject.LoginSessionInfo;
import com.victor.wang.bigCrab.sharedObject.LoginSessionUpdate;
import com.victor.wang.bigCrab.sharedObject.PaginatedAPIResult;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * The loginSession resource
 *
 * @author victor.wang
 * @version $Id$
 */
@Component
@Path("/api/loginSessions")
public class LoginSessionResource
{

	@Autowired
	private LoginSessionManager loginSessionManager;

	@Autowired
	private MapperFacade mapper;

	/**
	 * <h3>Description</h3>.
	 * <p>Get a loginSession</p>
	 *
	 * @param id The loginSession id
	 */
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public LoginSessionInfo getLoginSession(@PathParam("id") String id)
	{
		return mapper.map(loginSessionManager.getLoginSession(id), LoginSessionInfo.class);
	}

	/**
	 * <h3>Description</h3>.
	 * <p>Create a loginSession</p>
	 *
	 * @param loginSessionCreate the create object
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public LoginSessionInfo createLoginSession(LoginSessionCreate loginSessionCreate)
	{
		return mapper.map(loginSessionManager.createLoginSession(loginSessionCreate), LoginSessionInfo.class);
	}

	/**
	 * <h3>Description</h3>.
	 * <p>Update a loginSession</p>
	 *
	 * @param id           the loginSession id
	 * @param loginSessionUpdate the update object
	 */
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public LoginSessionInfo updateLoginSession(@PathParam("id") String id, LoginSessionUpdate loginSessionUpdate)
	{
		return mapper.map(loginSessionManager.updateLoginSession(id, loginSessionUpdate), LoginSessionInfo.class);
	}

	/**
	 * <h3>Description</h3>.
	 * <p>Delete a loginSession</p>
	 *
	 * @param id the loginSession id
	 */
	@DELETE
	@Path("{id}")
	public void deleteLoginSession(@PathParam("id") String id)
	{
		loginSessionManager.deleteLoginSession(id);
	}

	/**
	 * <h3>Description</h3>.
	 * <p>Search loginSessions, with paginated results.</p>
	 *
	 * @param page the page
	 * @param size the page size
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public PaginatedAPIResult<LoginSessionInfo> findLoginSessions(
													  @QueryParam("page") @DefaultValue("1") int page,
													  @QueryParam("size") @DefaultValue("10") int size)
	{
		return new PaginatedAPIResult<>(
				mapper.mapAsList(loginSessionManager.findLoginSessions(page, size), LoginSessionInfo.class),
				page,
				size,
				loginSessionManager.countLoginSessions());
	}
}
