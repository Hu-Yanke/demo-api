package com.http.config;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Version 1.0
 * @Since JDK1.8
 * @Author HYK
 * @Date 2018年12月6日 15:31:54
 */
public class MyMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
	public MyMappingJackson2HttpMessageConverter() {
		List<MediaType> mediaTypes = new ArrayList<>();
		//加入text/html类型的支持
		mediaTypes.add(MediaType.TEXT_PLAIN);
		mediaTypes.add(MediaType.TEXT_HTML);
		mediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
		setSupportedMediaTypes(mediaTypes);
	}
}
