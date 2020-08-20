package com.kipa.swf2js.util;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public class DataReader {
    private final InputStream reader;
    public DataReader(InputStream is) {
        this.reader = is;
    }

    public int readUnsignedShort() throws IOException {
        int lower = this.reader.read();
        int upper = this.reader.read();
        if (lower == -1 || upper == -1) {
            throw new EOFException();
        }
        return lower | (upper << 8);
    }

    public int readShort() throws IOException {
        int res = readUnsignedShort();
        if (res == -1) {
            throw new EOFException();
        }
        return MathHelper.makeSignedInt(res, 16);
    }

    public int readInt() throws IOException {
        int lower = this.readUnsignedShort();
        int upper = this.readUnsignedShort();
        return lower | (upper << 16);
    }

    public long readUnsignedInt() throws IOException {
        return (long)this.readInt() & 0xffffffffL;
    }

    public int read() throws IOException {
        int res = this.reader.read();
        if (res == -1) {
            throw new EOFException();
        }
        return res;
    }

    public void read(byte[] data) throws IOException {
        int lengthLeft = data.length, bytesRead;
        while (lengthLeft > 0 && (bytesRead = this.reader.read(data, data.length - lengthLeft, lengthLeft)) >= 0) {
            lengthLeft -= bytesRead;
        }
        if (lengthLeft > 0) {
            throw new EOFException();
        }
    }
}
