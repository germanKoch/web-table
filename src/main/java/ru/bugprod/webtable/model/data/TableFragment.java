package ru.bugprod.webtable.model.data;

import lombok.Data;

import java.util.List;

@Data
public class TableFragment {
    private final String name;
    private final List<TableColumn> columns;
}
