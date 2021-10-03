package ru.bugprod.webtable.model.io;

import org.apache.commons.fileupload.FileItemStream;
import ru.bugprod.webtable.model.exception.FileIOException;

import java.io.IOException;
import java.io.InputStream;

public class FileItemTableStreamHolder implements TableStreamHolder {

    private final FileItemStream itemStream;
    private final String tableName;

    public FileItemTableStreamHolder(FileItemStream itemStream, String tableName) {
        this.itemStream = itemStream;
        this.tableName = tableName;
    }

    @Override
    public String getTableName() {
        return this.tableName;
    }

    @Override
    public InputStream openStream() {
        try {
            return itemStream.openStream();
        } catch (IOException e) {
            throw new FileIOException(e.getMessage(), e);
        }
    }
}
