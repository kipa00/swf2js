package com.kipa.swf2js.file.svg.planar;

import com.kipa.swf2js.types.shape.FillStyle;
import com.kipa.swf2js.types.shape.LineStyle;

public class StyledEdge {
    private Edge edge;
    private LineStyle lineStyle;
    private FillStyle leftFillStyle, rightFillStyle;

    public StyledEdge(Edge edge, LineStyle lineStyle, FillStyle leftFillStyle, FillStyle rightFillStyle) {
        this.edge = edge;
        this.lineStyle = lineStyle;
        this.leftFillStyle = leftFillStyle;
        this.rightFillStyle = rightFillStyle;
    }

    public Edge getEdge() {
        return edge;
    }

    public LineStyle getLineStyle() {
        return lineStyle;
    }

    public FillStyle getLeftFillStyle() {
        return leftFillStyle;
    }

    public FillStyle getRightFillStyle() {
        return rightFillStyle;
    }
}
