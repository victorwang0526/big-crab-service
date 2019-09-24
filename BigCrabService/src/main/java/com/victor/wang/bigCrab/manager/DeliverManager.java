package com.victor.wang.bigCrab.manager;

import com.victor.wang.bigCrab.dao.DeliverDao;
import com.victor.wang.bigCrab.exception.DeliverNotFoundException;
import com.victor.wang.bigCrab.exception.base.BadRequestException;
import com.victor.wang.bigCrab.model.Card;
import com.victor.wang.bigCrab.model.Deliver;
import com.victor.wang.bigCrab.sharedObject.CardRequest;
import com.victor.wang.bigCrab.sharedObject.DeliverCreate;
import com.victor.wang.bigCrab.sharedObject.DeliverInfo;
import com.victor.wang.bigCrab.sharedObject.DeliverUpdate;
import com.victor.wang.bigCrab.sharedObject.SfOrderSearchResponse;
import com.victor.wang.bigCrab.sharedObject.SfRoute;
import com.victor.wang.bigCrab.sharedObject.lov.CardStatus;
import com.victor.wang.bigCrab.sharedObject.lov.DeliverStatus;
import com.victor.wang.bigCrab.util.UniqueString;
import com.victor.wang.bigCrab.util.XmlUtils;
import com.victor.wang.bigCrab.util.dao.DaoHelper;
import com.victor.wang.bigCrab.util.sf.CallExpressServiceTools;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import ma.glasnost.orika.MapperFacade;
import net.sf.oval.constraint.AssertValid;
import net.sf.oval.guard.Guarded;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Guarded
public class DeliverManager
{
	private static final Logger LOGGER = LoggerFactory.getLogger(DeliverManager.class);

	@Autowired
	DeliverDao deliverDao;

	@Autowired
	CardManager cardManager;

	@Autowired
	private MapperFacade mapper;

	@Value("${sf.clientCode}")
	private String clientCode;

	@Value("${sf.checkWord}")
	private String checkWord;

	@Value("${sf.url}")
	private String sfUrl;


	public Deliver getDeliver(String id)
	{
		LOGGER.debug("DeliverManager, getDeliver; id: {}", id);

		Deliver deliver = deliverDao.get(id);
		if (deliver == null)
		{
			throw new DeliverNotFoundException(id);
		}
		return deliver;
	}

	public Deliver getDeliverByCardNumber(String cardNumber)
	{
		return deliverDao.getByCardNumber(cardNumber);
	}

	public Deliver createDeliver(@AssertValid DeliverCreate deliverCreate)
	{
		LOGGER.info("DeliverManager, createDeliver; deliverCreate: {}", deliverCreate);
		Deliver deliver = mapper.map(deliverCreate, Deliver.class);
		deliver.setId(UniqueString.uuidUniqueString());
		DaoHelper.insert(deliverDao, deliver);
		return deliver;
	}

	public Deliver updateDeliver(String id, @AssertValid DeliverUpdate deliverUpdate)
	{
		LOGGER.info("DeliverManager, updateDeliver; id: {}, deliverUpdate: {}", id, deliverUpdate);
		Deliver deliver = getDeliver(id);
		DaoHelper.updateFromSource(deliverDao, deliverUpdate, deliver);
		return deliver;
	}

	public List<Deliver> findDelivers(
			int page,
			int size)
	{
		LOGGER.debug("DeliverManager, findDeliver; page: {}, size: {}", page, size);
		DeliverDao.DeliverQueryBuild queryBuild = DeliverDao.DeliverQueryBuild.build()
				.pagination(page, size);
		return deliverDao.getList(queryBuild.toParameter());
	}

	public int countDelivers()
	{
		LOGGER.debug("DeliverManager, countDeliver; ");
		DeliverDao.DeliverQueryBuild queryBuild = DeliverDao.DeliverQueryBuild.build();
		return deliverDao.getCount(queryBuild.toParameter());
	}

	public void deleteDeliver(String id)
	{
		LOGGER.info("DeliverManager, deleteDeliver; id: {}", id);
		getDeliver(id);
		deliverDao.delete(id);
	}

	public void sfOrderSelf(String cardNumber, String mailno)
	{
		Card card = cardManager.getCard(cardNumber);
		if (card.getStatus() == CardStatus.UNUSED)
		{
			throw new BadRequestException(400, "card_error", "未使用的卡号");
		}
//		if (card.getStatus() == CardStatus.PHONED)
//		{
//			throw new BadRequestException(400, "card_error", "已电话预约");
//		}
//		if (card.getStatus() == CardStatus.FROZEN)
//		{
//			throw new BadRequestException(400, "card_error", "已冻结");
//		}
//		if (card.getStatus() == CardStatus.DELIVERED)
//		{
//			throw new BadRequestException(400, "card_error", "已发货");
//		}
//		if (card.getStatus() == CardStatus.RECEIVED)
//		{
//			throw new BadRequestException(400, "card_error", "已收货");
//		}
		Deliver deliver = deliverDao.getByCardNumber(cardNumber);
		if (deliver == null)
		{
			throw new BadRequestException(400, "card_error", "无运单信息，无法发货。");
		}
		deliver.setMailno(mailno);
		deliver.setStatus(DeliverStatus.DELIVERED);
		deliver.setRealDeliverAt(new Date());
		DaoHelper.doUpdate(deliverDao, deliver);

		cardManager.delive(card);
	}

