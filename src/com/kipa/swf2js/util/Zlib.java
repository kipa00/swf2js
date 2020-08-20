package com.kipa.swf2js.util;

import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class Zlib {
    public static void decompress(byte[] data, byte[] result) throws DataFormatException {
        Inflater decompressor = new Inflater();
        decompressor.setInput(data);
        decompressor.inflate(result);
        decompressor.end();
    }
}
