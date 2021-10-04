package ru.bugprod.webtable.repository.mapper;

import ru.bugprod.webtable.model.data.TableColumn;
import ru.bugprod.webtable.model.data.TableFragment;
import tech.tablesaw.api.Table;

import java.util.stream.Collectors;

public class TableFragmentMapper {

    public static TableFragmentMapper INSTANCE = new TableFragmentMapper();

    public TableFragment map(Table table) {
        var columnList = table.columns().stream()
                .map(column -> new TableColumn(column.name(), column.asList()))
                .collect(Collectors.toList());
        return new TableFragment(table.name(), columnList);
    }
}


