package com.demo;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.impl.client.DefaultHttpClient;


import net.sf.json.JSONObject;

public class CustomerAuth {
	public static void main(String[] args) throws UnsupportedEncodingException {
		Map<String, String> condition = new HashMap<String, String>();
		DefaultHttpClient httpclient = new DefaultHttpClient();
//		 String url = "http://payapp.midaipay.com/payserver/auth";
		String url = "http://127.0.0.1:8888/payserver/auth";
		// String url =
		// "http://cfit0.daxtech.com.cn:8020/payform/organization_zfb";
//		JSONObject json = new JSONObject();
//		json.put("businessName", "吴厚东");
//		json.put("businessNo", "123456789");
//		json.put("mobile", "17612163161");
//		json.put("accountName", "吴厚东");
//		// json.put("transType", "Z001");
//		json.put("idCard", "342401199308078891");
//		json.put("screenNum", "6214832138898759");
//		json.put("screenName", "招商银行股份有限公司上海长宁支行");
//		json.put("screenNo", "308290003394");
//
//		json.put("mccNum", "5094");
//		json.put("swingCardDebitRate", 0.6);
//		json.put("swingCardCreditRate", 0.6);
//		json.put("settleFee", 2);
//		json.put("swingCardLimit", 0);
//		json.put("organizationId", "123123123");
//		json.put("provinceCode", "2900");
//		json.put("cityCode", "2900");
//		json.put("areaCode", "2904");
//		json.put("merAuto", "pos");
//		json.put("dmMark", "1");
		String req = "{'businessName':'代彤彤','businessNo':'123123123','mobile':'15011358319','accountName':'代彤彤','idCard':'130635199102031768','screenNum':'6217000010084255408','screenName':'中国建设银行北京市分行通州区支行营业部','screenNo':'105100018025','mccNum':'5094','swingCardDebitRate':0.63,'swingCardCreditRate':0.63,'settleFee':1,'swingCardLimit':0,'organizationId':'123123123','provinceCode':'2900','cityCode':'2900','areaCode':'2904','merAuto':'pos','dmMark':'1'}";

		String value = EncryptUtils.getThreeEncry("201612153DSMRAZUHHLJYHMYM5V1",req);
		condition.put("value", value);

		// {"mac":"45353443","sendTime":"20170628153319","transAmt":"2","respDesc":"获取成功","organizationId":"15901101057","transType":"Z001","respCode":"00","sendSeqId":"ZFBTest_0018","imgUrl":"https://qr.alipay.com/bax06140yaqvmvzakudg00da"}

		String resulta = HttpClientUtil.post(httpclient, url, condition);
		System.out.println("resulta :" + resulta);
	}
}
