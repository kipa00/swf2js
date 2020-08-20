package com.kipa.swf2js.file.svg;

import com.kipa.swf2js.exception.UnimplementedException;
import com.kipa.swf2js.file.File;
import com.kipa.swf2js.file.svg.planar.Edge;
import com.kipa.swf2js.file.svg.planar.Point;
import com.kipa.swf2js.file.svg.planar.StyledEdge;
import com.kipa.swf2js.tag.DefineShape2Tag;
import com.kipa.swf2js.types.Rect;
import com.kipa.swf2js.types.shape.record.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SVGFile extends File {
    private Rect viewBound;
    private List<SVGShape> shapes;

    public SVGFile() {
        this.shapes = new ArrayList<>();
    }

    @Override
    public void read(InputStream is) throws Exception {
        throw new UnimplementedException();
    }

    public void fromDefineShape2(DefineShape2Tag shapeDefinition) throws Exception {
        this.viewBound = shapeDefinition.getShapeBounds();
        Point lastPoint = new Point();
        List<StyledEdge> edges = new ArrayList<>();
        for (ShapeRecord record: shapeDefinition.getShapeDefinition().getShapeRecords()) {
            if (record instanceof StyleChangeRecord) {
                StyleChangeRecord castedRecord = (StyleChangeRecord) record;
                if (castedRecord.hasNewStyles()) {
                    this.shapes.add(new SVGShape(edges));
                    edges.clear();
                }
                if (castedRecord.isMove()) {
                    lastPoint = new Point(castedRecord.getMoveDeltaX(), castedRecord.getMoveDeltaY());
                }
            } else if (record instanceof EndShapeRecord) {
                break;
            } else {
                Point nowPoint = null;
                Edge edge = null;
                if (record instanceof StraightEdgeRecord) {
                    StraightEdgeRecord castedRecord = (StraightEdgeRecord)record;
                    int nowX = lastPoint.getX() + castedRecord.getDeltaX();
                    int nowY = lastPoint.getY() + castedRecord.getDeltaY();
                    nowPoint = new Point(nowX, nowY);
                    edge = new Edge(lastPoint, nowPoint);
                } else if (record instanceof CurvedEdgeRecord) {
                    CurvedEdgeRecord castedRecord = (CurvedEdgeRecord) record;
                    int controlX = lastPoint.getX() + castedRecord.getControlDeltaX();
                    int controlY = lastPoint.getY() + castedRecord.getControlDeltaY();
                    int nowX = controlX + castedRecord.getAnchorDeltaX();
                    int nowY = controlY + castedRecord.getAnchorDeltaY();
                    nowPoint = new Point(nowX, nowY);
                    edge = new Edge(lastPoint, nowPoint, new Point(controlX, controlY));
                }
                edges.add(new StyledEdge(edge, record.getLineStyle(), record.getFillStyle0(), record.getFillStyle1()));
                lastPoint = nowPoint;
            }
        }
        if (!edges.isEmpty()) {
            this.shapes.add(new SVGShape(edges));
        }
    }

    private String buildHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append("<svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"");
        sb.append(this.viewBound.getMinX());
        sb.append(' ');
        sb.append(this.viewBound.getMinY());
        sb.append(' ');
        sb.append(this.viewBound.getMaxX() - this.viewBound.getMinX());
        sb.append(' ');
        sb.append(this.viewBound.getMaxY() - this.viewBound.getMinY());
        sb.append("\">");
        sb.append("  <style>\n    path { stroke-linecap: round; stroke-linejoin: round; }\n  </style>\n");
        return sb.toString();
    }

    @Override
    public void write(OutputStream os) throws Exception {
        os.write((this.buildHeader() + "\n").getBytes(StandardCharsets.UTF_8));
        for (SVGShape shape: this.shapes) {
            os.write(shape.toString().getBytes(StandardCharsets.UTF_8));
        }
        os.write("</svg>\n".getBytes(StandardCharsets.UTF_8));
    }
}
