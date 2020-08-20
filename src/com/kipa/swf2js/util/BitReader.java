package com.kipa.swf2js.util;

import java.io.IOException;
import java.io.InputStream;

public class BitReader {
    private final InputStream reader;
    private int buffer, bufferSize;

    public BitReader(InputStream is) {
        this.reader = is;
        this.buffer = 0;
        this.bufferSize = 0;
    }

    public int read(int bits) throws IOException {
        if (bits == 0) {
            return 0;
        }
        int readStart = Math.min(this.bufferSize, bits);

        int res = this.buffer >>> (this.bufferSize - readStart);
        this.bufferSize -= readStart;
        bits -= readStart;
        this.buffer &= (1 << this.bufferSize) - 1;
        while (bits >= 8) {
            int val = this.reader.read();
            if (val == -1) {
                this.bufferSize = 0;
                this.buffer = 0;
                return -1;
            }
            res = (res << 8) | val;
            bits -= 8;
        }
        if (bits > 0) {
            int val = this.reader.read();
            if (val == -1) {
                this.bufferSize = 0;
                this.buffer = 0;
                return -1;
            }
            this.buffer = val;
            this.bufferSize = 8 - bits;
            res = (res << bits) | (this.buffer >>> this.bufferSize);
            this.buffer &= (1 << this.bufferSize) - 1;
        }
        return res;
    }

    public InputStream getReader() {
        this.discard();
        return this.reader;
    }

    public int readSigned(int bits) throws IOException {
        return MathHelper.makeSignedInt(this.read(bits), bits);
    }

    private void discard() {
        this.buffer = this.bufferSize = 0;
    }
}
