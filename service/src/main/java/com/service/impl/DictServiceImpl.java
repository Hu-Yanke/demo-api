package com.service.impl;

import com.form.local.req.AddDictReqForm;
import com.mapper.oracle.DictMapper;
import com.model.oracle.Dict;
import com.service.DictService;
import com.util.SeqUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Version 1.0
 * @Since JDK1.8
 * @Author HYK
 * @Date 2018/12/6 0006 16:54
 */
@Service
@Transactional(rollbackFor = Exception.class, transactionManager = "oracleTransactionManager")
public class DictServiceImpl implements DictService {

	@Resource
	private SeqUtil seqUtil;
	@Resource
	private DictMapper dictMapper;

	@Override
	public String add(AddDictReqForm reqForm) {
		// 类转
		Dict dict = new Dict();
		dict.setDictCode(reqForm.getCode());

		String dictId = seqUtil.getDict();
		dict.setDictId(dictId);
		dictMapper.insertSelective(dict);
		return dictId;
	}

}
