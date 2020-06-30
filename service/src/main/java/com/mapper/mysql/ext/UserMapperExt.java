package com.mapper.mysql.ext;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Version 1.0
 * @Since JDK1.8
 * @Author HYK
 * @Date 2018/12/6 0006 15:42
 */
@Mapper
public interface UserMapperExt {

	/**
	 * 根据用户ID获取用户名
	 *
	 * @param id 用户ID
	 * @return 用户名
	 */
	@Select("SELECT username FROM user WHERE id = #{id}")
	String findUserNameById(@Param("id") String id);

}
