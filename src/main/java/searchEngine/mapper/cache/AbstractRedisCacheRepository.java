package searchEngine.mapper.cache;

import searchEngine.service.RedisService;

import java.util.function.Consumer;

/**
 * @Description: Redis实现缓存
 **/
public abstract class AbstractRedisCacheRepository<K,V> extends AbstractCacheRepository<K,V> {
    private RedisService redisService;
    private Integer expireTime;
    private final String prefix;

    public AbstractRedisCacheRepository(RedisService redisService, Integer expireTime, String prefix) {
        //监测nullThing()定义是否单例，使用assert需要配置VM参数: -ea
        assert nullThing().equals(nullThing()) : "nullThing()实现有误";
        this.redisService = redisService;
        this.expireTime = expireTime;
        this.prefix = prefix;
    }

    @Override
    protected V getCache(K key) {
        String k = prefix + key.toString();
        V cache = (V) redisService.get(k);
        //缓存为空
        if (cache == null) {
            V wait = getIfAbsent(key);
            if (wait == null) {
                redisService.set(k, nullThing(), expireTime);
            } else {
                redisService.set(k, wait, expireTime);
            }
            return wait;
        } else {
            return cache.equals(nullThing()) ? null : cache;
        }
    }

    @Override
    protected void updateCache(K key, V value) {
        if (value == null) {
            value = nullThing();
        }
        redisService.set(prefix + key, value, expireTime);
    }

    @Override
    protected void processCache(K key, Consumer<V> consumer) {
        String k = prefix + key.toString();
        V cache = (V) redisService.get(k);
        if (cache != null && !cache.equals(nullThing())) {
            consumer.accept(cache);
            redisService.set(k, cache, expireTime);
        }
    }

    @Override
    protected void removeCache(K key) {
        redisService.get(prefix + key);
    }
}
