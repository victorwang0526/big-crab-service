package com.victor.wang.bigCrab.util;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class MD5Util
{
	/**利用MD5进行加密
	 * @param str  待加密的字符串
	 * @return  加密后的字符串
	 * @throws NoSuchAlgorithmException  没有这种产生消息摘要的算法
	 * @throws UnsupportedEncodingException
	 */
	public static String encoderByMd5(String str)
	{
		String encodedStr = "";
		MessageDigest md5 = null;
		try
		{
			md5 = MessageDigest.getInstance("MD5");
			encodedStr = (new HexBinaryAdapter()).marshal(md5.digest(str.getBytes()));
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return encodedStr;
	}

	public static void main(String[] args){
		String aa = "admin:admin123";
		Base64.getEncoder().encode(aa.getBytes());
		String s = encoderByMd5("admin123");
	}
}
