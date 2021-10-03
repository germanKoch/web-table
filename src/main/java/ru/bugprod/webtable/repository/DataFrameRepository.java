package ru.bugprod.webtable.repository;

import org.springframework.stereotype.Repository;
import ru.bugprod.webtable.model.data.TableFragment;
import ru.bugprod.webtable.model.exception.FileIOException;
import ru.bugprod.webtable.model.exception.TableNotFoundException;
import ru.bugprod.webtable.model.io.InputTableStreamHolder;
import ru.bugprod.webtable.model.io.TableStreamHolder;
import tech.tablesaw.api.Table;
import tech.tablesaw.selection.Selection;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Repository
public class DataFrameRepository {

    private final Map<String, Table> data;

    public DataFrameRepository() {
        data = new HashMap<>();
    }

    public void importData(String key, TableStreamHolder tableStreamHolder) {
        try(var stream = tableStreamHolder.openStream()) {
            var table = Table.read().csv(stream, tableStreamHolder.getTableName());
            data.put(key, table);
        } catch (IOException e) {
            throw new FileIOException(e.getMessage(), e);
        }
    }

    public TableStreamHolder exportData(String key) {
        checkTable(key);
        var table = data.get(key);
        var name = table.name();
        var in = new PipedInputStream();
        new Thread(() -> {
            try (var out = new PipedOutputStream(in)) {
                table.write().csv(out);
                data.remove(key);
            } catch (IOException e) {
                throw new FileIOException(e.getMessage(), e);
            }
        }).start();
        return new InputTableStreamHolder(in, name);
    }

    public TableFragment getBetween(String key, int offset, int limit) {
        checkTable(key);
        var table = data.get(key);
        var selection = Selection.withRange(offset,limit);
        var rows = table.where(selection);
        return null;
    }

    public void exprUseCase(String key, String expr) {

    }

    private void checkTable(String key) {
        if (!data.containsKey(key)) {
           throw new TableNotFoundException("Table not found");
        }
    }
}
