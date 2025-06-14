package io.kvstore.kv_store.model;


import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class KeyValueRequest {

    @NotEmpty(message = "Key can not be a null or empty")
    private String key;

    @NotEmpty(message = "Key can not be a null or empty")
    private String value;

}
