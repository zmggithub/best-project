package com.zmgab.springbootshiro.shiro;

import com.zmgab.springbootshiro.util.ApplicationContextUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Collection;
import java.util.Set;

/**
 * 自定义redis缓存的实现
 * @author: zmg
 */
public class RedisCache<k,v> implements Cache<k,v> {

    private String cacheName;

    public RedisCache(String cacheName) {
        this.cacheName = cacheName;
    }

    @Override
    public v get(k k) throws CacheException {
        System.out.println("get key k: " + k);
        return (v) this.getRedisTemplate().opsForHash().get(this.cacheName, k.toString());
    }

    @Override
    public v put(k k, v v) throws CacheException {
        System.out.println("put key k: " + k);
        System.out.println("put key v: " + v);
        this.getRedisTemplate().opsForHash().put(this.cacheName, k.toString(), v);
        return null;
    }

    @Override
    public v remove(k k) throws CacheException {
        System.out.println("remove key k: " + k);
        return (v) this.getRedisTemplate().opsForHash().delete(this.cacheName,k.toString());
    }

    @Override
    public void clear() throws CacheException {
        System.out.println("clear key" );
        this.getRedisTemplate().delete(this.cacheName);
    }

    @Override
    public int size() {
        System.out.println("size key" );
        return this.getRedisTemplate().opsForHash().size(this.cacheName).intValue();
    }

    @Override
    public Set<k> keys() {

        return getRedisTemplate().opsForHash().keys(this.cacheName);
    }

    @Override
    public Collection<v> values() {
        return getRedisTemplate().opsForHash().values(this.cacheName);
    }

    RedisTemplate getRedisTemplate() {
        RedisTemplate redis = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
        redis.setKeySerializer(new StringRedisSerializer());
        redis.setHashKeySerializer(new StringRedisSerializer());
        return redis;
    }
}
