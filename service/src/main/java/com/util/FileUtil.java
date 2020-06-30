package com.util;

import com.util.model.Base64DecodeMultipartFile;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Version 1.0
 * @Since JDK1.8
 * @Author HYK
 * @Date 2018年12月6日 15:39:00
 */
public class FileUtil {

	public static MultipartFile turnBass64ToMultipartFile(String base64) {
		try {
			return Base64DecodeMultipartFile.turnBase64ToMultipartFile(base64);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String turnBytesToBase64(byte[] bytes) {
		return Base64.encodeBase64String(bytes);
	}

	public static String turnWebFileToBase64(String filePath) {
		byte[] bytes = getWebFile(filePath);
		return new BASE64Encoder().encode(bytes);
	}

	public static byte[] getWebFile(String filePath) {
		try {
			URL url = new URL(filePath);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5 * 1000);
			InputStream inputStream = connection.getInputStream();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len;
			while ((len = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, len);
			}
			inputStream.close();
			return outputStream.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
