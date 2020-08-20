package com.kipa.swf2js.types;

import com.kipa.swf2js.exception.WrongTagException;
import com.kipa.swf2js.types.shape.FillStyle;
import com.kipa.swf2js.types.shape.LineStyle;
import com.kipa.swf2js.types.shape.record.CurvedEdgeRecord;
import com.kipa.swf2js.types.shape.record.EndShapeRecord;
import com.kipa.swf2js.types.shape.record.ShapeRecord;
import com.kipa.swf2js.types.shape.record.StraightEdgeRecord;
import com.kipa.swf2js.util.BitReader;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ShapeWithStyle {
    private final List<FillStyle> fillStyles;
    private final List<LineStyle> lineStyles;
    private final List<ShapeRecord> shapeRecords;
    private int numFillBits, numLineBits;
    private FillStyle nowFillStyle0, nowFillStyle1;
    private LineStyle nowLineStyle;
    private boolean supportsAlpha;

    public ShapeWithStyle(boolean alpha) {
        this.fillStyles = new ArrayList<>();
        this.lineStyles = new ArrayList<>();
        this.shapeRecords = new ArrayList<>();
        this.supportsAlpha = alpha;
    }

    public void read(InputStream is) throws IOException, WrongTagException {
        FillStyle.readArray(is, this.fillStyles, supportsAlpha);
        LineStyle.readArray(is, this.lineStyles, supportsAlpha);
        BitReader br = new BitReader(is);
        numFillBits = br.read(4);
        numLineBits = br.read(4);
        ShapeRecord sr;
        do {
            sr = ShapeRecord.classifyRead(br, this);
            this.shapeRecords.add(sr);
        } while (!(sr instanceof EndShapeRecord));
    }

    public int getNumFillBits() {
        return this.numFillBits;
    }

    public int getNumLineBits() {
        return this.numLineBits;
    }

    public void setNumFillBits(int numFillBits) {
        this.numFillBits = numFillBits;
    }

    public void setNumLineBits(int numLineBits) {
        this.numLineBits = numLineBits;
    }

    public FillStyle getNowFillStyle0() {
        return this.nowFillStyle0;
    }

    public FillStyle getNowFillStyle1() {
        return this.nowFillStyle1;
    }

    public LineStyle getNowLineStyle() {
        return this.nowLineStyle;
    }

    public void setNowFillStyle0(int nowFillStyle0) {
        if (nowFillStyle0 == 0) {
            this.nowFillStyle0 = null;
        } else {
            this.nowFillStyle0 = this.fillStyles.get(nowFillStyle0 - 1);
        }
    }

    public void setNowFillStyle1(int nowFillStyle1) {
        if (nowFillStyle1 == 0) {
            this.nowFillStyle1 = null;
        } else {
            this.nowFillStyle1 = this.fillStyles.get(nowFillStyle1 - 1);
        }
    }

    public void setNowLineStyle(int nowLineStyle) {
        if (nowLineStyle == 0) {
            this.nowLineStyle = null;
        } else {
            this.nowLineStyle = this.lineStyles.get(nowLineStyle - 1);
        }
    }

    public List<FillStyle> getFillStyles() {
        return fillStyles;
    }

    public List<LineStyle> getLineStyles() {
        return lineStyles;
    }

    public List<ShapeRecord> getShapeRecords() {
        return shapeRecords;
    }

    public boolean supportsAlpha() {
        return supportsAlpha;
    }
}
