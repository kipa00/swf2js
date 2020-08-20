package com.kipa.swf2js.types.shape;

import com.kipa.swf2js.types.shape.Color;
import com.kipa.swf2js.util.DataReader;

import java.io.IOException;
import java.io.InputStream;

public class RGBAColor extends Color {
    private int color;

    public RGBAColor() {
        this(0, 0, 0, 255);
    }
    public RGBAColor(int r, int g, int b, int a) {
        this.color = (a << 24) | (b << 16) | (g << 8) | r;
    }


    @Override
    public void read(InputStream is) throws IOException {
        this.color = new DataReader(is).readInt();
    }

    public int getRed() {
        return color & 255;
    }

    public int getGreen() {
        return (color >>> 8) & 255;
    }

    public int getBlue() {
        return (color >>> 16) & 255;
    }

    public int getAlpha() {
        return (color >>> 24) & 255;
    }

    @Override
    public String toCSSString() {
        if (this.getAlpha() == 255) {
            return new RGBColor(this.getRed(), this.getGreen(), this.getBlue()).toCSSString();
        }
        String alpha = String.format("%.3f", this.getAlpha() / 255.);
        return "rgba(" + this.getRed() + ", " + this.getGreen() + ", " + this.getBlue() + ", " + alpha + ")";
    }
}
