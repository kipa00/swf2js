package com.kipa.swf2js.types.shape.record;

import com.kipa.swf2js.exception.IsAnEndShapeRecord;
import com.kipa.swf2js.exception.WrongTagException;
import com.kipa.swf2js.types.ShapeWithStyle;
import com.kipa.swf2js.types.shape.FillStyle;
import com.kipa.swf2js.types.shape.LineStyle;
import com.kipa.swf2js.util.BitReader;

import java.io.IOException;
import java.util.List;

public class StyleChangeRecord extends ShapeRecord {
    private int moveDeltaX, moveDeltaY;
    private int flag;

    @Override
    public void read(BitReader br, ShapeWithStyle shapeDefinition) throws IOException, WrongTagException {
        flag = br.read(5);
        if (flag == 0) {
            throw new IsAnEndShapeRecord();
        }
        if (this.isMove()) {
            int moveBits = br.read(5);
            this.moveDeltaX = br.readSigned(moveBits);
            this.moveDeltaY = br.readSigned(moveBits);
        }
        if ((flag & 2) != 0) {
            shapeDefinition.setNowFillStyle0(br.read(shapeDefinition.getNumFillBits()));
        }
        if ((flag & 4) != 0) {
            shapeDefinition.setNowFillStyle1(br.read(shapeDefinition.getNumFillBits()));
        }
        if ((flag & 8) != 0) {
            shapeDefinition.setNowLineStyle(br.read(shapeDefinition.getNumLineBits()));
        }
        if ((flag & 16) != 0) {
            List<FillStyle> fillStyles = shapeDefinition.getFillStyles();
            fillStyles.clear();
            FillStyle.readArray(br.getReader(), fillStyles, shapeDefinition.supportsAlpha());

            List<LineStyle> lineStyles = shapeDefinition.getLineStyles();
            lineStyles.clear();
            LineStyle.readArray(br.getReader(), lineStyles, shapeDefinition.supportsAlpha());

            shapeDefinition.setNumFillBits(br.read(4));
            shapeDefinition.setNumLineBits(br.read(4));
        }
    }

    public int getMoveDeltaX() {
        return moveDeltaX;
    }

    public int getMoveDeltaY() {
        return moveDeltaY;
    }

    public boolean isMove() {
        return (this.flag & 1) != 0;
    }

    public boolean hasFillStyle0() {
        return (this.flag & 2) != 0;
    }

    public boolean hasFillStyle1() {
        return (this.flag & 4) != 0;
    }

    public boolean hasLineStyle() {
        return (this.flag & 8) != 0;
    }

    public boolean hasNewStyles() {
        return (this.flag & 16) != 0;
    }
}
