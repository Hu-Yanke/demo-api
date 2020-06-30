package com.util;

import com.alibaba.fastjson.JSONObject;

import java.util.*;

/**
 * @Version 1.0
 * @Since JDK1.8
 * @Author HYK
 * @Date 2018年12月6日 15:39:40
 */
public class StringUtil {

	public static boolean isNull(String string) {
		return Objects.equals(null, string) || Objects.equals("", string);
	}

	public static boolean isNotNull(String string){
		return !isNull(string);
	}

	public static String getUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static String getParamFromJson(JSONObject jsonObject) {
		StringBuilder stringBuilder = new StringBuilder("?");

		Set<String> ketSet = jsonObject.keySet();

		if (ketSet.isEmpty()) {
			return "";
		}

		List<String> keyList = new ArrayList<>(ketSet);
		keyList.forEach(key -> {
			String value = jsonObject.getString(key);
			stringBuilder.append(key + "=" + value + "&");
		});

		return stringBuilder.substring(0, stringBuilder.length() - 1);
	}

}
