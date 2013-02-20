package ru.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Representation of cache on Disk
 *
 * @param <K>
 * @param <V>
 */
class DiskCache<K, V> implements ICache<K, V> {

    private final Logger logger = Logger.getLogger(DiskCache.class.getName());

    private int maxSize;
    private int size;
    private File dir;

    public DiskCache(int maxSize, String directoryName) {
        File dir = new File(directoryName);
        this.dir = dir;
        if (!dir.exists()) {
            logger.log(Level.INFO, "Create dir " + directoryName);
            dir.mkdir();
            this.maxSize = maxSize;
        } else {
            logger.log(Level.INFO, "Delete all files from " + directoryName);
            this.clearCache();
            this.maxSize = maxSize;
        }
        size = 0;
    }

    private void writeToFile(K key, V value) {
        if (dir != null) {
            FileOutputStream fos = null;
            ObjectOutputStream oos = null;
            try {
                fos = new FileOutputStream(dir.getAbsolutePath() + File.separator + key.toString());
                oos = new ObjectOutputStream(fos);
                oos.writeObject(value);
            } catch (IOException e) {
                e.printStackTrace();
                logger.log(Level.SEVERE, "IOException " + e.getMessage());
            } finally {
                if (oos != null) {
                    try {
                        oos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        logger.log(Level.SEVERE, "IOException " + e.getMessage());
                    }
                }
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        logger.log(Level.SEVERE, "IOException " + e.getMessage());
                    }
                }
            }
        }
    }

    @Override
    public KV putObject(K key, V value) {
        if (dir == null) {
            return null;
        }
        if (size < maxSize) {
            writeToFile(key, value);
            size++;
        } else {
            long min = (new Date()).getTime();
            File lastModifiedFile = null;
            for (File file : dir.listFiles()) {
                if (min > file.lastModified()) {
                    min = file.lastModified();
                    lastModifiedFile = file;
                }
            }
            lastModifiedFile.delete();
            writeToFile(key, value);
        }
        return null;
    }

    @Override
    public V getObject(K key) {
        if (dir == null) {
            return null;
        }
        FileInputStream fis = null;
        ObjectInputStream oin = null;

        V result = null;
        try {
            fis = new FileInputStream(dir.getAbsolutePath() + File.separator + key.toString());
            oin = new ObjectInputStream(fis);
            result = (V) oin.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            logger.log(Level.SEVERE, "IOException " + e.getMessage() + " " + e.getCause());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            logger.log(Level.SEVERE, "ClassNotFound " + e.getMessage() + " " + e.getCause());
        }

        return result;
    }

    @Override
    public Integer getCacheSize() {
        return maxSize;
    }

    @Override
    public void clearCache() {
        if (dir != null) {
            for (File file : dir.listFiles()) {
                file.delete();
            }
            size = 0;
        }
    }

    @Override
    public void removeObject(K key) {
        if (dir != null) {
            File file = new File(dir.getAbsolutePath() + File.separator + key.toString());
            file.delete();
            size--;
        }
    }
}
