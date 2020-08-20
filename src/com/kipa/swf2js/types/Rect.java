package com.kipa.swf2js.types;

import com.kipa.swf2js.util.BitReader;
import com.kipa.swf2js.util.MathHelper;

import java.io.IOException;

public class Rect {
    private int minX, maxX, minY, maxY;

    public Rect() {
        this(0, 0, 0, 0);
    }

    public Rect(int minX, int maxX, int minY, int maxY) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    public void read(BitReader br) throws IOException {
        int bitSize = br.read(5);
        this.minX = br.readSigned(bitSize);
        this.maxX = br.readSigned(bitSize);
        this.minY = br.readSigned(bitSize);
        this.maxY = br.readSigned(bitSize);
    }

    public int getMinX() {
        return this.minX;
    }

    public int getMaxX() {
        return this.maxX;
    }

    public int getMinY() {
        return this.minY;
    }

    public int getMaxY() {
        return this.maxY;
    }

    public String toString() {
        return "Rect[(" + this.getMinX() + ", " + this.getMinY() + "), (" + this.getMaxX() + ", " + this.getMaxY() + ")]";
    }
}
