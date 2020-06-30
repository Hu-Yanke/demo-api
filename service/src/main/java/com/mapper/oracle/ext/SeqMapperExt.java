package com.mapper.oracle.ext;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Version 1.0
 * @Since JDK1.8
 * @Author HYK
 * @Date 2018/12/6 0006 16:47
 */
@Mapper
public interface SeqMapperExt {

	/**
	 * 获取序列
	 *
	 * @param seq    序列名称
	 * @param length 序列长度(位数不够前面补0)
	 * @return 序列号
	 */
	@Select("SELECT lpad(${seq}.nextval,${length},0) AS id FROM DUAL")
	String getSeq(@Param("seq") String seq, @Param("length") int length);

}
