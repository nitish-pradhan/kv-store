package io.kvstore.kv_store.exception;

public class KvStorePersistenceException extends RuntimeException {

    public KvStorePersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
