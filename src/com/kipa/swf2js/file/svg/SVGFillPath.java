package com.kipa.swf2js.file.svg;

import com.kipa.swf2js.file.svg.planar.Edge;
import com.kipa.swf2js.file.svg.planar.Point;
import com.kipa.swf2js.types.shape.FillStyle;

import java.util.*;

public class SVGFillPath {
    private FillStyle fillStyle;
    private List<Edge> fillControlList;

    public SVGFillPath(FillStyle fillStyle) {
        this.fillStyle = fillStyle;
        this.fillControlList = new ArrayList<>();
    }

    public FillStyle getFillStyle() {
        return fillStyle;
    }

    public void fromEdges(List<Edge> edges) {
        Map<Point, LinkedList<Edge>> sortedEdges = new HashMap<>();
        for (Edge edge: edges) {
            Point key = edge.getStart();
            LinkedList<Edge> list = sortedEdges.computeIfAbsent(key, k -> new LinkedList<>());
            list.add(edge);
        }
        while (!sortedEdges.isEmpty()) {
            Point startingPoint = sortedEdges.keySet().iterator().next();
            Point nowPoint = startingPoint;
            do {
                LinkedList<Edge> map = sortedEdges.get(nowPoint);
                Edge edge = map.pop();
                if (map.isEmpty()) {
                    sortedEdges.remove(nowPoint);
                }
                this.fillControlList.add(edge);
                nowPoint = edge.getEnd();
            } while (!nowPoint.equals(startingPoint));
        }
    }

    public String buildPath() {
        Point lastPoint = null;
        StringBuilder sb = new StringBuilder();
        for (Edge edge: fillControlList) {
            sb.append(edge.toString(lastPoint));
            sb.append(' ');
            lastPoint = edge.getEnd();
        }
        return sb.toString().trim();
    }

    @Override
    public String toString() {
        String path = "d=\"" + this.buildPath() + "\"";
        StringBuilder sb = new StringBuilder();
        sb.append("<path ");
        sb.append(path);
        sb.append(' ');
        sb.append(fillStyle);
        sb.append(" />");
        return sb.toString();
    }
}
