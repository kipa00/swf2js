package com.kipa.swf2js.types.shape;

import java.io.IOException;
import java.io.InputStream;

public abstract class Color {
    public abstract void read(InputStream is) throws IOException;
    public abstract String toCSSString();
}
