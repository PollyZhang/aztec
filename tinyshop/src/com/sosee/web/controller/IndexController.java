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
//	
//	@Before(IndexValidator.class)
//	public void index(){
//		boolean bClearSession=false;//清楚session中缓存的订票数据
//		try{
//			String topType=this.getPara("type");
//			String CheCi=this.getPara("checi");
//			String time=this.getPara("startTime");
//			Date sDate=DateUtil.parseDate(this.getPara("startDate"));
//			Member member=this.getSessionAttr(AppConstants.MEMBER);
//			//无topType为查询页面，topType=0为填写订单页面，topType=1为支付页面，topType=2为完成购票页面
//			//++++++++++++++++++++真实数据开始(1)++++++++++++++++++++
//			if(topType!=null){
//				if(member!=null){
//				//++++++++++++++++++++真实数据结束(1)++++++++++++++++++++
//				//----------------------测试假数据开始(1)--------------------
//				//if(topType!=null){
//				//----------------------测试假数据结束(1)--------------------
//					if(topType.equals("0") && sDate!=null && DateUtil.sizeComparison(sDate, 1,time) && CheCi!=null && !CheCi.equals("") && CheCi.length()<40){
//						//++++++++++++++++++++真实数据开始(2)++++++++++++++++++++
//						//填写订单信息
//						//查询是否启用节日票价
//						boolean bEnable=false;
//						Record record=Db.findFirst("select * from CanShuSheZhi where QiYongJieRiPiaoJia=1");
//						if(record!=null && !record.equals("")){
//							//查看购票日期是否在节假日时间段内
//							Date startDate=record.getDate("JieRiPiaoJiaQiRiQi");
//							Date endDate=record.getDate("JieRiPiaoJiaZhiRiQi");
//							if(startDate.before(sDate) && endDate.after(sDate)){
//								//在节假日中,并且时间符合要求
//								bEnable=true;
//							}
//						}
//						Record recordPiaoJia=Db.findFirst("select * from "+AppConstants.SYNC_RiCheCiJiHuaPiaoJia+DateUtil.getDateString(sDate)+" as r where r.Checi='"+CheCi+"'");
//						if(recordPiaoJia!=null && !recordPiaoJia.equals("")){
//							//正常票价
//							float piaojia=recordPiaoJia.getFloat("PingChangJiBenPiaoJia");
//							if(bEnable){
//								//节假日票价
//								piaojia=recordPiaoJia.getFloat("JieRiJiBenPiaoJia");
//							}
//							this.keepPara();
//							this.setAttr("type", "0");
//							this.setAttr("piaojia", piaojia);
//							this.setSessionAttr("checi", CheCi);
//							this.setSessionAttr("piaojia", piaojia);
//							this.setSessionAttr("startDate", this.getPara("startDate"));
//						}else{
//							bClearSession=true;
//							this.setAttr("msg", "没有找到对应车次的票价信息！");
//							this.setAttr("type", null);
//						}
//						//++++++++++++++++++++真实数据结束(2)++++++++++++++++++++
//						//----------------------测试假数据开始(2)--------------------
//	//					this.setAttr("type", "0");
//	//					this.setAttr("piaojia", 6);
//	//					this.keepPara();
//						//----------------------测试假数据结束(2)--------------------
//					}else if(topType.equals("1")){
//						//++++++++++++++++++++真实数据开始(3)++++++++++++++++++++
//						//查询订票数量，预占座位，跳转支付页面
//						String strCode=this.getSessionAttr("indexCode");
//						String code=this.getPara("code");
//						//判断验证码是否正确
//						if(strCode!=null && code!=null && code.length()<40 && code.equals(strCode)){
//							//通过比对session和前台传递的数据，进行数据准确性比对，数据不符的属于前台数据异常
//							String sCheCi=this.getSessionAttr("checi");
//							String sPiaoJia=String.valueOf(this.getSessionAttr("piaojia"));
//							String sStartDate=this.getSessionAttr("startDate");
//							String piaojia=this.getPara("piaojia");
//							String startDate=this.getPara("startDate");
//							
//							//前台传递需要购买的票数
//							int count=this.getParaToInt("count");
//							//if(sCheCi!=null && CheCi!=null && CheCi.equals(sCheCi) && sPiaoJia!=null){
//							if(sCheCi!=null && CheCi!=null && CheCi.equals(sCheCi) && sPiaoJia!=null && piaojia!=null && Float.valueOf(sPiaoJia).floatValue()==Float.valueOf(piaojia).floatValue() && startDate!=null && sStartDate!=null && startDate.equals(sStartDate)){
//								//售票系统查询实时余票信息
//								//订购的日期参数
//								String startDateFormatStr=DateUtil.getDateString(DateUtil.parseDate(sStartDate));
//								List<Record> yupiaoList=Db.find(DefaultConfig.c3p0Plugin2.getDataSource(), "select * from "+AppConstants.SYNC_RiCheCiJiHua+startDateFormatStr+" where CheCi='"+sCheCi+"'");
//								if(yupiaoList!=null && yupiaoList.size()>0){
//									//Record yupiaoRecord=null;
////									for(Record record:yupiaoList){
////										yupiaoRecord=record;
////										break;
////									}
//									Record yupiaoRecord=yupiaoList.get(0);
//									//实时余票
//									int yupiao=yupiaoRecord.getInt("ZhunShouZuoWei")-yupiaoRecord.getInt("YiShouZuoWei")-yupiaoRecord.getInt("YuLiuPiaoShu");
//									//判断是否有足够的车票
//									if(yupiao>=count){
//										//前台购票所需总票价
//										float piaojiaCount=Float.parseFloat(sPiaoJia)*count;
//										//售票系统占座
//										List<Record> zuoHaoList=Db.find(DefaultConfig.c3p0Plugin2.getDataSource() ,"select top "+count+" ID,ZuoHao from "+AppConstants.SYNC_RiCheCiJiHuaZuoHao+startDateFormatStr+" where CheCi='"+sCheCi+"' and ZhanYongBiaoJi=0 order by ZuoHao ");
//										if(zuoHaoList!=null && zuoHaoList.size()>0 && zuoHaoList.size()==count){
//											String zuoHaoStr="";
//											boolean b=false;//是否在座号字符串中加逗号
//											//更新占位信息
//											for(Record r : zuoHaoList){
//												Db.update(DefaultConfig.c3p0Plugin2.getDataSource(), "update "+AppConstants.SYNC_RiCheCiJiHuaZuoHao+startDateFormatStr+" set ZhanYongBiaoJi=1 where ID='"+r.getBigDecimal("ID").toString()+"'");
//												if(b){
//													zuoHaoStr+=",";
//												}
//												b=true;
//												zuoHaoStr+=r.getStr("ZuoHao");
//											}
//											//更新余票信息
//											Db.update(DefaultConfig.c3p0Plugin2.getDataSource(),"update "+AppConstants.SYNC_RiCheCiJiHua+startDateFormatStr+" set YiShouZuoWei=YiShouZuoWei+"+count+" where CheCi='"+sCheCi+"'");
//											//订单号
//											String workNum=AppConstants.createPiaoHao();
//											//生成售票信息
//											Record workOrderList=Db.findFirst("select concat(concat(concat('[',x.BianMa),']'),x.MingCheng) as XianLuMingCheng,z.MingCheng as ZhongDianZhan,x.FaWangCheZhan as XiaKeZhan,r.WeiJiFei as KouWeiJiFei,r.ZhanWuFei as KouZhanWuFei,r.KongTiaoFei as KouKongTiaoFei,r.QiTaFei as KouQiTaFei,x.JinZhanKou as JinZhanKou,x.FaCheQu as FaCheQu from "+AppConstants.SYNC_RiCheCiJiHuaPiaoJia+startDateFormatStr+" as r,XianLuBianMa as x,ZhanDianBianMa as z where r.XianLuBianMa=x.BianMa and r.ZhanDianBianMa=z.BianMa and r.CheCi='"+sCheCi+"'");
//											Record workOrderInfo=new Record();
//											workOrderInfo.set("id", UUID.randomUUID().toString());//ID
//											workOrderInfo.set("FaCheZhan",this.getAttr("faCheZhan"));//发车站
//											workOrderInfo.set("ShouPiaoZhan",AppConstants.ShouPiaoZhan);//售票站（值：互联网）
//											workOrderInfo.set("CheCi", sCheCi);//车次
//											workOrderInfo.set("XianLuMingCheng", workOrderList.getStr("XianLuMingCheng"));//线路名称
//											workOrderInfo.set("ZhongDianZhan", workOrderList.getStr("ZhongDianZhan"));//终点站
//											workOrderInfo.set("XiaKeZhan", workOrderList.getStr("XiaKeZhan"));//下客站
//											workOrderInfo.set("FaCheRiQi", sStartDate);//发车日期
//											workOrderInfo.set("FaCheShiJian", this.getPara("startTime"));//发车时间
//											workOrderInfo.set("PiaoShu", count);//票数
//											workOrderInfo.set("PiaoJia", sPiaoJia);//票价
//											workOrderInfo.set("KouWeiJiFei", workOrderList.getFloat("KouWeiJiFei"));//扣微机费
//											workOrderInfo.set("KouZhanWuFei", workOrderList.getFloat("KouZhanWuFei"));//扣站务费
//											workOrderInfo.set("KouKongTiaoFei", workOrderList.getFloat("KouKongTiaoFei"));//扣空调费
//											workOrderInfo.set("KouQiTaFei", workOrderList.getFloat("KouQiTaFei"));//扣其他费
//											workOrderInfo.set("ZuoHao", zuoHaoStr);//座号（每个座号用两位数表示：如：09）
//											workOrderInfo.set("DingDanHao",workNum);//订单号
//											workOrderInfo.set("GouPiaoRenDengLuMing", member.getStr("account"));//购票人登录名
//											workOrderInfo.set("GouPiaoRenXingMing", member.getStr("name"));//购票人姓名
//											workOrderInfo.set("QuPiaoRenXingMing", this.getPara("name"));//取票人姓名
//											workOrderInfo.set("ShenFenZhengHao", this.getPara("cardNum"));//取票人身份证号
//											workOrderInfo.set("ShouPiaoRiQiShiJian", new Date());//售票日期
//											workOrderInfo.set("JinZhanKou", workOrderList.getStr("JinZhanKou"));//进站口
//											workOrderInfo.set("FaCheQu", workOrderList.getStr("FaCheQu"));//发车区
//											workOrderInfo.set("ZhuangTai", AppConstants.PAY_STATUS_NO);//状态
//											workOrderInfo.set("ZhuangTaiRiQi", new Date());//状态日期
//											workOrderInfo.set("TongBuBiaoZhi", 0);//同步标记
//											workOrderInfo.set("ShouPiaoRiQiShiJian",new Date());//创建时间（售票记录产生时间）
//											workOrderInfo.set("goupiaorendianhua",member.getStr("telephone"));//登录人的联系电话
//											Db.save("WangShangShouPiaoJiLu", workOrderInfo);
//											Constants.createLog(member.getStr("name"), workNum, AppConstants.PAY_STATUS_NO, new Date());
//											this.setSessionAttr("piaojiaCount",piaojiaCount);
//											this.setAttr("cPiaojia", piaojiaCount);
//											this.setAttr("workNum", workNum);
//											this.setAttr("type", "1");
//										}else{
//											//车次座位占号不足
//											this.keepPara();
//											this.setAttr("msg","车次:"+CheCi+"剩余票不足"+count+"张！");
//											this.setAttr("piaojia", this.getSessionAttr("piaojia"));
//											this.setAttr("type", "0");
//											this.setAttr("sDate", this.getPara("startDate"));
//										}
//									}else{
//										//车次实时剩余票少于预订票
//										this.keepPara();
//										this.setAttr("msg","车次:"+CheCi+"剩余票不足，只有"+yupiao+"张！");
//										this.setAttr("piaojia", this.getSessionAttr("piaojia"));
//										this.setAttr("type", "0");
//										this.setAttr("sDate", this.getPara("startDate"));
//									}
//								}else{
//									//车次数据异常
//									bClearSession=true;
//									this.setAttr("msg", "没有找到车次信息！");
//									this.setAttr("type", null);
//								}
//							}else{
//								//数据异常
//								bClearSession=true;
//								this.setAttr("msg", "数据异常！");
//								this.setAttr("type", null);
//							}
//						}else{
//							this.keepPara();
//							this.setAttr("msg", "验证码有误！");
//							this.setAttr("piaojia", this.getSessionAttr("piaojia"));
//							this.setAttr("type", "0");
//							this.setAttr("sDate", this.getPara("startDate"));
//						}
//						//++++++++++++++++++++真实数据结束(3)++++++++++++++++++++
//						//----------------------测试假数据开始(3)--------------------
//	//						this.setAttr("cPiaojia", 60);
//	//						this.setAttr("workNum", AppConstants.createPiaoHao());
//	//						this.setAttr("type", "1");
//						//----------------------测试假数据结束(3)--------------------
//					}else if(topType.equals("2")){
//						String workNum=this.getPara("workNum");
//						//----------------------测试假数据开始(4)--------------------
//						Db.update("update WangShangShouPiaoJiLu set ZhuangTai='"+AppConstants.PAY_STATUS_YES+"',ZhuangTaiRiQi='"+DateUtil.formatLongDateTime(new Date())+"',TongBuBiaoZhi=0 where DingDanHao='"+workNum+"'");
//						//----------------------测试假数据结束(4)--------------------
//						if(workNum!=null && !workNum.equals("") && workNum.length()<=40){
//							//完成购票页面，提示购票结果
//							String strState=null;
//							Record rec=Db.findFirst("select ZhuangTai from WangShangShouPiaoJiLu where DingDanHao='"+workNum+"'");
//							if(StringKit.notNull(rec)){
//								strState=rec.get("ZhuangTai");
//							}
//							if(StringKit.notBlank(strState) && strState.trim().equals(AppConstants.PAY_STATUS_YES)){
//								this.setAttr("alipay", "yes");
//							}else{
//								this.setAttr("alipay", "no");
//							}
//							this.setAttr("type", "2");
//							this.setAttr("workNum", workNum);
//						}else{
//							this.setAttr("msg", "数据异常！");
//							this.setAttr("type", null);
//						}
//						bClearSession=true;
//					}
//					this.createToken("piaoToken", 1800);
//				}else{
//					bClearSession=true;
//					this.setAttr("msg", "请登陆后，再进行订票操作！");
//					this.setAttr("type", null);
//				}
//			}else{
//				bClearSession=true;
//			}
//			
//		}catch(Exception e){
//			this.setAttr("type", null);
//			bClearSession=true;
//		}
//		if(bClearSession){
//			this.removeSessionAttr("checi");
//			this.removeSessionAttr("piaojia");
//			this.removeSessionAttr("piaojiaCount");
//			this.removeSessionAttr("startDate");
//			//this.keepPara();
//		}
//		this.init();
//		render("/WEB-INF/web/index.html");
//	}
//	/*
//	 * 获取到达站编码
//	 */
//	public void getCode(){
//		String str=this.getPara("q");
//		if(str!=null && str.length()<40){
//			List<Record> list=Db.find("select * from zhandianbianma where PinYinMa like '"+str.toUpperCase()+"%'");
//			if(list!=null && list.size()>0){
//				String s="";
//				boolean b=false;
//				for(Record r : list){
//					if(b){
//						s+=",";
//					}
//					b=true;
//					s+="{";
//					s+="\"data\":{\"b\":\""+r.get("BianMa")+"\",\"py\":\""+r.get("PinYinMa")+"\",\"mc\":\""+r.get("MingCheng")+"\"}";
//					s+="}";
//				}
//				this.renderJson("["+s+"]");
//			}else{
//				this.renderJson("[]");
//			}
//		}else{
//			this.renderJson("[]");
//		}
//	}
//	/*
//	 * 获取车票列表
//	 */
//	public void getList(){
//		try{
//			String strCode=this.getPara("end").replace("'", "");
//			String strDate=this.getPara("startDate");
//			Date startDate=DateUtil.parseDate(strDate);
//			Date newDate=new Date();
//			
//			List<Record> listQuery=new ArrayList<Record>();//可以订票的车次
//			if(startDate!=null && DateUtil.formatDate(newDate).compareTo(strDate)<=0 && strCode!=null && strCode.length()<10){
//				List<Record> listRiCheCiJiHua=Db.find("select r.id,r.XianLuBianMa,r.CheCi,r.FaCheShiJian,r.ZhunShouZuoWei,r.YiShouZuoWei,r.YuLiuPiaoShu,r.BaoBanQingKuang,x.YunXuYuShouTianShu,x.FaWangCheZhan from "+AppConstants.SYNC_RiCheCiJiHua+DateUtil.getDateString(startDate)+" as r,XianLuBianMa as x where x.BianMa=r.XianLuBianMa and r.ZhuangTai=1 and x.FaWangCheZhan='"+strCode+"' order by r.FaCheShiJian asc");
//				//日车次表中的ZhuangTai字段为1的strCode线路名称的车次
//				if(listRiCheCiJiHua!=null && listRiCheCiJiHua.size()>0){
//					for(Record r : listRiCheCiJiHua){
//						try{
//							int BaoBanQingKuang=r.getInt("BaoBanQingKuang");
//							int YuShouTianShu=r.getInt("YunXuYuShouTianShu");
//							String FaCheShiJian=r.getStr("FaCheShiJian");
//							if(YuShouTianShu>0){
//								//预售天数大于零
//								long dateLong=(startDate.getTime()-newDate.getTime())/(24*60*60*1000);
//								//预售天数需要大于(发车时间与当前日期减1),并且需要发车前一个小时以前才可以订票
//								if(dateLong>=0 && dateLong<(YuShouTianShu-1) && DateUtil.sizeComparison(startDate, 1,FaCheShiJian)){
//									listQuery.add(r);
//								}
//							}else if(YuShouTianShu==0 && BaoBanQingKuang==1 && DateUtil.sizeComparison(startDate, 1,FaCheShiJian)){
//									//预售天数为零,已报班并且需要发车前一个小时以前才可以订票
//									listQuery.add(r);
//							}
//						}catch(Exception e){
//							continue;
//						}
//					}
//					String s="[";
//					if(listQuery.size()>0){
//						boolean b=false;
//						for(Record r : listQuery){
//							if(b){
//								s+=",";
//							}
//							b=true;
//							s+="{";
//							s+="\"checi\":\""+r.getStr("CheCi")+"\",";
//							s+="\"time\":\""+r.getStr("FaCheShiJian")+"\",";
//							s+="\"count\":"+r.getInt("ZhunShouZuoWei")+",";
//							s+="\"yupiao\":"+(r.getInt("ZhunShouZuoWei")-r.getInt("YiShouZuoWei")-r.getInt("YuLiuPiaoShu"))+",";
//							s+="\"end\":\""+r.getStr("FaWangCheZhan")+"\"";
//							s+="}";
//						}
//					}
//					s+="]";
//					this.renderJson(s);
//					return;
//				}
////				else{
////					this.renderJson("[]");
////				}
//			}
//		}catch(Exception e){}
//		this.renderJson("[]");
//		//----------------------测试假数据开始--------------------
//		//这一段只要打开下边的就行了，上边的注释不注释都没影响的
////		String s="[";
////		boolean b=false;
////		for(int i=0;i<10;i++){
////			if(b){
////				s+=",";
////			}
////			b=true;
////			s+="{";
////			s+="\"checi\":\""+i+"\",";
////			s+="\"time\":\""+DateUtil.formatDateTime(new Date())+"\",";
////			s+="\"count\":"+100+",";
////			s+="\"yupiao\":"+30+",";
////			s+="\"end\":\"开封\"";
////			s+="}";
////		}
////		s+="]";
////		this.renderJson(s);
//		//----------------------测试假数据结束--------------------
//	}
//	
//	
//	/*
//	 * 获取支付URL
//	 */
//	@Before(WebLoginInterceptor.class)
//	public void getAlipayUrl(){
//		try{
//			//生成单号
//			String workOrderNum=this.getPara("workNum").trim();
//			//付款总金额(seesion取)
//			String totalMoney=String.valueOf(this.getSessionAttr("piaojiaCount"));
//			//付款总金额(前台传)
//			String tMoney=String.valueOf(this.getPara("cPiaojia").trim());
//			//默认银行编码
//			String defaultbank=this.getPara("bankCode").trim();
//			//客户端ip
//			String clientIp=getIpAddr();
//			if(StringKit.isBlank(workOrderNum) || workOrderNum.length()>20 || StringKit.isBlank(defaultbank) || defaultbank.length()>20
//				|| StringKit.isBlank(totalMoney) || StringKit.isBlank(tMoney) || Float.valueOf(totalMoney).floatValue()!=Float.valueOf(tMoney).floatValue() || totalMoney.length()>40 || tMoney.length()>40){
//				//数据异常
//				throw new Exception();
//			}
//			//取得当前定单，方便在支付环节保存客户信息
//			WangShangShouPiaoJiLu currentWorkOrder=this.getModel(WangShangShouPiaoJiLu.class).findFirst("select * from WangShangShouPiaoJiLu where DingDanHao='"+workOrderNum+"'");
//			if(StringKit.notNull(currentWorkOrder)){
//				String url=AlipayUtils.buildRequest(currentWorkOrder, totalMoney, defaultbank, clientIp);
//				this.renderJson("{\"result\":1,\"url\":\""+url+"\"}");
//			}
//			else{
//				this.renderJson("{\"result\":0,\"url\":\"获取支付地址失败！\"}");
//			}
//			
//		}catch(Exception e){
//			this.renderJson("{\"result\":0,\"url\":\"获取支付地址失败！\"}");
//		}
//	}
//	/*
//	 * 获取验证码
//	 */
//	public void getCodeImg(){
//		int w=60;
//		int h=26;
//		BufferedImage image = new BufferedImage(w, h,
//				BufferedImage.TYPE_INT_RGB);
//		Graphics g = image.getGraphics();
//		//生成背景
//		Random random = new Random();
//		g.fillRect(0, 0, w, h);
//		g.setFont(new Font("Arial", Font.BOLD, 18));		
//		for (int i = 0; i < 10; i++) {
//			g.setColor(new Color(random.nextInt(100), random.nextInt(100),random.nextInt(100)));
//			int x = random.nextInt(w);
//			int y = random.nextInt(h);
//			int x1 = random.nextInt(w);
//			int y1 = random.nextInt(h);
//			g.drawLine(x, y, x1, y1);
//		}
//		//生成验证码
//		StringBuffer s = new StringBuffer();
//		for (int i = 0; i < 4; i++) {
//			int r = random.nextInt(10);
//			s.append(r);
//			g.setColor(new Color(50 + random.nextInt(100), 50 + random
//					.nextInt(100), 50 + random.nextInt(100)));
//			int j = random.nextInt(4);
//			g.drawString(String.valueOf(r), 13 * i + 6, 16 + j);
//		}
//		this.removeSessionAttr("indexCode");
//		this.setSessionAttr("indexCode", s.toString());
//		g.dispose();
//		try {
//			ImageIO.write(image, "JPEG",this.getResponse().getOutputStream());
//		} catch (IOException e) {
//		}
//	}
//	/*
//	 * 获取IP地址
//	 */
//	private String getIpAddr() {
//		HttpServletRequest request=this.getRequest();
//		String ip = request.getHeader("x-forwarded-for");
//		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("Proxy-Client-IP");
//		}
//		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("WL-Proxy-Client-IP");
//		}
//		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//	       ip = request.getRemoteAddr();
//		}
//	       return ip;
//	}
//	
//	/**
//	 * 支付后返回跳转到商户界面
//	 */
//	public void return_pay(){
//		//商户订单号
//		String out_trade_no = this.getPara("out_trade_no");
//		//支付宝交易号
//		//String trade_no = this.getPara("trade_no");
//		//交易状态
//		String trade_status = this.getPara("trade_status");
//		//校验消息是否正常
//		boolean verify_result = AlipayUtils.verify(this.getParaMap());
//		//是否支付成功
//		String alipayStr="no";
//		Member member=this.getSessionAttr(AppConstants.MEMBER);
//		if(verify_result){//验证成功
//			if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
//				//更新工单状态，跳转到成功页面
//				Db.update("update WangShangShouPiaoJiLu set ZhuangTai='"+AppConstants.PAY_STATUS_YES+"',ZhuangTaiRiQi='"+DateUtil.formatLongDateTime(new Date())+"',TongBuBiaoZhi=0 where DingDanHao='"+out_trade_no+"'");
//				Constants.createLog(member!=null?member.getStr("name"):"用户未知", out_trade_no, AppConstants.PAY_STATUS_YES, new Date());
//				alipayStr="yes";
//			}else{
//				//支付状态不正常就查询本地库是否已经支付过
//				String strState=null;
//				Record rec=Db.findFirst("select ZhuangTai from WangShangShouPiaoJiLu where DingDanHao='"+out_trade_no+"'");
//				if(StringKit.notNull(rec)){
//					strState=rec.get("ZhuangTai");
//				}
//				if(StringKit.notBlank(strState) && strState.trim().equals(AppConstants.PAY_STATUS_YES)){
//					alipayStr="yes";
//				}
//			}
//		}
//		this.setAttr("workNum", out_trade_no);
//		this.setAttr("type","2");
//		this.setAttr("alipay", alipayStr);
//		//避免上边数据缓存
//		this.removeSessionAttr("checi");
//		this.removeSessionAttr("piaojia");
//		this.removeSessionAttr("piaojiaCount");
//		this.removeSessionAttr("startDate");
//		
//		this.init();
//		render("/WEB-INF/web/index.html");
//	}
//	
//	/**
//	 * 支付后返回跳转到商户界面（异步）
//	 */
//	public void notify_pay(){
//		//商户订单号
//		String out_trade_no = this.getPara("out_trade_no");
//		//支付宝交易号
//		//String trade_no = this.getPara("trade_no");
//		//交易状态
//		String trade_status = this.getPara("trade_status");
//		//校验消息是否正常
//		boolean verify_result = AlipayUtils.verify(this.getParaMap());
//		//异步是否成功
//		String isNotify="fail";
//		Member member=this.getSessionAttr(AppConstants.MEMBER);
//		if(verify_result){//验证成功
//			if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
//				//更新工单状态，跳转到成功页面
//				Db.update("update WangShangShouPiaoJiLu set ZhuangTai='"+AppConstants.PAY_STATUS_YES+"',ZhuangTaiRiQi='"+DateUtil.formatLongDateTime(new Date())+"',TongBuBiaoZhi=0 where DingDanHao='"+out_trade_no+"'");
//				Constants.createLog(member!=null?member.getStr("name"):"用户未知", out_trade_no, AppConstants.PAY_STATUS_YES, new Date());
//				isNotify="success";
//			}else{
//				//支付状态不正常就查询本地库是否已经支付过
//				String strState=null;
//				Record rec=Db.findFirst("select ZhuangTai from WangShangShouPiaoJiLu where DingDanHao='"+out_trade_no+"'");
//				if(StringKit.notNull(rec)){
//					strState=rec.get("ZhuangTai");
//				}
//				if(StringKit.notBlank(strState) && strState.trim().equals(AppConstants.PAY_STATUS_YES)){
//					isNotify="success";
//				}
//			}
//		}
//		//避免上边数据缓存
//		this.removeSessionAttr("checi");
//		this.removeSessionAttr("piaojia");
//		this.removeSessionAttr("piaojiaCount");
//		this.removeSessionAttr("startDate");
//
//		renderText(isNotify);
//	}
}
