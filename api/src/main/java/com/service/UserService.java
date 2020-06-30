package com.service;

import com.form.local.req.FindUserListReqForm;
import com.github.pagehelper.PageInfo;

public interface UserService {

	/**
	 * 根据用户ID获取用户名
	 *
	 * @param id 用户ID
	 * @return 用户名
	 */
	String findUserNameById(String id);

	/**
	 * 获取用户列表
	 *
	 * @param reqForm 查询参数
	 * @return 用户列表
	 */
	PageInfo findList(FindUserListReqForm reqForm);

}
