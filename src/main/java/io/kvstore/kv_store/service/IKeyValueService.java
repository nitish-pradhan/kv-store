package io.kvstore.kv_store.service;

import io.kvstore.kv_store.model.KeyValueRequest;
import io.kvstore.kv_store.model.KeyValueResponse;
import org.rocksdb.RocksDBException;

import java.util.List;
import java.util.Optional;

public interface IKeyValueService {

    void put(KeyValueRequest keyValueRequest);

    Optional<KeyValueResponse> get(String key) throws RocksDBException;

    boolean delete(String key) throws RocksDBException;

    List<KeyValueResponse> getAll();
}
