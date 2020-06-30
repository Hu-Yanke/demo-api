package com.util;

import com.github.tobato.fastdfs.domain.MataData;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * @Version 1.0
 * @Since JDK1.8
 * @Author HYK
 * @Date 2018/12/7 0007 16:33
 */
@Slf4j
@Component
public class FastDfsUtil {

	@Resource
	private FastFileStorageClient fastFileStorageClient;

	private static final String MATA_DATA_KEY_AUTHOR = "author";
	private static final String MATA_DATA_KEY_DESCRIPTION = "description";

	public StorePath upload(String base64, String author, String description) {
		MultipartFile file = FileUtil.turnBass64ToMultipartFile(base64);
		return upload(file, author, description);
	}

	public StorePath upload(MultipartFile file, String author, String description) {
		log.debug("文件上传开始---> ");
		// 设置文件信息
		Set<MataData> mataDataSet = new HashSet<>();
		mataDataSet.add(new MataData(MATA_DATA_KEY_AUTHOR, author));
		mataDataSet.add(new MataData(MATA_DATA_KEY_DESCRIPTION, description));

		String originalFileName = file.getOriginalFilename();
		String fileSuffix = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
		try {
			StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), fileSuffix, mataDataSet);
			log.info("文件上传结束---> 文件全地址:{},文件分组:{},文件分组地址:{}", storePath.getFullPath(), storePath.getGroup(), storePath.getPath());
			return storePath;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("文件上传失败:{}", e.getMessage());
			return null;
		}
	}

	public void delete(String path) {
		fastFileStorageClient.deleteFile(path);
	}

	public void delete(String group, String path) {
		fastFileStorageClient.deleteFile(group, path);
	}

	public byte[] getFile(String group, String path) {
		return fastFileStorageClient.downloadFile(group, path, new DownloadByteArray());
	}

	public String getFileBase64(String group, String path) {
		byte[] bytes = getFile(group, path);
		return FileUtil.turnBytesToBase64(bytes);
	}

}
