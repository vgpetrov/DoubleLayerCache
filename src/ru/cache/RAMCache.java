package ru.cache;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Representation of cache in RAM
 *
 * @param <K>
 * @param <V>
 */
class RAMCache<K, V> implements ICache<K, V> {

    private Map<K, V> cache;
    private int size;
    private int maxSize;
	private LinkedList<KV> useList;

    public RAMCache(int maxSize) {
        cache = new HashMap<K, V>();
        useList = new LinkedList<KV>();
        size = 0;
        this.maxSize = maxSize;
    }

    @Override
    public KV putObject(K key, V value) {
        if (size < maxSize) {
            cache.put(key, value);
            useList.add(new KV(key, value));
            size++;
        } else {
            KV keyVal = useList.removeLast();
            cache.remove(keyVal.getKey());
            useList.addFirst(new KV(key, value));
            cache.put(key, value);
            return keyVal;
        }
        return null;
    }

    @Override
    public V getObject(K key) {
        V obj = cache.get(key);
        if (obj != null) {
			KV keyVal = new KV(key, obj);
            useList.remove(keyVal);
            useList.addFirst(keyVal);
            return obj;
        } else {
            return null;
        }
    }

    @Override
    public Integer getCacheSize() {
        return maxSize;
    }

    @Override
    public void clearCache() {
        cache.clear();
        useList.clear();
        size = 0;
    }

    @Override
    public void removeObject(K key) {
        V obj = cache.remove(key);
        useList.remove(new KV(key, obj));
        size--;
    }
}