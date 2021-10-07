package ru.bugprod.webtable.repository;

import org.springframework.stereotype.Repository;
import ru.bugprod.webtable.model.data.TableFragment;
import ru.bugprod.webtable.model.exception.FileIOException;
import ru.bugprod.webtable.model.exception.TableNotFoundException;
import ru.bugprod.webtable.model.io.InputTableStreamHolder;
import ru.bugprod.webtable.model.io.TableStreamHolder;
import ru.bugprod.webtable.repository.mapper.TableFragmentMapper;
import ru.bugprod.webtable.repository.service.calculator.ExpressionCalculator;
import ru.bugprod.webtable.repository.service.filter.ConditionExpressionCalculator;
import tech.tablesaw.api.DateColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.io.csv.CsvReadOptions;
import tech.tablesaw.selection.Selection;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Repository
public class DataFrameRepository {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

    private final Map<String, Table> data;
    private final TableFragmentMapper mapper = TableFragmentMapper.INSTANCE;

    public DataFrameRepository() {
        data = new HashMap<>();
    }

    public void importData(String key, TableStreamHolder tableStreamHolder) {
        try (var stream = tableStreamHolder.openStream()) {
            var table = Table.read()
                    .csv(CsvReadOptions
                            .builder(stream)
                            .tableName(tableStreamHolder.getTableName())
                            .dateFormat(DateTimeFormatter.ofPattern(DATE_FORMAT))
                            .dateTimeFormat(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT))
                            .build()
                    );
            table.columns().forEach(column -> column.setName(changeColumnName(column.name())));
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
        var maxRange = Math.min(offset + limit, table.rowCount());
        var selection = Selection.withRange(offset, maxRange);
        var tableFragment = table.where(selection);
        return mapper.map(tableFragment);
    }

    public void computeAndSave(String key, String columnName, String expr) {
        checkTable(key);
        var table = data.get(key);
        var result = ExpressionCalculator.executeExpression(table, expr);
        result.setName(columnName);
        table.addColumns(result);
    }

    public void filterByCondition(String key, String condition) {
        checkTable(key);
        var table = data.get(key);
        var selection = ConditionExpressionCalculator.getCondition(table, condition);
        var tableFragment = table.where(selection);
        data.put(key, tableFragment);
    }

    private void checkTable(String key) {
        if (!data.containsKey(key)) {
            throw new TableNotFoundException("Table not found");
        }
    }

    private String changeColumnName(String name) {
        return name
                .split(" ")[0]
                .toLowerCase()
                .replaceAll("[^a-z1-9]", "_");
    }
}