	public void sfOrder(CardRequest request)
	{
		StringBuilder errorMsg = new StringBuilder();
		for (String cardNumber : request.getCardNumbers())
		{
			Card card = cardManager.getCard(cardNumber);
			if (card.getStatus() == CardStatus.UNUSED)
			{
				errorMsg.append("未使用的卡号[").append(cardNumber).append("], ");
				continue;
			}
			if (card.getStatus() == CardStatus.PHONED)
			{
				errorMsg.append("已电话预约[").append(cardNumber).append("], ");
				continue;
			}
			if (card.getStatus() == CardStatus.FROZEN)
			{
				errorMsg.append("已冻结[").append(cardNumber).append("], ");
				continue;
			}
			if (card.getStatus() == CardStatus.DELIVERED)
			{
				errorMsg.append("已发货[").append(cardNumber).append("], ");
				continue;
			}
			if (card.getStatus() == CardStatus.RECEIVED)
			{
				errorMsg.append("已收货[").append(cardNumber).append("], ");
				continue;
			}
			Deliver deliver = deliverDao.getByCardNumber(cardNumber);
			if (deliver == null)
			{
				errorMsg.append("无运单信息，无法发货。[").append(cardNumber).append("], ");
				continue;
			}
			CallExpressServiceTools client = CallExpressServiceTools.getInstance();

			Map<String, Object> result = new HashMap<String, Object>();
			result.put("clientCode", clientCode);
			result.put("orderid", deliver.getCardNumber());
			result.put("d_company", "");
			result.put("d_contact", deliver.getdContact());
			result.put("d_tel", deliver.getdTel());
			result.put("d_province", deliver.getdProvince());
			result.put("d_city", deliver.getdCity());
			result.put("d_county", deliver.getdCounty());
			result.put("d_address", deliver.getdAddress());

			String respXml = client.callSfExpressServiceByCSIM(sfUrl,
					getRequest(result, "order.xml"), clientCode, checkWord);

			Document document = XmlUtils.stringTOXml(respXml);
			String responseCode = XmlUtils.getNodeValue(document, "Response/Head");
			if (responseCode.equals("ERR"))
			{
				String responseMsg = XmlUtils.getNodeValue(document, "Response/ERROR");
				throw new BadRequestException(400, "sf_error", responseMsg);
			}
			String mailno = XmlUtils.getValue(document, "mailno");
			deliver.setMailno(mailno);
			deliver.setRealDeliverAt(new Date());
			deliver.setStatus(DeliverStatus.DELIVERED);
			DaoHelper.doUpdate(deliverDao, deliver);

			cardManager.delive(card);
		}
		if (StringUtils.isNoneBlank(errorMsg.toString()))
		{
			throw new BadRequestException(400, "deliver_not_found", errorMsg.toString().substring(0, errorMsg.toString().length() - 2));
		}
	}

	public SfOrderSearchResponse sfGetOrder(String cardNumber)
	{
		Deliver deliver = deliverDao.getByCardNumber(cardNumber);
		if (deliver == null)
		{
			throw new BadRequestException(400, "deliver_not_found", "未找到该卡号");
		}
		if (StringUtils.isBlank(deliver.getMailno()))
		{
			throw new BadRequestException(400, "deliver_not_found", "运单号不存在");
		}
		CallExpressServiceTools client = CallExpressServiceTools.getInstance();

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("clientCode", clientCode);
		result.put("mailno", deliver.getMailno());

		String respXml = client.callSfExpressServiceByCSIM(sfUrl,
				getRequest(result, "route.xml"), clientCode, checkWord);
		Document document = XmlUtils.stringTOXml(respXml);
		String responseCode = XmlUtils.getNodeValue(document, "Response/Head");
		if (responseCode.equals("ERR"))
		{
			String responseMsg = XmlUtils.getNodeValue(document, "Response/ERROR");
			throw new BadRequestException(400, "sf_error", responseMsg);
		}


		Node route = document.getFirstChild().getLastChild().getFirstChild().getFirstChild();

		List<SfRoute> sfRoutes = new ArrayList<>();
		while(route != null) {

			SfRoute r = new SfRoute();

			NamedNodeMap map = route.getAttributes();
			r.setRemark(map.getNamedItem("remark").getNodeValue());
			r.setAcceptTime(map.getNamedItem("accept_time").getNodeValue());
			r.setAcceptAddress(map.getNamedItem("accept_address").getNodeValue());
			r.setOpcode(map.getNamedItem("opcode").getNodeValue());

			sfRoutes.add(r);

			route = route.getNextSibling();
		}

		SfOrderSearchResponse response = new SfOrderSearchResponse();
		response.setDeliverInfo(mapper.map(deliver, DeliverInfo.class));
		response.setRoutes(sfRoutes);

		return response;
	}

	public String getRequest(Map<String, Object> result, String template)
	{
		Configuration configuration = new Configuration();
		configuration.setDefaultEncoding("UTF-8");
		configuration.setClassForTemplateLoading(this.getClass(), "/sf");

		Template temp = null;
		try
		{
			temp = configuration.getTemplate(template);
			StringWriter writer = new StringWriter();

			// 执行模板替换
			temp.process(result, writer);
			return writer.toString();
		}
		catch (TemplateException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return "";
	}
}
