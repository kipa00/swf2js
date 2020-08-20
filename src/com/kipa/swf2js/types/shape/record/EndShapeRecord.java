package com.kipa.swf2js.types.shape.record;

import com.kipa.swf2js.types.ShapeWithStyle;
import com.kipa.swf2js.util.BitReader;

public class EndShapeRecord extends ShapeRecord {
    @Override
    public void read(BitReader br, ShapeWithStyle shapeDefinition) {
        // we MUST do nothing!
    }
}
