package ru.bugprod.webtable.model.data;

import lombok.Data;

import java.util.List;

@Data
public class Column {
    private final String key;
    private final List<?> values;
}
