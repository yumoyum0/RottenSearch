package searchEngine.mapper.cache;

import java.util.function.Consumer;

/**
 * @Description: TODO
 **/
public abstract class AbstractCacheRepository<K,V> {
    /**
     * 取代null放入缓存，用于防止缓存击穿
     * 必须是单例
     */
    protected abstract V nullThing();

    /**
     * 从缓存中取值
     */
    protected abstract V getCache(K key);

    /**
     * 如果缓存中不存在值，获取值的方法（通常是从数据库获取）
     */
    protected abstract V getIfAbsent(K key);

    /**
     * 更新缓存
     */
    protected abstract void updateCache(K key, V value);

    /**
     * 清除缓存
     */
    protected abstract void removeCache(K key);

    /**
     * 对缓存进行 consumer 处理
     */
    protected abstract void processCache(K key, Consumer<V> consumer);
}
