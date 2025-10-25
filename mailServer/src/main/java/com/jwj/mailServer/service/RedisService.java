package com.jwj.mailServer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

	private final RedisTemplate<String, String> redisTemplate;
	private final ValueOperations<String, String> valueOperations;

	public RedisService(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
		this.valueOperations = redisTemplate.opsForValue();
	}

	// Redis에 값을 저장하는 메서드
	public void setValue(String key, String value, Duration duration) {
		valueOperations.set(key, value);
	}

	// Redis에 값을 저장하고 만료시간을 지정하는 메서드
	public void setValueWithExpiration(String key, String value, long duration) {
		valueOperations.set(key, value, Duration.ofSeconds(duration));
	}

	// Redis에서 값을 조회하는 메서드
	public String getValue(String key) {
		return valueOperations.get(key);
	}

	// Redis에서 값을 삭제하는 메서드
	public boolean deleteValue(String key) {
		return Boolean.TRUE.equals(redisTemplate.delete(key));
	}

	// Redis에서 키 존재 여부를 확인하는 메서드
	public boolean hasKey(String key) {
		return Boolean.TRUE.equals(redisTemplate.hasKey(key));
	}

	// Redis에서 키 만료 시간을 지정하는 메서드
	public boolean setExpire(String key, long timeout) {
		return Boolean.TRUE.equals(redisTemplate.expire(key, timeout, TimeUnit.SECONDS));
	}
}
