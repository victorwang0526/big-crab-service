package com.victor.wang.bigCrab.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.victor.wang.bigCrab.dao.DeliverDao;
import com.victor.wang.bigCrab.exception.DeliverNotFoundException;
import com.victor.wang.bigCrab.exception.base.BadRequestException;
import com.victor.wang.bigCrab.model.Card;
import com.victor.wang.bigCrab.model.Deliver;
import com.victor.wang.bigCrab.sf.dto.CargoInfoDto;
import com.victor.wang.bigCrab.sf.dto.RlsInfoDto;
import com.victor.wang.bigCrab.sf.dto.WaybillDto;
import com.victor.wang.bigCrab.sf.util.MyJsonUtil;
import com.victor.wang.bigCrab.sharedObject.CardRequest;
import com.victor.wang.bigCrab.sharedObject.DeliverCreate;
import com.victor.wang.bigCrab.sharedObject.DeliverInfo;
import com.victor.wang.bigCrab.sharedObject.DeliverUpdate;
import com.victor.wang.bigCrab.sharedObject.SfOrderSearchResponse;
import com.victor.wang.bigCrab.sharedObject.SfPrintInfo;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
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

	public SfPrintInfo getPrint(String cardNumber, String mailno){

		SfPrintInfo sfPrintInfo = new SfPrintInfo();
		try {

		/*********2联150 丰密面单**************/
		/**调用打印机 不弹出窗口 适用于批量打印【二联单】 **/
		String url7 = "http://localhost:4040/sf/waybill/print?type=V2.0.FM_poster_100mm150mm&output=noAlertPrint";
		/** 调用打印机 弹出窗口 可选择份数 适用于单张打印【二联单】**/
		String url8 = "http://localhost:4040/sf/waybill/print?type=V2.0.FM_poster_100mm150mm&output=print";
		/**直接输出图片的BASE64编码字符串 可以使用html标签直接转换成图片【二联单】**/
		String url9 = "http://116.62.120.169:4040/sf/waybill/print?type=V2.0.FM_poster_100mm150mm&output=image";
		/*********3联210 丰密面单**************/
		/**调用打印机 不弹出窗口 适用于批量打印【三联单】**/
		String url10 = "http://localhost:4040/sf/waybill/print?type=V3.0.FM_poster_100mm210mm&output=noAlertPrint";
		/**调用打印机 弹出窗口 可选择份数 适用于单张打印【三联单】**/
		String url11 = "http://localhost:4040/sf/waybill/print?type=V3.0.FM_poster_100mm210mm&output=print";
		/** 直接输出图片的BASE64编码字符串 可以使用html标签直接转换成图片【三联单】**/
		String url12 = "http://localhost:4040/sf/waybill/print?type=V3.0.FM_poster_100mm210mm&output=image";
		//根据业务需求确定请求地址
		String reqURL= url9;
		//是否需要logo
		boolean topLogo=true;//true 需要logo  false 不需要logo
		if(reqURL.contains("V2.0")&&topLogo){
			reqURL=reqURL.replace("V2.0", "V2.1");
		}
		if(reqURL.contains("V3.0")&&topLogo){
			reqURL=reqURL.replace("V3.0", "V3.1");
		}
		System.out.println(reqURL);
		/**注意 需要使用对应业务场景的url  **/
		URL myURL = new URL(reqURL);
		//其中127.0.0.1:4040为打印服务部署的地址（端口如未指定，默认为4040），
		//type为模板类型（支持两联、三联，尺寸为100mm*150mm和100mm*210mm，type为poster_100mm150mm和poster_100mm210mm）
		//A5 poster_100mm150mm   A5 poster_100mm210mm
		//output为输出类型,值为print或image，如不传，
		//默认为print（print 表示直接打印，image表示获取图片的BASE64编码字符串）
		//V2.0/V3.0模板顶部是带logo的  V2.1/V3.1顶部不带logo
		HttpURLConnection httpConn = (HttpURLConnection) myURL.openConnection();
		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);
		httpConn.setUseCaches(false);
		httpConn.setRequestMethod("POST");
		//httpConn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
		httpConn.setRequestProperty("Content-Type", "text/plain;charset=utf-8");
		httpConn.setConnectTimeout(5000);
		httpConn.setReadTimeout(3 * 5000);
		List<WaybillDto> waybillDtoList = new ArrayList<WaybillDto>();
		WaybillDto dto = new WaybillDto();
		//必填
		dto.setAppId("WFX_a1uDS");//对应clientCode
		dto.setAppKey("2PwIcTrKG92tyWYCcNmgJtwV4boJRDVe");//对应checkWord
		dto.setMailNo("SF7551234567890");
		//dto.setMailNo("SF7551234567890,SF2000601520988,SF2000601520997");//子母单方式
		//签回单号  签单返回服务 会打印两份快单 其中第二份作为返寄的单
		//dto.setReturnTrackingNo("SF1060081717189");
		//收件人信息
		dto.setConsignerProvince("广东省");
		dto.setConsignerCity("深圳市");
		dto.setConsignerCounty("南山区");
		dto.setConsignerAddress("学府路软件产业基地2B12楼5200708号"); //详细地址建议最多30个字  字段过长影响打印效果
		dto.setConsignerCompany("神一样的科技");
		dto.setConsignerMobile("15893799999");
		dto.setConsignerName("风一样的旭哥");
		dto.setConsignerShipperCode("518052");
		dto.setConsignerTel("0755-33123456");
		//寄件人信息
		dto.setDeliverProvince("浙江省");
		dto.setDeliverCity("杭州市");
		dto.setDeliverCounty("拱墅区");
		dto.setDeliverCompany("神罗科技集团有限公司");
		dto.setDeliverAddress("舟山东路708号古墩路北（玉泉花园旁）百花苑西区7-2-201室");//详细地址建议最多30个字  字段过长影响打印效果
		dto.setDeliverName("艾丽斯");
		dto.setDeliverMobile("15881234567");
		dto.setDeliverShipperCode("310000");
		dto.setDeliverTel("0571-26508888");
		dto.setDestCode("755");//目的地代码 参考顺丰地区编号
		dto.setZipCode("571");//原寄地代码 参考顺丰地区编号
		//快递类型
		//1 ：标准快递   2.顺丰特惠   3： 电商特惠   5：顺丰次晨  6：顺丰即日  7.电商速配   15：生鲜速配
		dto.setExpressType(1);
		//COD代收货款金额,只需填金额, 单位元- 此项和月结卡号绑定的增值服务相关
		dto.setCodValue("999.9");
		dto.setInsureValue("501");//声明货物价值的保价金额,只需填金额,单位元
		dto.setMonthAccount("7550385912");//月结卡号
		dto.setPayMethod(1);//
		/**丰密面单相关**/
		List<RlsInfoDto> rlsInfoDtoList =new  ArrayList<RlsInfoDto>();
		RlsInfoDto rlsMain=new  RlsInfoDto();
		//主面单号
		rlsMain.setWaybillNo(dto.getMailNo());
		rlsMain.setDestRouteLabel("755WE-571A3");
		rlsMain.setPrintIcon("11110000");
		rlsMain.setProCode("T4");
		rlsMain.setAbFlag("A");
		rlsMain.setXbFlag("XB");
		rlsMain.setCodingMapping("F33");
		rlsMain.setCodingMappingOut("1A");
		rlsMain.setDestTeamCode("012345678");
		rlsMain.setSourceTransferCode("021WTF");
		//对应下订单设置路由标签返回字段twoDimensionCode 该参
		rlsMain.setQRCode("MMM={'k1':'755WE','k2':'021WT','k3':'','k4':'T4','k5':'SF7551234567890','k6':''}");
		rlsInfoDtoList.add(rlsMain);
		if(dto.getReturnTrackingNo()!=null){
			RlsInfoDto rlsBack=new  RlsInfoDto();
			//签回面单号Z
			rlsBack.setWaybillNo(dto.getReturnTrackingNo());
			rlsBack.setDestRouteLabel("021WTF");
			rlsBack.setPrintIcon("11110000");
			rlsBack.setProCode("T4");
			rlsBack.setAbFlag("A");
			rlsBack.setXbFlag("XB");
			rlsBack.setCodingMapping("1A");
			rlsBack.setCodingMappingOut("F33");
			rlsBack.setDestTeamCode("87654321");
			rlsBack.setSourceTransferCode("755WE-571A3");
			//对应下订单设置路由标签返回字段twoDimensionCode 该参
			rlsBack.setQRCode("MMM={'k1':'21WT','k2':'755WE','k3':'','k4':'T4','k5':'SF1060081717189','k6':''}");
			rlsInfoDtoList.add(rlsBack);
		}
		//设置丰密面单必要参数
		dto.setRlsInfoDtoList(rlsInfoDtoList);
		//客户个性化Logo 必须是个可以访问的图片本地路径地址或者互联网图片地址
		//dto.setCustLogo("D:\\ibm.jpg");
		//备注相关
		dto.setMainRemark("这是主面单的备注");
		dto.setChildRemark("子单号备注");
		dto.setReturnTrackingRemark("迁回单备注");
		//加密项
		dto.setEncryptCustName(true);//加密寄件人及收件人名称
		dto.setEncryptMobile(true);//加密寄件人及收件人联系手机
		CargoInfoDto cargo1 = new CargoInfoDto();
		cargo1.setCargo("苹果7S");
		cargo1.setCargoCount(1);
		cargo1.setCargoUnit("件");
		cargo1.setSku("00015645");
		cargo1.setRemark("手机贵重物品 小心轻放");
		CargoInfoDto cargo2 = new CargoInfoDto();
		cargo2.setCargo("苹果macbook pro");
		cargo2.setCargoCount(1);
		cargo2.setCargoUnit("件");
		cargo2.setSku("00015646");
		cargo2.setRemark("笔记本贵重物品 小心轻放");
		List<CargoInfoDto> cargoInfoList = new ArrayList<CargoInfoDto>();
		cargoInfoList.add(cargo1);
		cargoInfoList.add(cargo2);
		dto.setCargoInfoDtoList(cargoInfoList);
		waybillDtoList.add(dto);
		System.out.println("请求参数： "+ MyJsonUtil.object2json(waybillDtoList));
		ObjectMapper objectMapper = new ObjectMapper();
		StringWriter stringWriter = new StringWriter();
		objectMapper.writeValue(stringWriter,waybillDtoList);
		httpConn.getOutputStream().write(stringWriter.toString().getBytes());
		httpConn.getOutputStream().flush();
		httpConn.getOutputStream().close();
		InputStream in = httpConn.getInputStream();
		BufferedReader in2=new BufferedReader(new InputStreamReader(in));
		String y="";
		String strImg="";
		while((y=in2.readLine())!=null){
			strImg=y.substring(y.indexOf("[")+1,y.length()-"]".length()-1);
			if(strImg.startsWith("\"")){
				strImg=strImg.substring(1,strImg.length());
			}
			if(strImg.endsWith("\"")){
				strImg=strImg.substring(0,strImg.length()-1);
			}
		}
		//将换行全部替换成空
		strImg=strImg.replace("\\n", "");
		//System.out.println(strImg);
//        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-HHmmss");
//        String dateStr = format.format(new Date());
//        List<String> files = new ArrayList<String>();
//        if(strImg.contains("\",\"")){
//            //如子母单及签回单需要打印两份或者以上
//            String[] arr = strImg.split("\",\"");
//            /**输出图片到本地 支持.jpg、.png格式**/
//            for(int i = 0; i < arr.length; i++) {
//                String fileName = "F:\\qiaoWay"+dateStr+"-"+i+".jpg";
//                Base64ImageTools.generateImage(arr[i].toString(), fileName);
//                files.add(fileName);
//            }
//        }else{
//            String fileName = "F:\\qiaoWaybill"+dateStr+".jpg";
//            Base64ImageTools.generateImage(strImg, fileName);
//            files.add(fileName);
//        }
		sfPrintInfo.setImageStr(strImg);

		}catch (Exception e) {
			e.printStackTrace();
		}
		return sfPrintInfo;
	}
}
