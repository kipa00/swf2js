package com.kipa.swf2js.types.shape;

import com.kipa.swf2js.exception.WrongTagException;
import com.kipa.swf2js.util.DataReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class FillStyle {
    private FillStyleType fillStyleType = null;
    private Color color = null;
    // TODO: add a lot more to complete FillStyle

    public FillStyle(boolean supportsAlpha) {
        this.color = supportsAlpha ? new RGBAColor() : new RGBColor();
    }

    public void read(InputStream is) throws IOException, WrongTagException {
        DataReader dr = new DataReader(is);
        int typeCode = dr.read() & 255;
        this.fillStyleType = FillStyleType.findByByteCode(typeCode);
        if (this.fillStyleType == null) {
            throw new WrongTagException("type " + typeCode + " unknown");
        }
        if (this.fillStyleType.equals(FillStyleType.SOLID)) {
            this.color.read(is);
        } else {
            throw new WrongTagException("unimplemented");
        }
    }

    @Override
    public String toString() {
        if (this.fillStyleType.equals(FillStyleType.SOLID)) {
            return "fill=\"" + this.color.toCSSString() + "\"";
        }
        return super.toString();
    }

    public static void readArray(InputStream is, List<FillStyle> array, boolean supportsAlpha) throws IOException, WrongTagException {
        DataReader dr = new DataReader(is);
        int count = dr.read() & 255;
        if (count == 255) {
            count = dr.readUnsignedShort();
        }
        for (int i=0; i<count; ++i) {
            FillStyle fs = new FillStyle(supportsAlpha);
            fs.read(is);
            array.add(fs);
        }
    }
}
