package com.kipa.swf2js.types.shape.record;

import com.kipa.swf2js.exception.IsAnEndShapeRecord;
import com.kipa.swf2js.exception.WrongTagException;
import com.kipa.swf2js.types.ShapeWithStyle;
import com.kipa.swf2js.types.shape.FillStyle;
import com.kipa.swf2js.types.shape.LineStyle;
import com.kipa.swf2js.util.BitReader;

import java.io.IOException;

public abstract class ShapeRecord {
    private FillStyle fillStyle0, fillStyle1;
    private LineStyle lineStyle;

    public void read(BitReader br, ShapeWithStyle shapeDefinition) throws IOException, WrongTagException {
        this.fillStyle0 = shapeDefinition.getNowFillStyle0();
        this.fillStyle1 = shapeDefinition.getNowFillStyle1();
        this.lineStyle = shapeDefinition.getNowLineStyle();
    }

    public static ShapeRecord classifyRead(BitReader br, ShapeWithStyle shapeDefinition) throws IOException, WrongTagException {
        ShapeRecord res = null;
        if (br.read(1) == 0) {
            try {
                res = new StyleChangeRecord();
                res.read(br, shapeDefinition);
            } catch (WrongTagException err) {
                if (err instanceof IsAnEndShapeRecord) {
                    res = new EndShapeRecord();
                } else {
                    throw err;
                }
            }
            return res;
        } else if (br.read(1) == 0) {
            res = new CurvedEdgeRecord();
        } else {
            res = new StraightEdgeRecord();
        }
        res.read(br, shapeDefinition);
        return res;
    }

    public LineStyle getLineStyle() {
        return lineStyle;
    }

    public FillStyle getFillStyle0() {
        return fillStyle0;
    }

    public FillStyle getFillStyle1() {
        return fillStyle1;
    }
}
