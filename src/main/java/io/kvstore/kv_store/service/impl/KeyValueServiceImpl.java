package io.kvstore.kv_store.service.impl;

import io.kvstore.kv_store.exception.KvStorePersistenceException;
import io.kvstore.kv_store.exception.ResourceNotFoundException;
import io.kvstore.kv_store.model.KeyValueRequest;
import io.kvstore.kv_store.model.KeyValueResponse;
import io.kvstore.kv_store.persistence.RocksDBStore;
import io.kvstore.kv_store.service.IKeyValueService;
import io.kvstore.kv_store.store.InMemoryStore;
import jakarta.annotation.PreDestroy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rocksdb.RocksDBException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class KeyValueServiceImpl implements IKeyValueService {

    private final RocksDBStore rocksDBStore;

    @Override
    public void put(KeyValueRequest keyValueRequest) {
        log.debug("Saving key: {}, value: {}", keyValueRequest.getKey(), keyValueRequest.getValue());
        rocksDBStore.put(keyValueRequest.getKey(), keyValueRequest.getValue());
    }

    @Override
    public Optional<KeyValueResponse> get(String key) {
        log.debug("Retrieving key: {}", key);
        String value = rocksDBStore.get(key)
                .orElseThrow(() -> new ResourceNotFoundException(key));
        return Optional.of(new KeyValueResponse(key, value));
    }

    @Override
    public boolean delete(String key) {
        log.debug("Deleting key: {}", key);
        return rocksDBStore.delete(key);
    }

    @Override
    public List<KeyValueResponse> getAll() {
        return rocksDBStore.getAll()
                .stream()
                .map(entry -> new KeyValueResponse(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

}
