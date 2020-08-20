package com.kipa.swf2js.tag;

import com.kipa.swf2js.exception.WrongTagException;
import com.kipa.swf2js.types.Rect;
import com.kipa.swf2js.types.ShapeWithStyle;
import com.kipa.swf2js.util.BitReader;
import com.kipa.swf2js.util.DataReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class DefineShape2Tag extends Tag {
    private int shapeId;
    private Rect shapeBounds;
    protected ShapeWithStyle shapeDefinition;

    public DefineShape2Tag() {
        super();
        this.shapeBounds = new Rect();
        this.shapeDefinition = new ShapeWithStyle(false);
    }

    @Override
    public void validate() throws WrongTagException {
        ByteArrayInputStream bais = new ByteArrayInputStream(this.data);
        try {
            this.shapeId = new DataReader(bais).readUnsignedShort();
            this.shapeBounds.read(new BitReader(bais));
            this.shapeDefinition.read(bais);
        } catch (IOException err) {
            throw new WrongTagException();
        }
    }

    public int getShapeId() {
        return shapeId;
    }

    public Rect getShapeBounds() {
        return shapeBounds;
    }

    public ShapeWithStyle getShapeDefinition() {
        return shapeDefinition;
    }
}
