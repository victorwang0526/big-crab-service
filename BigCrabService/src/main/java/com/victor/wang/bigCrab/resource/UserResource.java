package com.victor.wang.bigCrab.resource;

import com.victor.wang.bigCrab.manager.UserManager;
import com.victor.wang.bigCrab.sharedObject.UserToken;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.util.Iterator;

/**
 * The user resource
 *
 * @author victor.wang
 * @version $Id$
 */
@Component
@Path("/api/users")
public class UserResource
{

	@Autowired
	private UserManager userManager;

	@Autowired
	private MapperFacade mapper;

	//[{"key":"Authorization","value":"Basic YWRtaW46YWRtaW4xMjM=","description":"","enabled":true}]
	/**
	 * <h3>Login</h3>.
	 * <p>Login</p>
	 * <p>
	 * <br/>
	 * <p>Authorization: Basic (base64 encoded 'username:password')</p>
	 * <p>
	 * <br/>
	 * <p>error:</p>
	 * <p>400, username_not_found, 用户名不存在</p><br/>
	 * <p>400, password_incorrect, 密码错误</p>
	 * <p>400, user_invalid, 无效的用户</p>
	 *
	 * @param httpHeader the create object
	 */
	@POST
	@Path("login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UserToken loginUser(@Context HttpHeaders httpHeader) {
		MultivaluedMap<String, String> headers = httpHeader.getRequestHeaders();
		Iterator<String> iterator = headers.keySet().iterator();
		String authorization = null;
		while (iterator.hasNext()) {
			String headName = iterator.next();
			System.out.println(headName + ":" + headers.get(headName) + "[\\r\\n]");

			if (headName.toLowerCase().equals("authorization")) {
				if (headers.get(headName) != null && headers.get(headName).size() > 0) {
					authorization = headers.get(headName).get(0);
				}
			}
		}
		return userManager.loginUser(authorization);
	}

//	/**
//	 * <h3>Description</h3>.
//	 * <p>Get a user</p>
//	 *
//	 * @param id The user id
//	 */
//	@GET
//	@Path("{id}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public UserInfo getUser(@PathParam("id") String id)
//	{
//		return mapper.map(userManager.getUser(id), UserInfo.class);
//	}

//	/**
//	 * <h3>Description</h3>.
//	 * <p>Create a user</p>
//	 *
//	 * @param userCreate the create object
//	 */
//	@POST
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public UserInfo createUser(UserCreate userCreate)
//	{
//		return mapper.map(userManager.createUser(userCreate), UserInfo.class);
//	}

//	/**
//	 * <h3>Description</h3>.
//	 * <p>Update a user</p>
//	 *
//	 * @param id           the user id
//	 * @param userUpdate the update object
//	 */
//	@PUT
//	@Path("{id}")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public UserInfo updateUser(@PathParam("id") String id, UserUpdate userUpdate)
//	{
//		return mapper.map(userManager.updateUser(id, userUpdate), UserInfo.class);
//	}

//	/**
//	 * <h3>Description</h3>.
//	 * <p>Delete a user</p>
//	 *
//	 * @param id the user id
//	 */
//	@DELETE
//	@Path("{id}")
//	public void deleteUser(@PathParam("id") String id)
//	{
//		userManager.deleteUser(id);
//	}

//	/**
//	 * <h3>Description</h3>.
//	 * <p>Search users, with paginated results.</p>
//	 *
//	 * @param page the page
//	 * @param size the page size
//	 */
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	public PaginatedAPIResult<UserInfo> findUsers(
//													  @QueryParam("page") @DefaultValue("1") int page,
//													  @QueryParam("size") @DefaultValue("10") int size)
//	{
//		return new PaginatedAPIResult<>(
//				mapper.mapAsList(userManager.findUsers(page, size), UserInfo.class),
//				page,
//				size,
//				userManager.countUsers());
//	}
}
