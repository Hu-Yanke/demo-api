package com.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Version 1.0
 * @Since JDK1.8
 * @Author HYK
 * @Date 2020/6/29 16:11
 * @Describe
 */
@Slf4j
@Component
public class RedisUtil {

	@Resource
	private RedisTemplate redisTemplate;

	/**
	 * 在redis中存放数据(永久存放)
	 *
	 * @param key   redisKey
	 * @param value 存放的value
	 * @return 是否存放成功
	 */
	public boolean set(String key, Object value) {
		try {
			redisTemplate.opsForValue().set(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("缓存放入失败:{}", e.getMessage());
			return false;
		}
	}

	/**
	 * 在redis中存放数据
	 *
	 * @param key        redisKey
	 * @param value      存放的value
	 * @param expireTime 存放的时间(s)
	 * @return 是否存放成功
	 */
	public boolean set(String key, Object value, Long expireTime) {
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("缓存放入失败:{}", e.getMessage());
			return false;
		}
	}

	/**
	 * 从redis中移除某个数据
	 *
	 * @param key 需要移除的redisKey
	 */
	public void remove(String key) {
		if (exists(key)) {
			redisTemplate.delete(key);
		}
	}

	/**
	 * 判断redis中是否有某个数据
	 *
	 * @param key redisKey
	 * @return 是否存在
	 */
	public boolean exists(String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * 从redis中获取数据
	 *
	 * @param key redisKey
	 * @return redis中存放的数据
	 */
	public Object get(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	/**
	 * 数据自增
	 *
	 * @param key 数据的redisKey
	 * @return 自增后的数量
	 */
	public Long inc(String key) {
		// 自增
		return redisTemplate.opsForValue().increment(key, 1L);
	}

	/**
	 * 插入数据到list列表
	 *
	 * @param key    list列表的redisKey
	 * @param object list列表中的数据
	 * @return 是否插入成功
	 */
	public boolean listInsert(String key, Object object) {
		long length = redisTemplate.opsForList().leftPush(key, object);
		return length > 0;
	}

	/**
	 * 插入数据到map中
	 *
	 * @param redisKey  map的redisKey
	 * @param hashKey   map中的key
	 * @param hashValue map中的value
	 * @return 是否插入成功
	 */
	public boolean mapInsert(String redisKey, String hashKey, Object hashValue) {
		redisTemplate.opsForHash().put(redisKey, hashKey, hashValue);
		return true;
	}

	/**
	 * 从map中获取数据
	 *
	 * @param redisKey map的redisKey
	 * @param hashKey  map中的key
	 * @return map中的value
	 */
	public Object findFromMapData(String redisKey, String hashKey) {
		return mapIsExist(redisKey, hashKey) ? redisTemplate.opsForHash().get(redisKey, hashKey) : null;
	}

	/**
	 * 判断map中是否有相应key
	 *
	 * @param redisKey map的redisKey
	 * @param hashKey  map中的key
	 * @return 是否有相应的key
	 */
	public boolean mapIsExist(String redisKey, String hashKey) {
		return redisTemplate.opsForHash().hasKey(redisKey, hashKey);
	}

	/**
	 * 从map中移除key
	 *
	 * @param redisKey map的redisKey
	 * @param hashKey  map中的key
	 */
	public void removeMapData(String redisKey, String hashKey) {
		redisTemplate.opsForHash().delete(redisKey, hashKey);
	}

	/**
	 * 获取map中的所有数据
	 *
	 * @param redisKey map的redisKey
	 * @return map中的所有数据
	 */
	public JSONObject findAllMapData(String redisKey) {
		JSONObject jsonObject = new JSONObject();

		Set<String> keySet = redisTemplate.opsForHash().keys(redisKey);

		for (String s : keySet) {
			jsonObject.put(s, findFromMapData(redisKey, s));
		}

		return jsonObject;
	}

	/**
	 * 获取list数据
	 *
	 * @param key list列表的redisKey
	 * @return list列表
	 */
	public JSONArray findAllListData(String key) {
		if (!exists(key)) {
			return null;
		}
		try {
			Object object = redisTemplate.opsForList().range(key, 0, -1);
			return JSONArray.parseArray(JSONArray.toJSONString(object));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 从list列表中移除某个数据
	 *
	 * @param key   list列表的redisKey
	 * @param value 需要移除的数据
	 */
	public void removeListData(String key, String value) {
		redisTemplate.opsForList().remove(key, 0L, value);
	}

}
