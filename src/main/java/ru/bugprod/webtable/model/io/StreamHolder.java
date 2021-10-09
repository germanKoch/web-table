package ru.bugprod.webtable.model.io;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;

@Data
@RequiredArgsConstructor
public class StreamHolder {

    private final InputStream stream;
    private final String fileName;

    public InputStream openStream() {
        return stream;
    }
}

