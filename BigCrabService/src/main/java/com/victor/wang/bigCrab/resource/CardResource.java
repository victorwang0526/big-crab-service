package com.victor.wang.bigCrab.resource;

import com.victor.wang.bigCrab.manager.CardManager;
import com.victor.wang.bigCrab.model.Card;
import com.victor.wang.bigCrab.sharedObject.CardCreate;
import com.victor.wang.bigCrab.sharedObject.CardInfo;
import com.victor.wang.bigCrab.sharedObject.CardStatus;
import com.victor.wang.bigCrab.sharedObject.CardUpdate;
import com.victor.wang.bigCrab.sharedObject.PaginatedAPIResult;
import com.victor.wang.bigCrab.util.dao.UniqueString;
import jersey.repackaged.com.google.common.collect.Lists;
import ma.glasnost.orika.MapperFacade;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * The card resource
 *
 * @author victor.wang
 * @version $Id$
 */
@Component
@Path("/api/cards")
public class CardResource
{

	@Autowired
	private CardManager cardManager;

	@Autowired
	private MapperFacade mapper;

	/**
	 * <h3>导入</h3>.
	 * <p>Get a card</p>
	 */
	@POST
	@Path("import")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public void importCards(FormDataMultiPart form)

	{
		List<FormDataBodyPart> fileBodyParts = form.getFields("file");
		if (fileBodyParts != null && fileBodyParts.size() > 0)
		{
			for (FormDataBodyPart body : fileBodyParts)
			{
				InputStream inputStream = body.getValueAs(InputStream.class);
				try
				{
					Workbook wb0 = new XSSFWorkbook(inputStream);
					Sheet sheet = wb0.getSheetAt(0);
					List<Card> cards = Lists.newArrayList();
					for (Row r : sheet)
					{
						if (r.getRowNum() < 1)
						{
							continue;
						}
						Card card = new Card();
						card.setId(UniqueString.uuidUniqueString());
						card.setCardNumber(r.getCell(0).getStringCellValue());
						card.setPassword(r.getCell(1).getStringCellValue());
						card.setCardType(r.getCell(2).getStringCellValue());
						card.setStatus(CardStatus.UNUSED.name());
						cards.add(card);
					}
					cardManager.createCards(cards);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}


	/**
	 * <h3>Description</h3>.
	 * <p>Get a card</p>
	 *
	 * @param id The card id
	 */
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public CardInfo getCard(@PathParam("id") String id)
	{
		return mapper.map(cardManager.getCard(id), CardInfo.class);
	}

//	/**
//	 * <h3>Description</h3>.
//	 * <p>Create a card</p>
//	 *
//	 * @param cardCreate the create object
//	 */
//	@POST
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public CardInfo createCard(CardCreate cardCreate)
//	{
//		return mapper.map(cardManager.createCard(cardCreate), CardInfo.class);
//	}

	/**
	 * <h3>Description</h3>.
	 * <p>Update a card</p>
	 *
	 * @param id         the card id
	 * @param cardUpdate the update object
	 */
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CardInfo updateCard(@PathParam("id") String id, CardUpdate cardUpdate)
	{
		return mapper.map(cardManager.updateCard(id, cardUpdate), CardInfo.class);
	}

//	/**
//	 * <h3>Description</h3>.
//	 * <p>Delete a card</p>
//	 *
//	 * @param id the card id
//	 */
//	@DELETE
//	@Path("{id}")
//	public void deleteCard(@PathParam("id") String id)
//	{
//		cardManager.deleteCard(id);
//	}

	/**
	 * <h3>Description</h3>.
	 * <p>Search cards, with paginated results.</p>
	 *
	 * @param page the page
	 * @param size the page size
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public PaginatedAPIResult<CardInfo> findCards(
			@QueryParam("page") @DefaultValue("1") int page,
			@QueryParam("size") @DefaultValue("10") int size)
	{
		return new PaginatedAPIResult<>(
				mapper.mapAsList(cardManager.findCards(page, size), CardInfo.class),
				page,
				size,
				cardManager.countCards());
	}
}
