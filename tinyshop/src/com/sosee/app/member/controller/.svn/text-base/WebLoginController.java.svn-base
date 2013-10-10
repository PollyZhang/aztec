package com.sosee.app.member.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import com.jfinal.core.Controller;
import com.jfinal.kit.StringKit;
import com.sosee.app.member.pojo.Member;
import com.sosee.app.util.AppConstants;
import com.sosee.sys.log.util.LoggerUtil;
import com.sosee.util.StringUtils;

public class WebLoginController extends Controller {
   
	/**
	 * 登陆跳转
	 */
	public void index(){
		//判断是否已经登陆了
		this.forwardAction("/index");
	}
	
	/**
	 * 登陆验证
	 */
	//@Before(WebLoginInterceptor.class)
	public void login(){
		try{
			String account=this.getPara("loginAccount");
			String password=this.getPara("loginPassword");
			String verifycode=this.getPara("verifycode");
			String systemCode=this.getSessionAttr(AppConstants.VERIFY_CODE);
			boolean bLogin=false;//是否登陆成功
			//判断是否已经登陆了
			Member memberSession=this.getSessionAttr(AppConstants.MEMBER);
			if(StringKit.notNull(memberSession)){
					bLogin=true;
			}else{
				if(StringKit.notBlank(account) && StringKit.notBlank(password)){
					if(StringKit.notBlank(verifycode) && StringKit.notBlank(systemCode) && verifycode.length()==4 && verifycode.equals(systemCode)){
						Member currentMember=this.getModel(Member.class).findFirst("select * from t_member where account='"+account+"' and status='"+AppConstants.MEMBER_STATUS_NORMAL+"'");
					    if(StringKit.notNull(currentMember)){
					    	//找到会员
					    	if(StringUtils.encodeBase64(password).equals(currentMember.getStr("password"))){
								bLogin=true;
								//记录用户
								this.setSessionAttr(AppConstants.MEMBER, currentMember);
								currentMember.set("loginSum", currentMember.getInt("loginSum")+1);
								currentMember.set("lastLoginTime", new Date());
								currentMember.update();
					    	}else {
					    		//密码不正确
					    		this.setAttr("errorinfo", "密码不正确!");	
							}
					    }else{
					       //未找到会员
					    	this.setAttr("errorinfo", "会员帐号不存在!");
					    }
					}else{
					  //录入验证码不正确
						this.setAttr("errorinfo", "验证码输入错误!");
					}
				}else {
					//输入的帐号和密码为空
					this.setAttr("errorinfo", "输入的帐号和密码为空!");
				}

			}
			if(bLogin){
				this.setSessionAttr(AppConstants.WEB_LOGIN_NAME,account );
				this.setSessionAttr(AppConstants.WEB_LOGIN_IP, getIpAddr());
				LoggerUtil.security.info("用户登录_成功_用户验证登录");
			}else{
				//this.setAttr("member", member);
				this.removeSessionAttr(AppConstants.VERIFY_CODE);
			}
		}catch(Exception e){
			this.setAttr("errorinfo", "登陆异常!");
			this.removeSessionAttr(AppConstants.VERIFY_CODE);
		}
		
		index();
	}
	
	/**
	 * 退出
	 */
	public void logout(){
		Member member=this.getSessionAttr(AppConstants.MEMBER);
		if(StringKit.notNull(member)){
			this.removeSessionAttr(AppConstants.MEMBER);
		}
		
		index();
	}
	

	/**
	 * 获取IP地址
	 */
	private String getIpAddr() {
		HttpServletRequest request=this.getRequest();
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
	public void generateCode(){
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
		this.removeSessionAttr(AppConstants.VERIFY_CODE);
		this.setSessionAttr(AppConstants.VERIFY_CODE, s.toString());
		g.dispose();
		try {
			ImageIO.write(image, "JPEG",this.getResponse().getOutputStream());
		} catch (IOException e) {
		}
	}
}
