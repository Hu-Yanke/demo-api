package com.controller;

import com.common.ErrorCode;
import com.controller.base.BaseController;
import com.form.local.res.BaseResForm;
import com.github.tobato.fastdfs.exception.FdfsServerException;
import com.util.FastDfsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

/**
 * @Version 1.0
 * @Since JDK1.8
 * @Author HYK
 * @Date 2018/12/7 0007 16:50
 */
@Slf4j
@RestController
@RequestMapping("photo")
public class PhotoController extends BaseController {

	@Resource
	private FastDfsUtil fastDfsUtil;

	@GetMapping("getStream/{group}/{path}")
	public BaseResForm getStream(@PathVariable String group, @PathVariable String path, HttpServletResponse response) {
		try {
			byte[] bytes = fastDfsUtil.getFile(group, path);
			OutputStream stream = response.getOutputStream();
			stream.write(bytes);
			stream.flush();
			stream.close();
		} catch (FdfsServerException e) {
			e.printStackTrace();
			log.error("文件服务器异常:{}", e.getMessage());
			if (Objects.equals(2, e.getErrorCode())) {
				return BaseResForm.back(ErrorCode.PHOTO_IS_NOT_IN_FAST_DFS);
			} else {
				return BaseResForm.back(ErrorCode.FAST_DFS_ERROR);
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.error("获取文件IO异常:{}", e.getMessage());
			return BaseResForm.back(ErrorCode.PHOTO_IS_ERROR);
		}

		return BaseResForm.success();
	}

}
