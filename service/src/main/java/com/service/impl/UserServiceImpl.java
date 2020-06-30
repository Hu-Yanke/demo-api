package com.service.impl;

import com.form.local.req.FindUserListReqForm;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mapper.mysql.UserMapper;
import com.mapper.mysql.ext.UserMapperExt;
import com.model.mysql.User;
import com.model.mysql.UserExample;
import com.model.mysql.vo.UserVo;
import com.service.UserService;
import com.util.ListUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Version 1.0
 * @Since JDK1.8
 * @Author HYK
 * @Date 2018/12/6 0006 15:45
 */
@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapperExt userMapperExt;
	@Resource
	private UserMapper userMapper;

	@Override
	public String findUserNameById(String id) {
		return userMapperExt.findUserNameById(id);
	}

	@Override
	public PageInfo findList(FindUserListReqForm reqForm) {
		// 拼接查询语句
		UserExample example = new UserExample();
		UserExample.Criteria criteria = example.or();
		if (!Objects.equals(null, reqForm.getIdQuery())) {
			criteria.andIdEqualTo(reqForm.getIdQuery());
		}

		// 调用分页插件进行分页
		PageHelper.startPage(reqForm.getPageNum(), reqForm.getPageSize());
		// 获取分页后的数据(此时取出的是数据库基本数据)
		List<User> userList = userMapper.selectByExample(example);

		// 对数据进行格式化,填充我们想要的数据
		List<UserVo> userVoList = new ArrayList<>();
		if (ListUtil.isNotNull(userList)) {
			userList.forEach(user -> {
				UserVo userVo = UserVo.turnUser(user);

				userVoList.add(userVo);
			});
		}

		// 原list此时带有分页数据,所以以原list进行分页对象创建,然后将list修改为我们格式化之后的list
		PageInfo pageInfo = new PageInfo(userList);
		pageInfo.setList(userVoList);

		return pageInfo;
	}

}
