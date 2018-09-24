package com.victor.wang.bigCrab.manager;

import com.victor.wang.bigCrab.dao.DeliverDao;
import com.victor.wang.bigCrab.exception.DeliverNotFoundException;
import com.victor.wang.bigCrab.exception.base.BadRequestException;
import com.victor.wang.bigCrab.model.Deliver;
import com.victor.wang.bigCrab.sharedObject.DeliverCreate;
import com.victor.wang.bigCrab.sharedObject.DeliverUpdate;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import java.io.IOException;
import java.io.StringWriter;
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

	public void sfOrder(String cardNumber)
	{

		Deliver deliver = deliverDao.getByCardNumber(cardNumber);
		if (deliver == null)
		{
			throw new BadRequestException(400, "deliver_not_found", "未找到该卡号");
		}
		CallExpressServiceTools client = CallExpressServiceTools.getInstance();

		String respXml = client.callSfExpressServiceByCSIM(sfUrl,
				getRequest(deliver, "order.xml"), clientCode, checkWord);

		Document document = XmlUtils.stringTOXml(respXml);
		String responseCode = XmlUtils.getNodeValue(document, "Response/Head");
		if (responseCode.equals("ERR"))
		{
			String responseMsg = XmlUtils.getNodeValue(document, "Response/ERROR");
			throw new BadRequestException(400, "sf_error", responseMsg);
		}
	}

	public void sfGetOrder(String cardNumber){
		Deliver deliver = deliverDao.getByCardNumber(cardNumber);
		if (deliver == null)
		{
			throw new BadRequestException(400, "deliver_not_found", "未找到该卡号");
		}
		CallExpressServiceTools client = CallExpressServiceTools.getInstance();

		String respXml = client.callSfExpressServiceByCSIM(sfUrl,
				getRequest(deliver, "search.xml"), clientCode, checkWord);
		Document document = XmlUtils.stringTOXml(respXml);
		String responseCode = XmlUtils.getNodeValue(document, "Response/Head");
		if (responseCode.equals("ERR"))
		{
			String responseMsg = XmlUtils.getNodeValue(document, "Response/ERROR");
			throw new BadRequestException(400, "sf_error", responseMsg);
		}
	}



	public String getRequest(Deliver deliver, String template)
	{
		Configuration configuration = new Configuration();
		configuration.setDefaultEncoding("UTF-8");
		configuration.setClassForTemplateLoading(this.getClass(), "/sf");

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
