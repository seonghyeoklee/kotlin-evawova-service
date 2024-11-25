package com.evawova.repository

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class RedisService(
    private val redisTemplate: RedisTemplate<String, String>,
) {
    fun save(
        key: String,
        value: String,
    ) {
        redisTemplate.opsForValue().set(key, value)
    }

    /**
     * 데이터 저장 (TTL 설정)
     */
    fun saveWithTTL(
        key: String,
        value: String,
        timeout: Long,
        unit: TimeUnit,
    ) {
        redisTemplate.opsForValue().set(key, value, timeout, unit)
    }

    /**
     * 데이터 조회
     */
    fun find(key: String): String? = redisTemplate.opsForValue().get(key)

    /**
     * 데이터 삭제
     */
    fun delete(key: String) {
        redisTemplate.delete(key)
    }

    /**
     * 키 존재 여부 확인
     */
    fun exists(key: String): Boolean = java.lang.Boolean.TRUE == redisTemplate.hasKey(key)
}
