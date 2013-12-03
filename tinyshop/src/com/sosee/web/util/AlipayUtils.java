package com.sosee.web.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.sosee.util.DateUtil;

/**
 * 
 * 作者:强乐
 * 功能:支付宝工具类
 * 时间:2013-3-27 下午8:46:08
 */
public class AlipayUtils {
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	private static String partner = "2088901840914725";
	// 商户的私钥
	private static String key = "yc2yjgq13y78zpl1z3jg39rnu2kd89pq";
	//支付宝提供给商户的服务接入网关URL(新)
	private static String alipayGateWayNew = "https://mapi.alipay.com/gateway.do?";
	// 字符编码格式 目前支持 gbk 或 utf-8
	private static String input_charset = "utf-8";
	// 签名方式 不需修改
	private static String sign_type = "MD5";
	//支付类型(必填，不能修改)
	private static String payment_type = "1";
	//服务器异步通知页面路径,需http://格式的完整路径，不能加?id=123这类自定义参数
	private static String notify_url = "http://www.zhongxinruanjian.com/index/notify_pay";
	//页面跳转同步通知页面路径,需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/
	private static String return_url = "http://www.zhongxinruanjian.com/index/return_pay";
	//private static String return_url = "http://localhost:8000/olticket/index/return_pay";
	//卖家支付宝帐户(必填)
	private static String seller_email = "zzzhongxin@sina.cn";
	//订单名称(必填)
	private static String subject = "车票";
	//订单描述
	private static String body = "车票";
	//默认支付方式(必填)
	private static String paymethod = "bankPay";
	//商品展示地址,需以http://开头的完整路径，例如：http://www.xxx.com/myorder.html
	private static String show_url = "";
	//http://www.zhongxinruanjian.com/create_direct_pay_by_user-JAVA-UTF-8/return_url.jsp?body=%E5%B1%88%E5%BF%97%E6%B0%91%E8%AE%A2%E8%B4%AD%E7%9A%842013-06-16%E5%BC%80%E8%BD%A6%E6%97%B6%E9%97%B4%E4%B8%BA11.30%E4%BB%8E%E6%96%B0%E4%B9%A1%E5%AE%A2%E8%BF%90%E6%80%BB%E7%AB%99%E5%88%B0%E5%A4%A7%E9%82%91388%E6%AC%A1%2C%E7%BC%96%E5%8F%B7%E4%B8%BAPH1371348585703%E7%9A%84%E5%AE%9A%E5%8D%95&buyer_email=outworld%40139.com&buyer_id=2088702790747465&exterface=create_direct_pay_by_user&is_success=T&notify_id=RqPnCoPT3K9%252Fvwbh3I72KLYHsNptntbBZ7CU3zhBWdauZt%252FMaSMyq4hdOuYMSlpG9CTF&notify_time=2013-06-16+10%3A11%3A14&notify_type=trade_status_sync&out_trade_no=PH1371348585703&payment_type=1&seller_email=zzzhongxin%40sina.cn&seller_id=2088901840914725&subject=%E5%B1%88%E5%BF%97%E6%B0%91%E8%AE%A2%E8%B4%AD%E7%9A%842013-06-16%E5%BC%80%E8%BD%A6%E6%97%B6%E9%97%B4%E4%B8%BA11.30%E4%BB%8E%E6%96%B0%E4%B9%A1%E5%AE%A2%E8%BF%90%E6%80%BB%E7%AB%99%E5%88%B0%E5%A4%A7%E9%82%91388%E6%AC%A1%2C%E7%BC%96%E5%8F%B7%E4%B8%BAPH1371348585703%E7%9A%84%E5%AE%9A%E5%8D%95&total_fee=0.01&trade_no=2013061637287546&trade_status=TRADE_SUCCESS&sign=41fc8d59717de4aea96376606f4c66ca&sign_type=MD5
	//支付宝消息验证地址
	private static final String HTTPS_VERIFY_URL = "https://mapi.alipay.com/gateway.do?service=notify_verify&";
	
	
	
    
    /**
     * 生成要请求给支付宝的参数数组
     * @param sParaTemp 请求前的参数数组
     * @return 要请求的参数数组
     */
    private static Map<String, String> buildRequestPara(Map<String, String> sParaTemp) {
        //除去数组中的空值和签名参数
        Map<String, String> sPara = paraFilter(sParaTemp);
        //生成签名结果
        String mysign = buildRequestMysign(sPara);

        //签名结果与签名方式加入请求提交参数组中
        sPara.put("sign", mysign);
        sPara.put("sign_type", sign_type);

        return sPara;
    }
    
