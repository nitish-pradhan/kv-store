package io.kvstore.kv_store.config;

import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class RocksDBConfig {

    static {
        RocksDB.loadLibrary();
    }

    @Value("${kvstore.persistence.path}")
    private String dbPath;

    @Bean
    public RocksDB rocksDB() throws RocksDBException, IOException {
        Files.createDirectories(Paths.get(dbPath).toAbsolutePath().normalize());

        Options options = new Options().setCreateIfMissing(true);
        return RocksDB.open(options, dbPath);
    }
}
