package com.victor.wang.bigCrab.util.sf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CallExpressServiceTools
{
	private static Logger logger = LoggerFactory.getLogger(CallExpressServiceTools.class);
	private static CallExpressServiceTools tools = new CallExpressServiceTools();

	private CallExpressServiceTools() {
	}

	public static CallExpressServiceTools getInstance() {
		Class var0 = CallExpressServiceTools.class;
		synchronized(CallExpressServiceTools.class) {
			if (tools == null) {
				tools = new CallExpressServiceTools();
			}
		}

		return tools;
	}

	public static String callSfExpressServiceByCSIM(String reqURL, String reqXML, String clientCode, String checkword) {
		String result = null;
		String verifyCode = VerifyCodeUtil.md5EncryptAndBase64(reqXML + checkword);
		result = querySFAPIservice(reqURL, reqXML, verifyCode);
		return result;
	}

	private static String querySFAPIservice(String url, String xml, String verifyCode) {
		HttpClientUtil httpclient = new HttpClientUtil();
		if (url == null) {
			url = "http://bsp-oisp.sf-express.com/bsp-oisp/sfexpressService";
		}

		String result = null;

		try {
			result = httpclient.postSFAPI(url, xml, verifyCode);
			return result;
		} catch (Exception var6) {
			logger.warn(" " + var6);
			return null;
		}
	}


}
