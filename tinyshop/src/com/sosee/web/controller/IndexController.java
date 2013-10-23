package com.sosee.web.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import com.jfinal.aop.Before;
import com.jfinal.kit.StringKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.sosee.app.member.interceptor.WebLoginInterceptor;
import com.sosee.app.member.pojo.Member;
import com.sosee.app.ticket.pojo.WangShangShouPiaoJiLu;
import com.sosee.app.util.AppConstants;
import com.sosee.config.DefaultConfig;
import com.sosee.util.DateUtil;
import com.sosee.web.util.AlipayUtils;
import com.sosee.web.util.Constants;
import com.sosee.web.validator.IndexValidator;

/**
 * 
 * @author  :outworld
 * @date    :2013-2-22 上午10:51:06
 * @Copyright:2013 outworld Studio Inc. All rights reserved.
 * @function:
 */

public class IndexController extends WebController{


	public void index(){
		//this.createToken("contentsToken",1800);
		render("/WEB-INF/web/index.html");
	}
	
	
}
