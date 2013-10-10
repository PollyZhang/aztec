package com.sosee.util;



import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/*
 * 对系统配置信息,XML数据进行设置与读取
 * 
 */
@SuppressWarnings("serial")
public class XMLParser implements Serializable {




	// 定义系统初始化基本参数变量
	// first:选项配置信息

	public XMLParser() {

	}

	/*
	 * create new xml file @param:filename //xml file name
	 * @return:true:success;false:fail
	 * 
	 */
	public boolean createXMLFile(String fileName) {
		boolean bReturnValue = false;

		// create Document object
		Document document = DocumentHelper.createDocument();

		// write document object to file
		bReturnValue = WriteXMLtoFile(document, fileName);

		return bReturnValue;
	}

	/*
	 * create Document object @return:document //Document object
	 * 
	 */
	public Document CreateXMLDocument() {
		Document document = DocumentHelper.createDocument();
		return document;
	}

	/*
	 * delete xml file Element Dom4j:delete element name
	 * 
	 * @param:document //source filename @param:nodes //destion filename
	 * @param:elementName //select element name @param:childElementName //select
	 * child element name @return:document //Document object
	 * 
	 */
	public Document AddXMLElement(Document document, String nodes,
			String elementName, String childElementName) {
		List list = document.selectNodes(nodes + "/" + elementName);

		Iterator iter = list.iterator();

		while (iter.hasNext()) {

			Element parentElement = (Element) iter.next();
			parentElement.addElement(childElementName);
		}

		return document;
	}

	/*
	 * delete xml file Element Dom4j:delete element name
	 * 
	 * @param:document //source filename @param:nodes //destion filename
	 * @param:elementName //select element name @param:sAttribute //select
	 * attribute name @param:sValue // attribute value @return:document
	 * //Document object
	 * 
	 */
	public Document AddXMLAttribute(Document document, String nodes,
			String elementName, String sAttribute, String sValue) {
		List list = document.selectNodes(nodes + "/" + elementName);

		Iterator iter = list.iterator();

		while (iter.hasNext()) {

			Element parentElement = (Element) iter.next();
			parentElement.addAttribute(sAttribute, sValue);
		}

		return document;
	}

	/*
	 * delete xml file Element Dom4j:delete element name
	 * 
	 * @param:document //source filename @param:nodes //destion filename
	 * @param:elementName //select element name @param:childElementName //select
	 * child element name @return:document //Document object
	 * 
	 */
	public Document DeleteXMLElement(Document document, String nodes,
			String elementName, String childElementName) {

		List list = document.selectNodes(nodes + "/" + elementName);

		Iterator iter = list.iterator();

		while (iter.hasNext()) {

			Element parentElement = (Element) iter.next();
			Iterator childIterator = parentElement
					.elementIterator(childElementName);

			while (childIterator.hasNext()) {
				Element childElement = (Element) childIterator.next();
				parentElement.remove(childElement);
			}

		}

		return document;
	}

	/*
	 * delete xml file Element Dom4j:delete element name
	 * 
	 * @param:document //source filename @param:nodes //destion filename
	 * @param:elementName //select element name @param:childElementName //select
	 * child element name @param:sValue //select element value @return:document
	 * //Document object
	 * 
	 */
	public Document DeleteXMLElement(Document document, String nodes,
			String elementName, String childElementName, String sValue) {

		List list = document.selectNodes(nodes + "/" + elementName);

		Iterator iter = list.iterator();

		while (iter.hasNext()) {

			Element parentElement = (Element) iter.next();
			Iterator childIterator = parentElement
					.elementIterator(childElementName);

			while (childIterator.hasNext()) {
				Element childElement = (Element) childIterator.next();

				if (childElement.getText().equals(sValue)) {
					parentElement.remove(childElement);
				}
			}

		}

		return document;
	}

