package ru.bugprod.webtable.model.io;

import java.io.InputStream;

public interface TableStreamHolder {

    String getTableName();

    InputStream openStream();

}
