package io.kvstore.kv_store.store;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryStore {

    // Thread-safe in-memory key-value map
    private final Map<String, String> store = new ConcurrentHashMap<>();

    // Create or update a key
    public void put(String key, String value) {
        store.put(key, value);
    }

    // Retrieve a value by key
    public Optional<String> get(String key) {
        return Optional.ofNullable(store.get(key));
    }

    // Delete a key
    public boolean delete(String key) {
        return store.remove(key) != null;
    }

    // List all keys (or you can return full map)
    public Set<Map.Entry<String, String>> getAll() {
        return store.entrySet();
    }
}