    /**
     * 生成签名结果
     * @param sPara 要签名的数组
     * @return 签名结果字符串
     */
    private static String buildRequestMysign(Map<String, String> sPara) {
    	String prestr = createLinkString(sPara); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = "";
        if(sign_type.equals("MD5") ) {
        	mysign = MD5.sign(prestr, key, input_charset);
        }
        return mysign;
    }
	
	/** 
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    private static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }
    
    /** 
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    private static Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }
    
    /**
     * 用于防钓鱼，调用接口query_timestamp来获取时间戳的处理函数
     * 注意：远程解析XML出错，与服务器是否支持SSL等配置有关
     * @return 时间戳字符串
     * @throws IOException
     * @throws DocumentException
     * @throws MalformedURLException
     */
    @SuppressWarnings("unchecked")
	private static String query_timestamp(){
        StringBuffer result = new StringBuffer();
    	try{
        //构造访问query_timestamp接口的URL串
        String strUrl = alipayGateWayNew + "service=query_timestamp&partner=" + partner;

        SAXReader reader = new SAXReader();
        Document doc = reader.read(new URL(strUrl).openStream());

		List<Node> nodeList = doc.selectNodes("//alipay/*");

        for (Node node : nodeList) {
            // 截取部分不需要解析的信息
            if (node.getName().equals("is_success") && node.getText().equals("T")) {
                // 判断是否有成功标示
                List<Node> nodeList1 = doc.selectNodes("//response/timestamp/*");
                for (Node node1 : nodeList1) {
                    result.append(node1.getText());
                }
            }
        }
    	}catch(Exception e){}
        return result.toString();
    }
    
    /**
     * 验证消息是否是支付宝发出的合法消息
     * @param params 通知返回来的参数数组
     * @return 验证结果
     */
    public static boolean verify(Map<String, String[]> params) {
    	
    	Map<String,String> mapParams=new HashMap<String,String>();
    	for(String keyStr : params.keySet()){
    		String valueStr="";
    		String[] paramStrs=params.get(keyStr);
    		for(int i=0;i<paramStrs.length;i++){
    			valueStr+=(i==0?"":",")+paramStrs[i];
    		}
        	mapParams.put(keyStr, valueStr);
    	}
    	
        //判断responsetTxt是否为true，isSign是否为true
        //responsetTxt的结果不是true，与服务器设置问题、合作身份者ID、notify_id一分钟失效有关
        //isSign不是true，与安全校验码、请求时的参数格式（如：带自定义参数等）、编码格式有关
    	String responseTxt = "true";
		if(mapParams.get("notify_id") != null) {
			String notify_id = mapParams.get("notify_id");
			responseTxt = verifyResponse(notify_id);
		}
	    String sign = "";
	    if(mapParams.get("sign") != null) {sign = mapParams.get("sign");}
	    boolean isSign = getSignVeryfy(mapParams, sign);

        //写日志记录（若要调试，请取消下面两行注释）
        //String sWord = "responseTxt=" + responseTxt + "\n isSign=" + isSign + "\n 返回回来的参数：" + AlipayCore.createLinkString(params);
	    //AlipayCore.logResult(sWord);

        if (isSign && responseTxt.equals("true")) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 根据反馈回来的信息，生成签名结果
     * @param Params 通知返回来的参数数组
     * @param sign 比对的签名结果
     * @return 生成的签名结果
     */
	private static boolean getSignVeryfy(Map<String, String> Params, String sign) {
    	//过滤空值、sign与sign_type参数
    	Map<String, String> sParaNew = paraFilter(Params);
        //获取待签名字符串
        String preSignStr = createLinkString(sParaNew);
        //获得签名验证结果
        boolean isSign = false;
        if(sign_type.equals("MD5") ) {
        	isSign = MD5.verify(preSignStr, sign, key, input_charset);
        }
        return isSign;
    }
	
	/**
    * 获取远程服务器ATN结果,验证返回URL
    * @param notify_id 通知校验ID
    * @return 服务器ATN结果
    * 验证结果集：
    * invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 
    * true 返回正确信息
    * false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
    */
    private static String verifyResponse(String notify_id) {
        //获取远程服务器ATN结果，验证是否是支付宝服务器发来的请求
        String veryfy_url = HTTPS_VERIFY_URL + "partner=" + partner + "&notify_id=" + notify_id;

        return checkUrl(veryfy_url);
    }
    
    /**
     * 获取远程服务器ATN结果
     * @param urlvalue 指定URL路径地址
     * @return 服务器ATN结果
     * 验证结果集：
     * invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 
     * true 返回正确信息
     * false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
     */
     private static String checkUrl(String urlvalue) {
         String inputLine = "";

         try {
             URL url = new URL(urlvalue);
             HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
             BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection
                 .getInputStream()));
             inputLine = in.readLine().toString();
         } catch (Exception e) {
             e.printStackTrace();
             inputLine = "";
         }

         return inputLine;
     }    
}
