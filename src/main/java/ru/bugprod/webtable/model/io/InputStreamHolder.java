package ru.bugprod.webtable.model.io;

import java.io.InputStream;

public class InputStreamHolder implements StreamHolder {

    private final InputStream stream;

    public InputStreamHolder(InputStream stream) {
        this.stream = stream;
    }

    @Override
    public InputStream openStream() {
        return stream;
    }
}