	/*
	 * delete xml file Element attribute Dom4j:delete element name
	 * 
	 * @param:document //source filename @param:nodes //destion filename
	 * @param:attributeName //select attribute name @return:document //Document
	 * object
	 * 
	 */
	public Document DeleteXMLAttribute(Document document, String nodes,
			String attributeName) {

		List list = document.selectNodes(nodes);

		Iterator iter = list.iterator();

		while (iter.hasNext()) {

			Element element = (Element) iter.next();

			Iterator attributeIterator = element.attributeIterator();
			while (attributeIterator.hasNext()) {
				Attribute attribute = (Attribute) attributeIterator.next();
				if (attribute.getName().equals(attributeName)) {
					element.remove(attribute);
				}
			}

		}

		return document;
	}

	/*
	 * delete xml file Element attribute Dom4j:delete element name
	 * 
	 * @param:document //source filename @param:nodes //destion filename
	 * @param:attributeName //select attribute name @param:dValue //attribute
	 * value @return:document //Document object
	 * 
	 */
	public Document DeleteXMLAttribute(Document document, String nodes,
			String attributeName, String dValue) {

		List list = document.selectNodes(nodes);

		Iterator iter = list.iterator();

		while (iter.hasNext()) {

			Element element = (Element) iter.next();

			Iterator attributeIterator = element.attributeIterator();
			while (attributeIterator.hasNext()) {
				Attribute attribute = (Attribute) attributeIterator.next();
				if (attribute.getName().equals(attributeName)
						&& attribute.getText().equals(dValue)) {
					element.remove(attribute);
				}
			}

		}

		return document;
	}

	/*
	 * modify xml file Element value Dom4j:modify element name
	 * 
	 * @param:document //source filename @param:nodes //destion filename
	 * @param:elementName //select element name @param:desElementName // des
	 * element name @param:dValue //set vlaue @return:document //Document object
	 * 
	 */
	public Document ModifyXMLElement(Document document, String nodes,
			String elementName, String desElementName, String dValue) {

		List list = document.selectNodes(nodes + "/" + elementName);

		Iterator iter = list.iterator();

		while (iter.hasNext()) {

			Element childElement = (Element) iter.next();
			if (childElement.getText().equals(dValue))
				childElement.setName(desElementName);
		}

		return document;
	}

	/*
	 * modify xml file Element value Dom4j:modify Element name
	 * 
	 * @param:document //source filename @param:nodes //destion filename
	 * @param:elementName //select element name @param:desElementName // des
	 * element name @return:document //Document object
	 * 
	 */
	public Document ModifyXMLElement(Document document, String nodes,
			String elementName, String desElementName) {

		List list = document.selectNodes(nodes + "/" + elementName);

		Iterator iter = list.iterator();

		while (iter.hasNext()) {

			Element childElement = (Element) iter.next();
			childElement.setName(desElementName);
		}

		return document;
	}

	/*
	 * modify xml file attribute Dom4j:modify element name
	 * 
	 * @param:document //source filename @param:nodes //destion filename example
	 * /people/person/@ssn @param:sValue //select nodes value @param:dValue
	 * //set vlaue @return:document //Document object
	 * 
	 */
	public Document ModifyXMLAttribute(Document document, String nodes,
			String sValue, String dValue) {

		List list = document.selectNodes(nodes);
		Iterator it = list.iterator();

		while (it.hasNext()) {
			Attribute attribute = (Attribute) it.next();
			if (attribute.getValue().equals(sValue)) {
				attribute.setValue(dValue);
			}
		}
		return document;
	}

	/*
	 * modify xml file Element value Dom4j:modify
	 * 
	 * @param:document //source filename @param:nodes //destion
	 * filename,example:/people/person @param:dValue //set vlaue
	 * @return:document //Document object
	 * 
	 */
	public Document ModifyXMLElementValue(Document document, String nodes,
			String dValue) {

		List list = document.selectNodes(nodes);

		Iterator iter = list.iterator();

		while (iter.hasNext()) {

			Element element = (Element) iter.next();
			element.setText(dValue);
		}

		return document;
	}

