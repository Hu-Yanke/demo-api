package com.http;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.http.config.MyMappingJackson2HttpMessageConverter;
import com.http.config.SslClientHttpRequestFactory;
import com.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Version 1.0
 * @Since JDK1.8
 * @Author HYK
 * @Company bangsun
 * @Date 2018年11月22日 11:12:07
 */
@Slf4j
@Component
public class BaseHttp {

	/**
	 * 连接池的最大连接数
	 */
	private static final int MAX_TOTAL_CONNECT = 0;
	/**
	 * 单个主机的最大连接数
	 */
	private static final int MAX_CONNECT_PER_ROUTE = 200;
	/**
	 * 连接超时时间
	 */
	private static final int CONNECT_TIMEOUT = 2000;
	/**
	 * 响应超时时间
	 */
	private static final int READ_TIMEOUT = 60000;

	public JSONObject doPost(String url, JSONObject sendJson) {
		return doPost(url, sendJson, new HttpHeaders());
	}

	public JSONObject doPost(String url, JSONObject sendJson, HttpHeaders httpHeaders) {
		log.info("发送http  POST请求-----");
		log.info("地址:{},内容:{},请求头:{}", url, sendJson, httpHeaders.toString());
		JSONObject result = null;
		long startTime = System.currentTimeMillis();
		try {
			RestTemplate restTemplate = getRestTemplate();
			HttpEntity httpEntity = new HttpEntity(sendJson, httpHeaders);
			result = restTemplate.postForObject(url, httpEntity, JSONObject.class);

			long dealTime = System.currentTimeMillis() - startTime;
			log.info("http请求结束-----交互时间:{}ms,返回值:{}", dealTime, result);
		} catch (Exception e) {
			log.error("网络请求失败:" + e.getMessage());
		}
		return result;
	}

	public JSONObject doGet(String url, JSONObject sendJson) {
		return doGet(url, sendJson, new HttpHeaders());
	}

	public JSONObject doGet(String url, JSONObject sendJson, HttpHeaders httpHeaders) {
		log.info("发送http  GET请求-----");
		log.info("地址:{},内容:{},请求头:{}", url, sendJson, httpHeaders.toString());

		JSONObject result = null;
		long startTime = System.currentTimeMillis();

		String sendParam = StringUtil.getParamFromJson(sendJson);

		try {
			RestTemplate restTemplate = getRestTemplate();

			HttpEntity httpEntity = new HttpEntity(httpHeaders);

			RequestCallback requestCallback = restTemplate.httpEntityCallback(httpEntity, JSONObject.class);
			ResponseExtractor<ResponseEntity<JSONObject>> responseExtractor = restTemplate.responseEntityExtractor(JSONObject.class);

			result = restTemplate.execute(url + sendParam, HttpMethod.GET, requestCallback, responseExtractor).getBody();

			long dealTime = System.currentTimeMillis() - startTime;
			log.info("http请求结束-----交互时间:{}ms,返回值:{}", dealTime, result);
		} catch (Exception e) {
			log.error("网络请求失败:" + e.getMessage());
		}
		return result;
	}

	public RestTemplate getRestTemplate() {
		RestTemplate restTemplate = new RestTemplate(this.createFactory());
		List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();

		//重新设置StringHttpMessageConverter字符集为UTF-8，解决中文乱码问题
		HttpMessageConverter<?> converterTarget = null;
		for (HttpMessageConverter<?> item : converterList) {
			if (StringHttpMessageConverter.class == item.getClass()) {
				converterTarget = item;
				break;
			}
		}
		if (null != converterTarget) {
			converterList.remove(converterTarget);
		}
		converterList.add(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));

		restTemplate.getMessageConverters().add(new MyMappingJackson2HttpMessageConverter());

		//加入FastJson转换器
		converterList.add(new FastJsonHttpMessageConverter());
		return restTemplate;
	}

	/**
	 * 创建HTTP客户端工厂
	 */
	private ClientHttpRequestFactory createFactory() {
		if (MAX_TOTAL_CONNECT <= 0) {
			SslClientHttpRequestFactory factory = new SslClientHttpRequestFactory();
			factory.setConnectTimeout(CONNECT_TIMEOUT);
			factory.setReadTimeout(READ_TIMEOUT);
			return factory;
		}
		HttpClient httpClient = HttpClientBuilder.create().setMaxConnTotal(MAX_TOTAL_CONNECT).setMaxConnPerRoute(MAX_CONNECT_PER_ROUTE).build();
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
		factory.setConnectTimeout(CONNECT_TIMEOUT);
		factory.setReadTimeout(READ_TIMEOUT);
		return factory;
	}

}
