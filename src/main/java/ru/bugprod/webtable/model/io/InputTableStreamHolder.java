package ru.bugprod.webtable.model.io;

import java.io.InputStream;

public class InputTableStreamHolder implements TableStreamHolder {

    private final InputStream stream;
    private final String tableName;

    public InputTableStreamHolder(InputStream stream, String tableName) {
        this.stream = stream;
        this.tableName = tableName;
    }

    @Override
    public String getTableName() {
        return this.tableName;
    }

    @Override
    public InputStream openStream() {
        return stream;
    }
}
