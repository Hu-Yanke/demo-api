package com.form.local.res;

import com.common.ErrorCode;
//import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @Version 1.0
 * @Since JDK1.8
 * @Author HYK
 * @Date 2018/12/6 0006 15:19
 */
@Data
// 去除值为null的参数
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResForm {

	private String code;

	private String msg;

	private Object data;

	public static BaseResForm success() {
		BaseResForm baseResForm = new BaseResForm();
		baseResForm.setCode(ErrorCode.SUCCESS.code);
		baseResForm.setMsg(ErrorCode.SUCCESS.desc);
		baseResForm.setData(null);
		return baseResForm;
	}

	public static BaseResForm success(Object data) {
		BaseResForm baseResForm = new BaseResForm();
		baseResForm.setCode(ErrorCode.SUCCESS.code);
		baseResForm.setMsg(ErrorCode.SUCCESS.desc);
		baseResForm.setData(data);
		return baseResForm;
	}

	public static BaseResForm back(String code, String msg) {
		BaseResForm baseResForm = new BaseResForm();
		baseResForm.setCode(code);
		baseResForm.setMsg(msg);
		baseResForm.setData(null);
		return baseResForm;
	}

	public static BaseResForm back(ErrorCode errorCode) {
		BaseResForm baseResForm = new BaseResForm();
		baseResForm.setCode(errorCode.getCode());
		baseResForm.setMsg(errorCode.getDesc());
		baseResForm.setData(null);
		return baseResForm;
	}

}
