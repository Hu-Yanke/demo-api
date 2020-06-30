package com.controller;

import com.common.ErrorCode;
import com.controller.base.BaseController;
import com.form.local.req.AddDictReqForm;
import com.form.local.req.FindUserListReqForm;
import com.form.local.res.BaseResForm;
import com.model.oracle.Dict;
import com.service.DictService;
import com.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Version 1.0
 * @Since JDK1.8
 * @Author HYK
 * @Date 2018/12/6 0006 15:17
 */
@Slf4j
@RestController
@RequestMapping("test")
public class TestController extends BaseController {

	@Resource
	private UserService userService;
	@Resource
	private DictService dictService;

	@RequestMapping("hi")
	public BaseResForm hi() {
		return BaseResForm.success();
	}

	@GetMapping("findUserNameById/{id}")
	public BaseResForm findUserNameById(@PathVariable String id) {
		return BaseResForm.success(userService.findUserNameById(id));
	}

	@GetMapping("findUserList")
	public BaseResForm findUserList(FindUserListReqForm findUserListReqForm) {
		return BaseResForm.success(userService.findList(findUserListReqForm));
	}

	@PostMapping("addDict")
	public BaseResForm addDict(@RequestBody AddDictReqForm reqForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String errMsg = bindingResult.getFieldError().getDefaultMessage();
			return BaseResForm.back(ErrorCode.PARAM_WRONG.code, errMsg);
		}

		dictService.add(reqForm);

		return BaseResForm.success();
	}

}
