package com.form.local.req;

import com.form.local.req.base.PageReqForm;
import lombok.Data;
import lombok.ToString;

/**
 * @Version 1.0
 * @Since JDK1.8
 * @Author HYK
 * @Date 2018/12/6 0006 16:21
 */
@Data
@ToString(callSuper = true)
public class FindUserListReqForm extends PageReqForm {

	private Long idQuery;

}
