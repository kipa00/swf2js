package com.kipa.swf2js.types.shape.record;

import com.kipa.swf2js.exception.WrongTagException;
import com.kipa.swf2js.types.ShapeWithStyle;
import com.kipa.swf2js.util.BitReader;

import java.io.IOException;

public class StraightEdgeRecord extends ShapeRecord {
    private int deltaX, deltaY;

    public StraightEdgeRecord() {
        this(0, 0);
    }

    public StraightEdgeRecord(int dx, int dy) {
        this.deltaX = dx;
        this.deltaY = dy;
    }

    @Override
    public void read(BitReader br, ShapeWithStyle shapeDefinition) throws IOException, WrongTagException {
        super.read(br, shapeDefinition);
        int numBits = br.read(4) + 2;
        boolean generalLineFlag = br.read(1) != 0;
        boolean vertLineFlag = generalLineFlag || br.read(1) != 0;
        if (generalLineFlag || !vertLineFlag) {
            this.deltaX = br.readSigned(numBits);
        } else {
            this.deltaX = 0;
        }
        if (generalLineFlag || vertLineFlag) {
            this.deltaY = br.readSigned(numBits);
        } else {
            this.deltaY = 0;
        }
    }

    public int getDeltaX() {
        return deltaX;
    }

    public int getDeltaY() {
        return deltaY;
    }
}
