package com.form.local.req.base;

import lombok.Data;

import java.util.Objects;

/**
 * @Version 1.0
 * @Since JDK1.8
 * @Author HYK
 * @Date 2018/12/6 0006 16:18
 */
@Data
public class PageReqForm {

	private Integer pageNum;

	private Integer pageSize;

	public Integer getPageNum() {
		return Objects.equals(null, pageNum) ? 1 : pageNum;
	}

	public Integer getPageSize() {
		return Objects.equals(null, pageSize) ? 10 : pageSize;
	}
}
