package com.victor.wang.bigCrab.resource;

import com.victor.wang.bigCrab.manager.DeliverManager;
import com.victor.wang.bigCrab.sharedObject.DeliverCreate;
import com.victor.wang.bigCrab.sharedObject.DeliverInfo;
import com.victor.wang.bigCrab.sharedObject.DeliverUpdate;
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
 * The deliver resource
 *
 * @author victor.wang
 * @version $Id$
 */
@Component
@Path("/api/delivers")
public class DeliverResource
{

	@Autowired
	private DeliverManager deliverManager;

	@Autowired
	private MapperFacade mapper;

	/**
	 * <h3>Description</h3>.
	 * <p>Get a deliver</p>
	 *
	 * @param id The deliver id
	 */
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public DeliverInfo getDeliver(@PathParam("id") String id)
	{
		return mapper.map(deliverManager.getDeliver(id), DeliverInfo.class);
	}

	/**
	 * <h3>Description</h3>.
	 * <p>Create a deliver</p>
	 *
	 * @param deliverCreate the create object
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public DeliverInfo createDeliver(DeliverCreate deliverCreate)
	{
		return mapper.map(deliverManager.createDeliver(deliverCreate), DeliverInfo.class);
	}

	/**
	 * <h3>Description</h3>.
	 * <p>Update a deliver</p>
	 *
	 * @param id           the deliver id
	 * @param deliverUpdate the update object
	 */
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public DeliverInfo updateDeliver(@PathParam("id") String id, DeliverUpdate deliverUpdate)
	{
		return mapper.map(deliverManager.updateDeliver(id, deliverUpdate), DeliverInfo.class);
	}

	/**
	 * <h3>Description</h3>.
	 * <p>Delete a deliver</p>
	 *
	 * @param id the deliver id
	 */
	@DELETE
	@Path("{id}")
	public void deleteDeliver(@PathParam("id") String id)
	{
		deliverManager.deleteDeliver(id);
	}

	/**
	 * <h3>Description</h3>.
	 * <p>Search delivers, with paginated results.</p>
	 *
	 * @param page the page
	 * @param size the page size
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public PaginatedAPIResult<DeliverInfo> findDelivers(
													  @QueryParam("page") @DefaultValue("1") int page,
													  @QueryParam("size") @DefaultValue("10") int size)
	{
		return new PaginatedAPIResult<>(
				mapper.mapAsList(deliverManager.findDelivers(page, size), DeliverInfo.class),
				page,
				size,
				deliverManager.countDelivers());
	}
}
