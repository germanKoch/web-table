package ru.bugprod.webtable.model.io;

import org.apache.commons.fileupload.FileItemStream;
import ru.bugprod.webtable.model.exception.FileIOException;

import java.io.IOException;
import java.io.InputStream;

public class FileItemStreamHolder implements StreamHolder {

    private final FileItemStream itemStream;

    public FileItemStreamHolder(FileItemStream itemStream) {
        this.itemStream = itemStream;
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
