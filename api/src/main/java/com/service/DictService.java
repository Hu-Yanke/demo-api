package com.service;

import com.form.local.req.AddDictReqForm;

public interface DictService {

	/**
	 * 添加字典
	 *
	 * @param dict 字典信息
	 * @return 字典ID
	 */
	String add(AddDictReqForm reqForm);

}
