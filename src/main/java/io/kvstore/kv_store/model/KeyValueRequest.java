package io.kvstore.kv_store.model;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class KeyValueRequest {

    @NotBlank
    private String key;

    @NotBlank
    private String value;

}
