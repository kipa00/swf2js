package com.kipa.swf2js.types.shape;

import com.kipa.swf2js.util.DataReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class LineStyle {
    private int width;
    private Color color;

    public LineStyle(boolean supportsAlpha) {
        this.color = supportsAlpha ? new RGBAColor() : new RGBColor();
    }

    public void read(InputStream is) throws IOException {
        this.width = new DataReader(is).readUnsignedShort();
        this.color.read(is);
    }

    public static void readArray(InputStream is, List<LineStyle> array, boolean supportsAlpha) throws IOException {
        DataReader dr = new DataReader(is);
        int count = dr.read();
        if (count == 255) {
            count = dr.readUnsignedShort();
        }
        for (int i=0; i<count; ++i) {
            LineStyle ls = new LineStyle(supportsAlpha);
            ls.read(is);
            array.add(ls);
        }
    }

    public int getWidth() {
        return this.width;
    }

    @Override
    public String toString() {
        String strokeWidth = "stroke-width=\"" + width + "\"";
        String color = "stroke=\"" + this.color.toCSSString() + "\"";
        return strokeWidth + " " + color;
    }
}
