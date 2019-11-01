package com.victor.wang.bigCrab.util;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class XmlUtils
{

	public static String getValue(String xmlString, String nodeName) {
		Document document = XmlUtils.stringTOXml(xmlString);
		return XmlUtils.getValue(document, "OrderResponse", nodeName);
	}


	public static String getValue(String xmlString, String tagName, String nodeName) {
		Document document = XmlUtils.stringTOXml(xmlString);
		return XmlUtils.getValue(document, tagName, nodeName);
	}

	public static String getValue(Document document, String nodeName) {
		return XmlUtils.getValue(document, "OrderResponse", nodeName);
	}

	public static String getValue(Document document, String tagName, String nodeName) {
		NodeList booklist = document.getElementsByTagName(tagName);
		if(booklist == null || booklist.getLength() == 0){
			return null;
		}
		Node node = booklist.item(0).getAttributes().getNamedItem(nodeName);
		if(node == null) {
			return null;
		}
		return node.getNodeValue();
	}




	public static String getValue(String tagName, Document document, String nodeName) {
		NodeList booklist = document.getElementsByTagName(tagName);
		if(booklist == null || booklist.getLength() == 0){
			return null;
		}
		return booklist.item(0).getAttributes().getNamedItem(nodeName).getNodeValue();
	}

	/**
	 * @param document
	 * @return 某个节点的值 前提是需要知道xml格式，知道需要取的节点相对根节点所在位置
	 */
	public static String getNodeValue(Document document, String nodePaht)
	{
		XPathFactory xpfactory = XPathFactory.newInstance();
		XPath path = xpfactory.newXPath();
		String servInitrBrch = "";
		try
		{
			servInitrBrch = path.evaluate(nodePaht, document);
		}
		catch (XPathExpressionException e)
		{
			e.printStackTrace();
		}
		return servInitrBrch;
	}

	/**
	 * @param str xml形状的str串
	 * @return Document 对象
	 */
	public static Document stringTOXml(String str)
	{

		StringBuilder sXML = new StringBuilder();
		sXML.append(str);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		Document doc = null;
		try
		{
			InputStream is = new ByteArrayInputStream(sXML.toString().getBytes("utf-8"));
			doc = dbf.newDocumentBuilder().parse(is);
			is.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return doc;
	}


}
