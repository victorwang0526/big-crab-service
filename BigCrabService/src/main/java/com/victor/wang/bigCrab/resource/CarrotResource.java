package com.victor.wang.bigCrab.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.victor.wang.bigCrab.manager.CarrotManager;
import com.victor.wang.bigCrab.model.Carrot;
import com.victor.wang.bigCrab.sharedObject.CarrotCreate;
import com.victor.wang.bigCrab.sharedObject.CarrotInfo;
import com.victor.wang.bigCrab.sharedObject.CarrotUpdate;
import com.victor.wang.bigCrab.sharedObject.PaginatedAPIResult;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
 * The carrot resource
 *
 * @author victor.wang
 * @version $Id$
 */
@Component
@Path("/carrots")
public class CarrotResource
{

	@Autowired
	private CarrotManager carrotManager;

	@Autowired
	private MapperFacade mapper;

	/**
	 * <h3>Description</h3>.
	 * <p>Get a carrot</p>
	 *
	 * @param id The carrot id
	 */
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public CarrotInfo getCarrot(@PathParam("id") String id)
	{
		return mapper.map(carrotManager.getCarrot(id), CarrotInfo.class);
	}

	/**
	 * <h3>Description</h3>.
	 * <p>Create a carrot</p>
	 *
	 * @param carrotCreate the create object
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CarrotInfo createCarrot(CarrotCreate carrotCreate)
	{
		return mapper.map(carrotManager.createCarrot(carrotCreate), CarrotInfo.class);
	}

	/**
	 * <h3>Description</h3>.
	 * <p>Update a carrot</p>
	 *
	 * @param id           the carrot id
	 * @param carrotUpdate the update object
	 */
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CarrotInfo updateCarrot(@PathParam("id") String id, CarrotUpdate carrotUpdate)
	{
		return mapper.map(carrotManager.updateCarrot(id, carrotUpdate), CarrotInfo.class);
	}

	/**
	 * <h3>Description</h3>.
	 * <p>Delete a carrot</p>
	 *
	 * @param id the carrot id
	 */
	@DELETE
	@Path("{id}")
	public void deleteCarrot(@PathParam("id") String id)
	{
		carrotManager.deleteCarrot(id);
	}

	/**
	 * <h3>Description</h3>.
	 * <p>Search carrots, with paginated results.</p>
	 *
	 * @param name the carrot name
	 * @param page the page
	 * @param size the page size
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public PaginatedAPIResult<CarrotInfo> findCarrots(@QueryParam("name") String name,
													  @QueryParam("page") @DefaultValue("1") int page,
													  @QueryParam("size") @DefaultValue("10") int size)
	{
		return new PaginatedAPIResult<>(mapper.mapAsList(carrotManager.findCarrots(name, page, size), CarrotInfo.class), page * size, size);
	}
}
