package com.util.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.util.Objects;

/**
 * @Version 1.0
 * @Since JDK1.7
 * @Author HYK
 * @Date 2018年12月6日 15:38:28
 */
@Data
public class Base64DecodeMultipartFile implements MultipartFile {

	private final byte[] imgContent;
	private final String header;

	public Base64DecodeMultipartFile(byte[] imgContent, String header) {
		this.imgContent = imgContent;
		this.header = header.split(";")[0];
	}

	@Override
	public String getName() {
		return System.currentTimeMillis() + Math.random() + "." + header.split("/")[1];
	}

	@Override
	public String getOriginalFilename() {
		return System.currentTimeMillis() + (int) Math.random() * 10000 + "." + header.split("/")[1];
	}

	@Override
	public String getContentType() {
		return header.split(":")[1];
	}

	@Override
	public boolean isEmpty() {
		return imgContent == null || imgContent.length == 0;
	}

	@Override
	public long getSize() {
		return imgContent.length;
	}

	@Override
	public byte[] getBytes() {
		return imgContent;
	}

	@Override
	public InputStream getInputStream() {
		return new ByteArrayInputStream(imgContent);
	}

	@Override
	public void transferTo(File file) throws IOException, IllegalStateException {
		new FileOutputStream(file).write(imgContent);
	}

	public static MultipartFile turnBase64ToMultipartFile(String base64) throws Exception {
		String[] baseStrs = base64.split(",");
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] bytes;
		if (Objects.equals(1, baseStrs.length)) {
			bytes = decoder.decodeBuffer(baseStrs[0]);
		} else {
			bytes = decoder.decodeBuffer(baseStrs[1]);
		}

		for (int i = 0; i < bytes.length; ++i) {
			if (bytes[i] < 0) {
				bytes[i] += 256;
			}
		}
		return new Base64DecodeMultipartFile(bytes, baseStrs[0]);
	}

}
