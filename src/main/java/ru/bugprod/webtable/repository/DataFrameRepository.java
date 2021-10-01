package ru.bugprod.webtable.repository;

import org.springframework.stereotype.Repository;
import ru.bugprod.webtable.model.exception.FileIOException;
import ru.bugprod.webtable.model.io.InputStreamHolder;
import ru.bugprod.webtable.model.io.StreamHolder;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.HashMap;
import java.util.Map;

@Repository
public class DataFrameRepository {

    private final Map<String, Table> data;

    public DataFrameRepository() {
        data = new HashMap<>();
    }

    public void importData(String key, String tableName, StreamHolder streamHolder) {
        try {
            var table = Table.read().csv(streamHolder.openStream(), tableName);
            data.put(key, table);
        } catch (IOException e) {
            throw new FileIOException(e.getMessage(), e);
        }
    }

    public StreamHolder exportData(String key) {
        var in = new PipedInputStream();
        new Thread(() -> {
            try (var out = new PipedOutputStream(in)) {
                data.get(key).write().csv(out);
                data.remove(key);
            } catch (IOException e) {
                throw new FileIOException(e.getMessage(), e);
            }
        }).start();
        return new InputStreamHolder(in);
    }
}
