package ru.cache;

/**
 * Holds the key and val
 *
 * @param <K>
 * @param <V>
 */
class KV<K,V> {

    private K key;
    private V val;

    public KV(K key, V val) {
        this.key = key;
        this.val = val;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getVal() {
        return val;
    }

    public void setVal(V val) {
        this.val = val;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KV)) return false;

        KV kv = (KV) o;

        if (key != null ? !key.equals(kv.key) : kv.key != null) return false;
        if (val != null ? !val.equals(kv.val) : kv.val != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (val != null ? val.hashCode() : 0);
        return result;
    }
}
