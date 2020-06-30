package com.form.local.req;

import com.form.local.req.base.BaseReqForm;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @Version 1.0
 * @Since JDK1.8
 * @Author HYK
 * @Date 2018/12/6 0006 16:57
 */
@Data
@ToString(callSuper = true)
public class AddDictReqForm extends BaseReqForm {

	@NotBlank(message = "字典码不能为空")
	private String code;

//	public static Dict turnToDict(AddDictReqForm reqForm) {
//		Dict dict = new Dict();
//		dict.setDictCode(reqForm.getCode());
//		return dict;
//	}

}
