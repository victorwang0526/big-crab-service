package com.victor.wang.bigCrab.resource;

import com.victor.wang.bigCrab.manager.CardManager;
import com.victor.wang.bigCrab.manager.DeliverManager;
import com.victor.wang.bigCrab.model.Card;
import com.victor.wang.bigCrab.model.Deliver;
import com.victor.wang.bigCrab.sharedObject.CardInfo;
import com.victor.wang.bigCrab.sharedObject.CardRedeemRequest;
import com.victor.wang.bigCrab.sharedObject.CardRequest;
import com.victor.wang.bigCrab.sharedObject.CardValidateRequest;
import com.victor.wang.bigCrab.sharedObject.DeliverInfo;
import com.victor.wang.bigCrab.sharedObject.SfOrderSearchResponse;
import com.victor.wang.bigCrab.sharedObject.SfPrintInfo;
import com.victor.wang.bigCrab.sharedObject.lov.CardStatus;
import com.victor.wang.bigCrab.sharedObject.PaginatedAPIResult;
import com.victor.wang.bigCrab.util.dao.UniqueString;
import jersey.repackaged.com.google.common.collect.Lists;
import ma.glasnost.orika.MapperFacade;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
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
	private DeliverManager deliverManager;

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
						card.setCardNumber(r.getCell(2).getStringCellValue().replace("NO.", ""));
						card.setPassword(r.getCell(3).getStringCellValue());
						card.setCardType(r.getCell(4).getStringCellValue());
						card.setDescription(r.getCell(5).getStringCellValue());
						card.setStatus(CardStatus.UNUSED);
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
	 * <p>Get a card card number</p>
	 *
	 * @param cardNumber The card number
	 */
	@GET
	@Path("{cardNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public CardInfo getCard(@PathParam("cardNumber") String cardNumber)
	{
		return mapper.map(cardManager.getCard(cardNumber), CardInfo.class);
	}

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
			@QueryParam("cardNumber") String cardNumber,
			@QueryParam("status") String status,
			@QueryParam("page") @DefaultValue("1") int page,
			@QueryParam("size") @DefaultValue("10") int size)
	{
		PaginatedAPIResult<CardInfo> result = new PaginatedAPIResult<CardInfo>(
				mapper.mapAsList(cardManager.findCards(cardNumber, status, page, size), CardInfo.class),
				page,
				size,
				cardManager.countCards(cardNumber, status));

		for (CardInfo card : result.getElements())
		{
			card.setDeliverInfo(mapper.map(deliverManager.getDeliverByCardNumber(card.getCardNumber()), DeliverInfo.class));
		}
		return result;
	}

	/**
	 * <h3>Description</h3>.
	 * <p>标记为电话预约</p>
	 *
	 * @param request the card number
	 */
	@PUT
	@Path("phone")
	@Produces(MediaType.APPLICATION_JSON)
	public void makePhone(CardRequest request)
	{
		cardManager.markPhone(request);
	}

	/**
	 * <h3>Description</h3>.
	 * <p>解除冻结</p>
	 *
	 * @param request the card number
	 */
	@PUT
	@Path("unfrozen")
	@Produces(MediaType.APPLICATION_JSON)
	public void unfrozen(CardRequest request)
	{
		cardManager.unfrozen(request);
	}

	/**
	 * <h3>Description</h3>.
	 * <p>冻结</p>
	 *
	 * @param request the card number
	 */
	@PUT
	@Path("frozen")
	@Produces(MediaType.APPLICATION_JSON)
	public void frozen(CardRequest request)
	{
		cardManager.frozen(request);
	}

	/**
	 * <h3>Description</h3>.
	 * <p>校验卡</p>
	 *
	 * @param cardNumber the card number
	 */
	@PUT
	@Path("{cardNumber}/validate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CardInfo validate(@PathParam("cardNumber") String cardNumber,
							 CardValidateRequest validateRequest)
	{
		return mapper.map(cardManager.validate(cardNumber, validateRequest), CardInfo.class);
	}


	/**
	 * <h3>Description</h3>.
	 * <p>填写地址</p>
	 *
	 * @param cardNumber the card number
	 */
	@PUT
	@Path("{cardNumber}/redeem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public DeliverInfo redeem(@PathParam("cardNumber") String cardNumber,
							  CardRedeemRequest redeemRequest)
	{
		return mapper.map(cardManager.redeem(cardNumber, redeemRequest), DeliverInfo.class);
	}


	/**
	 * <h3>Description</h3>.
	 * <p>顺丰下单</p>
	 *
	 * @param request the card number
	 */
	@POST
	@Path("sfOrder")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void sfOrder(CardRequest request)
	{
		deliverManager.sfOrder(request);
	}

	/**
	 * <h3>Description</h3>.
	 * <p>顺丰下单</p>
	 *
	 * @param cardNumber the card number
	 */
	@POST
	@Path("{cardNumber}/sfOrder/{mailno}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void sfOrderSelf(@PathParam("cardNumber") String cardNumber,
							@PathParam("mailno") String mailno)
	{
		deliverManager.sfOrderSelf(cardNumber, mailno);
	}

	/**
	 * <h3>Description</h3>.
	 * <p>顺丰打印</p>
	 *
	 * @param cardNumber the card number
	 */
	@POST
	@Path("{cardNumber}/sfOrder/{mailno}/print")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SfPrintInfo sfOrderPrint(@PathParam("cardNumber") String cardNumber,
									@PathParam("mailno") String mailno)
	{
		return deliverManager.getPrint(cardNumber, mailno);
	}

	/**
	 * <h3>Description</h3>.
	 * <p>顺丰，查询订单状态</p>
	 *
	 * @param cardNumber the card number
	 */
	@GET
	@Path("{cardNumber}/sfOrder")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SfOrderSearchResponse sfGetOrder(@PathParam("cardNumber") String cardNumber)
	{
		return deliverManager.sfGetOrder(cardNumber);
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

//	/**
//	 * <h3>Description</h3>.
//	 * <p>Update a card</p>
//	 *
//	 * @param id         the card id
//	 * @param cardUpdate the update object
//	 */
//	@PUT
//	@Path("{id}")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public CardInfo updateCard(@PathParam("id") String id, CardUpdate cardUpdate)
//	{
//		return mapper.map(cardManager.updateCard(id, cardUpdate), CardInfo.class);
//	}

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


}
