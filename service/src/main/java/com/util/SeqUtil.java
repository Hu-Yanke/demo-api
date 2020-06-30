package com.util;

import com.mapper.oracle.ext.SeqMapperExt;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Version 1.0
 * @Since JDK1.8
 * @Author HYK
 * @Date 2018/12/6 0006 16:47
 */
@Component
public class SeqUtil {

	@Resource
	private SeqMapperExt seqMapperExt;

	private static final String SEQ_T_DICT = "SEQ_T_DICT";

	private static final int SEQ_LENGTH_12 = 12;

	public synchronized String getDict() {
		return DateUtil.getSystemDate() + getSeq(SEQ_T_DICT, SEQ_LENGTH_12);
	}

	private String getSeq(String seq, int length) {
		return seqMapperExt.getSeq(seq, length);
	}

}
