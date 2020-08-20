package com.kipa.swf2js.file;

import java.io.InputStream;
import java.io.OutputStream;

public abstract class File {
    public abstract void read(InputStream is) throws Exception;
    public abstract void write(OutputStream os) throws Exception;
}
