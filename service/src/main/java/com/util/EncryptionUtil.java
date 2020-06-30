package com.util;

import org.apache.commons.codec.digest.DigestUtils;
import sun.misc.BASE64Encoder;

/**
 * @Version 1.0
 * @Since JDK1.8
 * @Author HYK
 * @Date 2018年12月6日 15:37:40
 */
public class EncryptionUtil {

	public static String md5Encoder(String signStr) {
		return DigestUtils.md5Hex(signStr);
	}

	public static String md5Encoder(String content, String key) {
		String signStr = content + key;
		return md5Encoder(signStr);
	}

	public static String sha1Encoder(String signStr) {
		return DigestUtils.sha1Hex(signStr);
	}

	public static String base64Encoder(String str) {
		return new BASE64Encoder().encode(str.getBytes());
	}
}
