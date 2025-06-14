package io.kvstore.kv_store.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class KvResponse {

    private String statusCode;

    private String statusMessage;
}
