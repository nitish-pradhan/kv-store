package io.kvstore.kv_store.controller;


import io.kvstore.kv_store.constants.KvStoreConstants;
import io.kvstore.kv_store.model.KeyValueRequest;
import io.kvstore.kv_store.model.KeyValueResponse;
import io.kvstore.kv_store.model.KvResponse;
import io.kvstore.kv_store.service.IKeyValueService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rocksdb.RocksDBException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/kvstore", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
@Slf4j
public class KvStoreController {

    private final IKeyValueService iKeyValueService;


    @PostMapping("/put")
    public ResponseEntity<KvResponse> put(@RequestBody @Valid KeyValueRequest keyValueRequest) {
        log.info("Received request to save: {}", keyValueRequest);
        iKeyValueService.put(keyValueRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new KvResponse(KvStoreConstants.STATUS_201, KvStoreConstants.MESSAGE_201));
    }

    @GetMapping("/get")
    public ResponseEntity<KeyValueResponse> get(@RequestParam String key) throws RocksDBException {
        log.info("Received request to get key: {}", key);
        KeyValueResponse response = iKeyValueService.get(key).get(); // safe because exception is thrown if not found
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @DeleteMapping("/delete")
    public ResponseEntity<KvResponse> delete(@RequestParam String key) throws RocksDBException {
        log.info("Received request to delete key: {}", key);
        boolean isDeleted = iKeyValueService.delete(key);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new KvResponse(KvStoreConstants.STATUS_200, KvStoreConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new KvResponse(KvStoreConstants.STATUS_417, KvStoreConstants.MESSAGE_417_DELETE));
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<KeyValueResponse>> getAll() {
        log.info("Fetching all key-value pairs");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(iKeyValueService.getAll());
    }


}
