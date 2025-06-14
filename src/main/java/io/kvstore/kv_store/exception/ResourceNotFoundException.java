package io.kvstore.kv_store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String fieldName) {
        super(String.format("KvStore doesn't contain input data %s", fieldName));
    }
}
