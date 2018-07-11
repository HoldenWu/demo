package com.demo;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import sun.misc.BASE64Decoder;

public class EncryptUtils {
	
	public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";
	/**
	 * @author whd
	 * @date 2016年12月15日下午5:35:58
	 * @description 默认数据三重加密
	 */
	public static String getThreeEncry(String key, String str) throws UnsupportedEncodingException{
		StringBuffer sb = new StringBuffer();
		sb.append(URLEncoder.encode(encodeBase64(encodeDes(key, str)), "UTF-8"));
		return sb.toString();
	}
	
	/**
	 * DES算法，加密
	 *
	 * @param data 待加密字符串
	 * @param key  加密私钥，长度不能够小于8位
	 * @return 加密后的字节数组，一般结合Base64编码使用
	 * @throws InvalidAlgorithmParameterException
	 * @throws Exception
	 */
	public static String encodeDes(String key,String data) {
		if(data == null)
			return null;
		try{
			DESKeySpec dks = new DESKeySpec(key.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			//key的长度不能够小于8位字节
			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
			IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
			AlgorithmParameterSpec paramSpec = iv;
			cipher.init(Cipher.ENCRYPT_MODE, secretKey,paramSpec);
			byte[] bytes = cipher.doFinal(data.getBytes());
			return byte2hex(bytes);
		}catch(Exception e){
			e.printStackTrace();
			return data;
		}
	}
	
	/**
	 * @author Zerml
	 * @date 2016年12月13日下午3:14:53
	 * @description Base64常规编码
	 */
	public static String encodeBase64(String s) {
		if (s == null) return null;
		return (new sun.misc.BASE64Encoder()).encode(s.getBytes());
	}
	
	
	/**
	 * 二行制转字符串
	 * @param b
	 * @return
	 */
	private static String byte2hex(byte[] b) {
		StringBuilder hs = new StringBuilder();
		String stmp;
		for (int n = 0; b!=null && n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0XFF);
			if (stmp.length() == 1)
				hs.append('0');
			hs.append(stmp);
		}
		return hs.toString().toUpperCase();
	}
	
	
	/**
	 * 解密算法
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getDeThreeEncry(String key, String str) throws UnsupportedEncodingException{
		StringBuffer sb = new StringBuffer();
		sb.append(decodeDes(key, decodeBase64(URLDecoder.decode(str, "UTF-8"))));
		return sb.toString();
	}
	
	/**
	 * DES算法，解密
	 *
	 * @param data 待解密字符串
	 * @param key  解密私钥，长度不能够小于8位
	 * @return 解密后的字节数组
	 * @throws Exception 异常
	 */
	public static String decodeDes(String key,String data) {
		if(data == null)
			return null;
		try {
			DESKeySpec dks = new DESKeySpec(key.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			//key的长度不能够小于8位字节
			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
			IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
			AlgorithmParameterSpec paramSpec = iv;
			cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
			return new String(cipher.doFinal(hex2byte(data.getBytes())));
		} catch (Exception e){
			e.printStackTrace();
			return data;
		}
	}
	
	
	/**
	 * @author 
	 * @date 2016年12月13日下午3:15:07
	 * @description Base64常规解码
	 */
	public static String decodeBase64(String s) {
		if (s == null) return null;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(s);
			return new String(b);
		} catch (Exception e) {
			return null;
		}
	}
	
	
	private static byte[] hex2byte(byte[] b) {
		if((b.length%2)!=0)
			throw new IllegalArgumentException();
		byte[] b2 = new byte[b.length/2];
		for (int n = 0; n < b.length; n+=2) {
			String item = new String(b,n,2);
			b2[n/2] = (byte)Integer.parseInt(item,16);
		}
		return b2;
	}


}
