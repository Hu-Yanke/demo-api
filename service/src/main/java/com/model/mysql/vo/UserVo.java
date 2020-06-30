package com.model.mysql.vo;

import com.model.mysql.User;
import lombok.Data;

/**
 * @Version 1.0
 * @Since JDK1.8
 * @Author HYK
 * @Date 2018/12/6 0006 16:26
 */
@Data
public class UserVo {

	private Long id;

	private String username;

	private String password;


	public static UserVo turnUser(User user){
		UserVo userVo = new UserVo();
		userVo.setId(user.getId());
		userVo.setUsername(user.getUsername());
		userVo.setPassword(user.getPassword());
		return userVo;
	}

}
