package io.kvstore.kv_store.persistence;

import io.kvstore.kv_store.model.KeyValueResponse;
import org.rocksdb.*;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RocksDBStore {

    private final RocksDB db;

    public RocksDBStore(RocksDB db) {
        this.db = db;
    }

    public void put(String key, String value) throws RocksDBException {
        if (key != null && value != null) {
            db.put(key.getBytes(), value.getBytes());
        }
    }

    public Optional<String> get(String key) throws RocksDBException {
        if (key == null) return Optional.empty();
        byte[] value = db.get(key.getBytes());
        return value == null ? Optional.empty() : Optional.of(new String(value));
    }

    public boolean delete(String key) throws RocksDBException {
        if (key == null) return false;
        db.delete(key.getBytes());
        return true;
    }

    public List<KeyValueResponse> getAll() {
        List<KeyValueResponse> result = new ArrayList<>();
        try (RocksIterator iterator = db.newIterator()) {
            for (iterator.seekToFirst(); iterator.isValid(); iterator.next()) {
                String key = new String(iterator.key());
                String value = new String(iterator.value());
                result.add(new KeyValueResponse(key, value));
            }
        }
        return result;
    }
}
