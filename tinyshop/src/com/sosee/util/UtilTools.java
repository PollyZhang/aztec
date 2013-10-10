package com.sosee.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 常用工具方法汇总
 * @author  :outworld
 * @date    :2013-8-30 上午10:08:10
 * @Copyright:2013 outworld Studio Inc. All rights reserved.
 * @function:
 */
public class UtilTools {

	/**
	 * 获取IP地址
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	       ip = request.getRemoteAddr();
		}
	       return ip;
	}
	
	/**
	 * 生成验证码
	 */
	public static void generateCode(HttpSession session,String sessionKey,HttpServletResponse response){
		int w=60;
		int h=26;
		BufferedImage image = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		//生成背景
		Random random = new Random();
		g.fillRect(0, 0, w, h);
		g.setFont(new Font("Arial", Font.BOLD, 18));		
		for (int i = 0; i < 10; i++) {
			g.setColor(new Color(random.nextInt(100), random.nextInt(100),random.nextInt(100)));
			int x = random.nextInt(w);
			int y = random.nextInt(h);
			int x1 = random.nextInt(w);
			int y1 = random.nextInt(h);
			g.drawLine(x, y, x1, y1);
		}
		//生成验证码
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			int r = random.nextInt(10);
			s.append(r);
			g.setColor(new Color(50 + random.nextInt(100), 50 + random
					.nextInt(100), 50 + random.nextInt(100)));
			int j = random.nextInt(4);
			g.drawString(String.valueOf(r), 13 * i + 6, 16 + j);
		}
		session.removeAttribute(sessionKey);
		session.setAttribute(sessionKey,s.toString());
		g.dispose();
		try {
			ImageIO.write(image, "JPEG",response.getOutputStream());
		} catch (IOException e) {
		}
	}
}
