package ru.cache;

/**
 * ICache interface for cache
 *
 * @param <K>
 * @param <V>
 */
public interface ICache<K, V> {

    /**
     * Put object in cache
     *
     * @param key
     * @param value
     * @return
     */
    public KV putObject(K key, V value);

    /**
     * Get object by key
     *
     * @param key
     * @return
     */
    public V getObject(K key);

    /**
     * Get cache size
     *
     * @return
     */
    public Integer getCacheSize();

    /**
     * Clear cache
     */
    public void clearCache();

    /**
     * Remove object by key
     *
     * @param key
     */
    public void removeObject(K key);
}
