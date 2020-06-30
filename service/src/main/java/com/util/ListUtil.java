package com.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Version 1.0
 * @Since JDK1.8
 * @Author HYK
 * @Date 2018年12月6日 15:39:24
 */
public class ListUtil {

	public static boolean isNull(List list) {
		if (Objects.equals(null, list) || Objects.equals(0, list.size())) {
			return true;
		}
		return false;
	}

	public static boolean isNotNull(List list) {
		return !isNull(list);
	}

	public static List format(List list) {
		if (isNull(list)) {
			return new ArrayList();
		}
		return list;
	}

}
