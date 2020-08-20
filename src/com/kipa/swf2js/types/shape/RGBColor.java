package com.kipa.swf2js.types.shape;

import com.kipa.swf2js.util.DataReader;

import java.io.IOException;
import java.io.InputStream;

public class RGBColor extends Color {
    private byte red, green, blue;

    public RGBColor() {
        this(0, 0, 0);
    }

    public RGBColor(int r, int g, int b) {
        this.red = (byte)r;
        this.green = (byte)g;
        this.blue = (byte)b;
    }

    @Override
    public void read(InputStream is) throws IOException {
        DataReader dr = new DataReader(is);
        this.red = (byte)dr.read();
        this.green = (byte)dr.read();
        this.blue = (byte)dr.read();
    }

    public int getRed() {
        return this.red & 255;
    }

    public int getGreen() {
        return this.green & 255;
    }

    public int getBlue() {
        return this.blue & 255;
    }

    @Override
    public String toCSSString() {
        int colorCode = (this.getRed() << 16) | (this.getGreen() << 8) | this.getBlue();
        return "#" + Integer.toHexString((1 << 24) | colorCode).substring(1);
    }
}
