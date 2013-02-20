package ru.cache;

public class DoubleLayerCache<K, V> implements ICache<K, V> {

    private ICache<K, V> ramCache;
    private ICache<K, V> diskCache;

    public DoubleLayerCache(Integer ramCacheSize, Integer diskCacheSize, String dirName) {
        ramCache = new RAMCache<K, V>(ramCacheSize);
        diskCache = new DiskCache<K, V>(diskCacheSize, dirName);
    }

    @Override
    public synchronized KV putObject(K key, V value) {
        KV obj = ramCache.putObject(key, value);
        if (obj != null) {
            diskCache.putObject((K) obj.getKey(), (V) obj.getVal());
        }
        return null;
    }

    @Override
    public synchronized V getObject(K key) {
        V obj = ramCache.getObject(key);
        if (obj != null) {
            return obj;
        } else {
            V val = diskCache.getObject(key);
            diskCache.removeObject(key);
            if (val != null) {
                this.putObject(key, val);
            }
            return val;
        }
    }

    @Override
    public Integer getCacheSize() {
        return ramCache.getCacheSize() + diskCache.getCacheSize();
    }

    @Override
    public void clearCache() {
        ramCache.clearCache();
        diskCache.clearCache();
    }

    @Override
    public synchronized void removeObject(K key) {
        ramCache.removeObject(key);
        diskCache.removeObject(key);
    }
}