	/*
	 * modify xml file Element value Dom4j:modify
	 * 
	 * @param:document //source filename @param:nodes //destion filename
	 * @param:sValue //select nodes value @param:dValue //set vlaue
	 * @return:document //Document object
	 * 
	 */
	public Document ModifyXMLElementValue(Document document, String nodes,
			String sValue, String dValue) {

		List list = document.selectNodes(nodes);

		Iterator iter = list.iterator();

		while (iter.hasNext()) {

			Element element = (Element) iter.next();
			if (element.getText().equals(sValue))
				element.setText(dValue);
		}

		return document;
	}

	/**
	 * format xml document,example chinese gb2312
	 * 
	 * @param sFormat
	 *            //code string
	 * 
	 * @return format //OutputFormat
	 * 
	 */

	public OutputFormat setXMLFormat(String sFormat) {

		/** create format */
		OutputFormat format = OutputFormat.createPrettyPrint();

		/** code */
		format.setEncoding(sFormat);

		return format;
	}

	/*
	 * reader a xml file @param:fileName //xml file @return:document //Document
	 * object
	 */
	public Document XMLParse(String fileName) {
		try {
			SAXReader reader = new SAXReader();

			Document document = reader.read(new File(fileName));

			return document;

		} catch (NullPointerException ne) {
			ne.printStackTrace();
		} catch (DocumentException de) {
			de.printStackTrace();
		}

		return null;
	}

	/*
	 * reader a xml file @param:url //URL object @return:document //Document
	 * object
	 */
	public Document XMLParse(URL url) {
		try {
			SAXReader reader = new SAXReader();

			Document document = reader.read(url);

			return document;

		} catch (DocumentException de) {
			de.printStackTrace();
		}

		return null;
	}

	/*
	 * write document object to file @param: document //Document object; @param:
	 * fileName //create file name @param:format //format
	 * @return:success:true;fail:false;
	 * 
	 */
	public boolean WriteXMLtoFile(Document document, String fileName,
			OutputFormat format) {
		boolean bReturnValue = false;

		try {
			// create XMLWriter object

			XMLWriter writer = new XMLWriter(
					new FileWriter(new File(fileName)), format);

			writer.write(document);

			writer.close();

			bReturnValue = true;
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return bReturnValue;
	}

	/*
	 * write document object to file @param: document //Document object; @param:
	 * fileName //create file name @return:success:true;fail:false;
	 * 
	 */
	public boolean WriteXMLtoFile(Document document, String fileName) {
		boolean bReturnValue = false;

		try {
			// create XMLWriter object

			//XMLWriter writer = new XMLWriter(new FileWriter(new File(fileName)));
			XMLWriter writer = new XMLWriter(new FileOutputStream(fileName));
			writer.write(document);

			writer.close();

			bReturnValue = true;
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return bReturnValue;
	}

	/*
	 * get xml file attribute
	 * 
	 * @param:document //source document object @param:nodes //destion filename
	 * example /people/person/@ssn
	 * 
	 */
	protected String getElement(Document document, String node) {
		String sValue = "";
		Node nd = document.selectSingleNode(node);
		sValue = nd.getStringValue();
		return sValue;
	}

	/*
	 * get xml file attribute
	 * 
	 * @param:document //source document object @param:nodes //destion filename
	 * example /people/person/@ssn
	 * 
	 */
	public String getElement(Element element, String node) {
		String sValue = "";
		Node nd = element.selectSingleNode(node);
		if(nd!=null){
		   sValue = nd.getStringValue();
		}
		return sValue;
	}

	/*
	 * get xml
	 * 
	 * @param:document //source document object @param:nodes //destion filename
	 * example /people/person/@ssn
	 * 
	 */
	public List getElements(Document document, String nodes) {
		List list = document.selectNodes(nodes);
		return list;
	}


	/*
	 * get xml
	 * 
	 * @param:document //source document object @param:nodes //destion filename
	 * example /people/person/@ssn
	 * 
	 */
	public List getElements(Element element, String nodes) {
		List list = element.selectNodes(nodes);
		return list;
	}

	/**
	 *get root element 
	 */
	 public Element getRootElement(Document doc){
	       return doc.getRootElement();
	 }

}
