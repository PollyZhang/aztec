package com.sosee.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailUtil {

//	/**
//	 * 设置发邮件的邮件会话
//	 * @param host
//	 * @param IsCheck
//	 * @return
//	 */
//
//	public static Session getSession(String host, boolean IsCheck) {
//		Properties properties = new Properties();
//		properties.put("mail.smtp.host", host);
//		try {
//			if (IsCheck) {
//				properties.put("mail.smtp.auth", "true");
//			} else {
//				properties.put("mail.smtp.auth", "false");
//			}
//
//		} catch (Exception e) {
//			properties.put("mail.smtp.auth", "true");
//		}
//
//		return Session.getDefaultInstance(properties);
//	}
//
//	/**
//	 * 
//	 * 产生消息对象
//	 * Message.RecipientType.TO 收件人
//	 * Message.RecipientType.CC 抄送
//	 * Message.RecipientType.BCC 暗送
//	 * @param session
//	 *            短信会话
//	 * @param mailutil
//	 * @param address
//	 *            邮件的基本内容(接收人,发送人,标题,内容等)
//	 * @return 消息对象
//	 * @throws MessagingException
//	 */
//
//	public static MimeMessage getMimeMessage(Session session,
//			Multipart mailutil, AddressUtil addressUtil)
//			throws MessagingException {
//		MimeMessage mimeMessage = new MimeMessage(session);
//		mimeMessage.setFrom(new InternetAddress(addressUtil.getFrom()));// 加载发件人地址，需要显示多个from地址，可以使用addFrom()方法
//		if (!IsEmpty(addressUtil.getTo())) {// 加载单个收件人地址
//			mimeMessage.setRecipient(Message.RecipientType.TO,
//					new InternetAddress(addressUtil.getTo()));
//		}
//
//		if (addressUtil.getTos() != null && addressUtil.getTos().length > 0) {// 加载多个收件人
//			List<InternetAddress> adresss = MailUtil
//					.getInternetAddresss(addressUtil.getTos());
//			if (!IsEmpty(addressUtil.getTo())) {
//				adresss.add(new InternetAddress(addressUtil.getTo()));
//			}
//
//			mimeMessage.setRecipients(Message.RecipientType.TO,
//					adresss.toArray(new InternetAddress[adresss.size()]));
//		}
//
//		mimeMessage.setSubject(addressUtil.getSubject());// 加载标题
//		mimeMessage.setContent(mailutil);// 将multipart对象放到message中
//		mimeMessage.setSentDate(new Date());// 设置日期
//		return mimeMessage;
//	}
//
//	/**
//	 * 
//	 * 得到 Transport类
//	 * @param session
//	 *            会话
//	 * @param addressUtil
//	 *            邮件内容
//	 * @return 发送邮件需要的Transport类
//	 * @throws MessagingException
//	 */
//
//	public static Transport getTransport(Session session,
//			AddressUtil addressUtil) throws MessagingException {
//		Transport transport = session.getTransport("smtp");
//		transport.connect(addressUtil.getHost(), addressUtil.getUser(),
//				addressUtil.getPwd());// 连接服务器的邮箱
//		if (addressUtil.getPort() != 25) {// 连接服务器的邮箱
//			transport.connect(addressUtil.getHost(), 25, addressUtil.getUser(),
//					addressUtil.getPwd());
//		}
//		return transport;
//	}
//
//	/**
//	 * @param contentPart
//	 * @return
//	 * @throws MessagingException
//	 */
//
//	public static Multipart getMultipart(List<BodyPart> contentPart)
//			throws MessagingException {
//
//		Multipart multipart = null;
//		if (contentPart != null && contentPart.size() > 0) {
//			multipart = new MimeMultipart();
//			for (BodyPart part : contentPart) {
//				multipart.addBodyPart(part);
//			}
//		}
//		return multipart;
//	}
//
//	/**
//	 * 设置邮件的文本内容
//	 * @param txt
//	 *            邮件内容
//	 * @return BodyPart
//	 * @throws MessagingException
//	 */
//
//	public static BodyPart getBodyPart(String txt) throws MessagingException {
//
//		BodyPart bodyPart = new MimeBodyPart();
//		bodyPart.setText(txt);
//		return bodyPart;
//	}
//
//	/**
//	 * 
//	 * 添加附件
//	 * @param filepath
//	 *            文件路径
//	 * @param encoding
//	 *            编码格式
//	 * @param filename
//	 *            文件名称
//	 * @return
//	 * @throws MessagingException
//	 */
//
//	public static BodyPart getBodyPart(String filepath, String encoding,
//			String filename) throws MessagingException {
//
//		BodyPart bodyPart = new MimeBodyPart();
//		if (IsEmpty(filepath)) {// 添加附件的内容
//			bodyPart.setDataHandler(new DataHandler(
//					new FileDataSource(filepath)));
//		}
//
//		if (IsEmpty(filename)) {// 添加附件的标题(这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码)
//			sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
//			if (IsEmpty(encoding)) {
//				bodyPart.setFileName("=?GBK?B?"
//						+ enc.encode(filename.getBytes()) + "?=");
//			} else {
//				bodyPart.setFileName("=?" + encoding + "?B?"
//						+ enc.encode(filename.getBytes()) + "?=");
//			}
//
//		}
//		return bodyPart;
//	}
//
//	/**
//	 * 
//	 * 处理多附件附件
//	 * @param maps
//	 * @param encoding
//	 * @param parts
//	 * @return
//	 * @throws MessagingException
//	 */
//
//	public static List<BodyPart> getBodyParts(Map<String, String> maps,
//			String encoding, List<BodyPart> parts) throws MessagingException {
//
//		if (maps != null && maps.size() > 0) {
//			Set<String> files = maps.keySet();
//			for (String filename : files) {
//				BodyPart part = new MimeBodyPart();
//				if (!IsEmpty(maps.get(filename)) && !IsEmpty(filename)) {// 添加附件的内容
//					part.setDataHandler(new DataHandler(new FileDataSource(maps
//							.get(filename))));
//					// 添加附件的标题
//					// 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
//					sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
//					if (IsEmpty(encoding)) {
//						part.setFileName("=?GBK?B?"
//								+ enc.encode(filename.getBytes()) + "?=");
//					} else {
//						part.setFileName("=?" + encoding + "?B?"
//								+ enc.encode(filename.getBytes()) + "?=");
//					}
//					parts.add(part);
//				}
//			}
//		}
//		return parts;
//	}
//
//	/**
//	 * 
//	 * 处理多人发送
//	 * @param strs
//	 * @return
//	 * @throws AddressException
//	 */
//
//	public static List<InternetAddress> getInternetAddresss(String[] strs)
//			throws AddressException {
//
//		List<InternetAddress> address = new ArrayList<InternetAddress>();
//		for (int i = 1; i < strs.length; i++) {
//			if (!IsEmpty(strs[i])) {
//				address.add(new InternetAddress(strs[i]));
//			}
//		}
//
//		return address;
//	}
//
//	public static void send(AddressUtil addressu) {
//
//		Session session = MailUtil.getSession(addressu.getHost(), true);
//		session.setDebug(true);// 在console处显示发送邮件过程信息
//		try {
//			BodyPart contentPart = MailUtil.getBodyPart(addressu.getTxt());// 设置邮件的文本内容
//			List<BodyPart> parts = new ArrayList<BodyPart>();
//			parts.add(contentPart);
//			// 添加附件
//			// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
//			MailUtil.getBodyParts(addressu.getFilemap(),
//					addressu.getEncoding(), parts);
//			Multipart multipart = MailUtil.getMultipart(parts);
//			MimeMessage mimeMessage = MailUtil.getMimeMessage(session,
//					multipart, addressu);
//			// 保存邮件
//			mimeMessage.saveChanges();
//			// 发送邮件
//			Transport transport = getTransport(session, addressu);
//
//			// 把邮件发送出去
//			transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
//			transport.close();// 关闭连接
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	/**
//	 * 判断是否为空
//	 * @param obj
//	 * @return true false
//	 */
//
//	private static boolean IsEmpty(String string) {
//
//		try {
//			if (string == null || string.equals("")) {
//				return true;
//			} else {
//				return false;
//			}
//
//		} catch (Exception e) {
//			return true;
//		}
//	}
}