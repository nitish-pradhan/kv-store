package io.kvstore.kv_store.persistence;

import io.kvstore.kv_store.exception.KvStorePersistenceException;
import io.kvstore.kv_store.model.KeyValueResponse;
import jakarta.annotation.PreDestroy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rocksdb.FlushOptions;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.rocksdb.RocksIterator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
@Component
@AllArgsConstructor
public class RocksDBStore {

    private RocksDB db;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();


    public void put(String key, String value) {
        lock.writeLock().lock();
        try {
            db.put(key.getBytes(), value.getBytes());
            log.info("Persisted key '{}' successfully", key);
        } catch (RocksDBException e) {
            log.error("Failed to persist key '{}'", key, e);
            throw new KvStorePersistenceException("Error saving data to RocksDB", e);
        } finally {
            lock.writeLock().unlock();
        }
    }

//    public void putBatch(List<KeyValueResponse> entries) {
//        lock.writeLock().lock();
//        try (WriteBatch batch = new WriteBatch(); WriteOptions writeOptions = new WriteOptions()) {
//            for (KeyValueResponse entry : entries) {
//                batch.put(entry.key().getBytes(), entry.value().getBytes());
//            }
//            db.write(writeOptions, batch);
//            log.info("Batch persisted {} entries successfully", entries.size());
//        } catch (RocksDBException e) {
//            log.error("Failed to batch persist entries", e);
//            throw new KvStorePersistenceException("Error writing batch to RocksDB", e);
//        } finally {
//            lock.writeLock().unlock();
//        }
//    }

    public Optional<String> get(String key) {
        lock.readLock().lock();
        try {
            byte[] value = db.get(key.getBytes());
            return Optional.ofNullable(value).map(String::new);
        } catch (RocksDBException e) {
            log.error("Failed to read key '{}'", key, e);
            throw new KvStorePersistenceException("Error reading data from RocksDB", e);
        } finally {
            lock.readLock().unlock();
        }
    }

    public boolean delete(String key) {
        Objects.requireNonNull(key, "Key cannot be null");

        lock.writeLock().lock();
        try {
            // Check if key exists first
            if (!keyExists(key)) {
                log.warn("Delete failed - key '{}' not found", key);
                return false;
            }

            db.delete(key.getBytes());
            log.info("Deleted key '{}' successfully", key);
            return true;
        } catch (RocksDBException e) {
            log.error("Failed to delete key '{}'", key, e);
            throw new KvStorePersistenceException("Error deleting key '" + key + "' from RocksDB", e);
        } finally {
            lock.writeLock().unlock();
        }
    }

    private boolean keyExists(String key) throws RocksDBException {
        // Using get() instead of keyMayExist() for accurate existence check
        byte[] value = db.get(key.getBytes());
        return value != null;
    }

    public List<KeyValueResponse> getAll() {
        lock.readLock().lock();
        List<KeyValueResponse> results = new ArrayList<>();
        try (RocksIterator iterator = db.newIterator()) {
            for (iterator.seekToFirst(); iterator.isValid(); iterator.next()) {
                String key = new String(iterator.key());
                String value = new String(iterator.value());
                results.add(new KeyValueResponse(key, value));
            }
        } catch (Exception e) {
            log.error("Failed to iterate over RocksDB store", e);
            throw new KvStorePersistenceException("Error reading all entries from RocksDB", e);
        } finally {
            lock.readLock().unlock();
        }
        return results;
    }

    /**
     * Graceful shutdown hook that ensures:
     * 1. All pending writes are flushed to disk
     * 2. Database resources are properly closed
     * 3. Prevents data corruption on application exit
     */
    @PreDestroy
    public void close() {
        lock.writeLock().lock();
        try {
            if (db != null) {
                try {
                    // 1. Flush all pending operations
                    db.flush(new FlushOptions().setWaitForFlush(true));

                    // 2. Close database instance
                    db.close();

                    // 3. Optionally call static RocksDB shutdown
                    RocksDB.loadLibrary(); // Ensures native library is loaded
                    // RocksDB.shutdown(); // Only needed if using multiple instances
                } catch (RocksDBException e) {
                    throw new KvStorePersistenceException("Failed to shutdown RocksDB", e);
                } finally {
                    db = null;
                }
            }
        } finally {
            lock.writeLock().unlock();
        }
    }
}
