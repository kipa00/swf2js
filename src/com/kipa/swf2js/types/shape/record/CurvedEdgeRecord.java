package com.kipa.swf2js.types.shape.record;

import com.kipa.swf2js.exception.WrongTagException;
import com.kipa.swf2js.types.ShapeWithStyle;
import com.kipa.swf2js.util.BitReader;

import java.io.IOException;

public class CurvedEdgeRecord extends ShapeRecord {
    private int controlDeltaX, controlDeltaY, anchorDeltaX, anchorDeltaY;

    @Override
    public void read(BitReader br, ShapeWithStyle shapeDefinition) throws IOException, WrongTagException {
        super.read(br, shapeDefinition);
        int numBits = br.read(4) + 2;
        this.controlDeltaX = br.readSigned(numBits);
        this.controlDeltaY = br.readSigned(numBits);
        this.anchorDeltaX = br.readSigned(numBits);
        this.anchorDeltaY = br.readSigned(numBits);
    }

    public int getControlDeltaX() {
        return controlDeltaX;
    }

    public int getControlDeltaY() {
        return controlDeltaY;
    }

    public int getAnchorDeltaX() {
        return anchorDeltaX;
    }

    public int getAnchorDeltaY() {
        return anchorDeltaY;
    }
}
